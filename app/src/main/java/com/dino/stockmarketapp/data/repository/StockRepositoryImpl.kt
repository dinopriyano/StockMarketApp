package com.dino.stockmarketapp.data.repository

import com.dino.stockmarketapp.data.csv.CSVParser
import com.dino.stockmarketapp.data.csv.CompanyListingsParser
import com.dino.stockmarketapp.data.mapper.toCompanyListing
import com.dino.stockmarketapp.data.mapper.toCompanyListingEntity
import com.dino.stockmarketapp.data.source.Resource
import com.dino.stockmarketapp.data.source.local.dao.StockDao
import com.dino.stockmarketapp.data.source.remote.StockApi
import com.dino.stockmarketapp.domain.model.CompanyListing
import com.dino.stockmarketapp.domain.repository.StockRepository
import com.opencsv.CSVReader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val dao: StockDao,
    private val companyListingsParser: CSVParser<CompanyListing >
): StockRepository {

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit( Resource.Loading(true) )
            val localListings = dao.searchCompanyListing(query)
            emit( Resource.Success(
                value = localListings.map {
                    it.toCompanyListing()
                }
            ))

            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            if(!isDbEmpty && !fetchFromRemote) {
                emit( Resource.Loading(false) )
                return@flow
            }

            val remoteListings = try {
                val response = api.getListings()
                companyListingsParser.parser(response.byteStream())
            }
            catch (e: IOException) {
                e.printStackTrace()
                emit( Resource.Error("Couldn't load data") )
                null
            }
            catch (e: HttpException) {
                e.printStackTrace()
                emit( Resource.Error("Couldn't load data") )
                null
            }

            remoteListings?.let { listings ->
                dao.clearCompanyListing()
                dao.insertCompanyListings(
                    listings.map {
                        it.toCompanyListingEntity()
                    }
                )
                emit(
                    Resource.Success(
                        value = dao.searchCompanyListing("")
                            .map {
                                it.toCompanyListing()
                            }
                    )
                )
                emit(Resource.Loading(false))
            }
        }
    }

}