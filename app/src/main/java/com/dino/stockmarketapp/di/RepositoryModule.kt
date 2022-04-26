package com.dino.stockmarketapp.di

import com.dino.stockmarketapp.data.csv.CSVParser
import com.dino.stockmarketapp.data.csv.CompanyListingsParser
import com.dino.stockmarketapp.data.repository.StockRepositoryImpl
import com.dino.stockmarketapp.domain.model.CompanyListing
import com.dino.stockmarketapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class  RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingsParser: CompanyListingsParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository

}