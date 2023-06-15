package teka.android.smsmanager.presentation.recipient_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import teka.android.smsmanager.data.room.Recipient



@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "FlowOperatorInvokedInComposition")
@Composable
fun RecipientScreen(recipientViewModel: RecipientViewModel, navController: NavHostController) {

    val recipientsState by recipientViewModel.allRecipients
        .flowOn(Dispatchers.Main)
        .collectAsState(initial = emptyList())

    Scaffold {
        RecipientList(recipients = recipientsState)
    }
}



@Composable
fun RecipientList(recipients: List<Recipient>) {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Recipient List",
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.h6
        )
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)

        ) {
            items(recipients) { recipient ->
                Text(text = "Name: ${recipient.name}")
                Text(text = "Phone: ${recipient.phone}")
                Divider()
            }
        }
    }
}