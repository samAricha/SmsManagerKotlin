package teka.android.smsmanager.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipientsDao {

        @Query("SELECT * FROM recipients")
        fun getAllRecipients(): Flow<List<Recipient>>

        @Insert
        fun insertRecipient(recipient: Recipient)

        @Update
        fun updateRecipient(recipient: Recipient)

        @Delete
        fun deleteRecipient(recipient: Recipient)


}