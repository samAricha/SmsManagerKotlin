package teka.android.smsmanager.presentation.recipient_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import teka.android.smsmanager.data.room.Recipient
import teka.android.smsmanager.data.room.RecipientsDao



class RecipientViewModel(private val recipientDao: RecipientsDao) : ViewModel() {


    val allRecipients: Flow<List<Recipient>> = recipientDao.getAllRecipients()
        .flowOn(Dispatchers.IO)

    fun insertRecipient(recipient: Recipient) {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                recipientDao.insertRecipient(recipient)
            }
            //recipientDao.insertRecipient(recipient)
        }

    }
}

class RecipientViewModelFactory(private val recipientDao: RecipientsDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipientViewModel::class.java)) {
            return RecipientViewModel(recipientDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}