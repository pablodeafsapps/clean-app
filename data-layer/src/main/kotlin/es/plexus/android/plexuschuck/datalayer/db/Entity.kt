package es.plexus.android.plexuschuck.datalayer.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "user_entity")
data class UserEntity(
        @PrimaryKey(autoGenerate = true) val uId: Int = 0
)

@Entity(tableName = "financial_product_entity")
data class FinancialProductEntity(
        @PrimaryKey(autoGenerate = true) val pId: Int,
        val ownerId: Int,
        val pName: String,
        val pOrder: Int
)

data class UserWithFinancialProductEntity(
        @Embedded val user: UserEntity,
        @Relation(
                parentColumn = "uId",
                entityColumn = "ownerId"
        )
        val financialProductList: List<FinancialProductEntity>
)
