package com.dino.stockmarketapp.data.csv

import com.dino.stockmarketapp.domain.model.CompanyListing
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyListingsParser @Inject constructor(): CSVParser<CompanyListing> {
    override suspend fun parser(stream: InputStream): List<CompanyListing> {
        val csvRader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvRader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val symbol = line.getOrNull(0)
                    val name = line.getOrNull(1)
                    val exchange = line.getOrNull(2)
                    CompanyListing(
                        symbol = symbol ?: return@mapNotNull null,
                        name = name ?: return@mapNotNull null,
                        exchange = exchange ?: return@mapNotNull null
                    )
                }
                .also {
                    csvRader.close()
                }
        }
    }
}