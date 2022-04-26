package com.dino.stockmarketapp.domain.repository

import com.dino.stockmarketapp.data.source.Resource
import com.dino.stockmarketapp.domain.model.CompanyListing
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

}