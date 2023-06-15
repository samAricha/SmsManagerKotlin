package teka.android.smsmanager.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipients")
data class Recipient(

    @PrimaryKey val id: Int,
    val name: String,
    val email: String

)
