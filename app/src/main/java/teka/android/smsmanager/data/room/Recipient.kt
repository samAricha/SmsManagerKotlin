package teka.android.smsmanager.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipients")
data class Recipient(

    @PrimaryKey
    val id: Int = 0,
    val name: String,
    val phone: String

)
