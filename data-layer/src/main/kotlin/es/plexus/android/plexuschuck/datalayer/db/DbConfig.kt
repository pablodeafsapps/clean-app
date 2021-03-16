package es.plexus.android.plexuschuck.datalayer.db

import androidx.room.Database
import androidx.room.RoomDatabase

const val APP_DB_TAG = "appDb"

@Database(entities = [UserEntity::class, FinancialProductEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}
