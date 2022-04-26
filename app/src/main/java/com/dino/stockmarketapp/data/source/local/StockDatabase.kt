package com.dino.stockmarketapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dino.stockmarketapp.data.source.local.dao.StockDao
import com.dino.stockmarketapp.data.source.local.entity.CompanyListingEntity

@Database(
    entities = [CompanyListingEntity::class],
    version = 1
)
abstract class StockDatabase: RoomDatabase() {

    abstract val stockDao: StockDao

}