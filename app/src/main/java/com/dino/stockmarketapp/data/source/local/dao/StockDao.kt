package com.dino.stockmarketapp.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dino.stockmarketapp.data.source.local.entity.CompanyListingEntity

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(
        companyListingsEntity: List<CompanyListingEntity>
    )

    @Query("DELETE FROM CompanyListingEntity")
    suspend fun clearCompanyListing()

    @Query("SELECT * FROM CompanyListingEntity WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR UPPER(:query) == symbol")
    suspend fun searchCompanyListing(query: String): List<CompanyListingEntity>
    
}