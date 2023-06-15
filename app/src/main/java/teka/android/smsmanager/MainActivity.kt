package teka.android.smsmanager

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.view.ContextThemeWrapper
import android.widget.Space
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import teka.android.smsmanager.data.room.DBProvider
import teka.android.smsmanager.data.room.SmsManagerDB
import teka.android.smsmanager.navigation.Screen
import teka.android.smsmanager.navigation.myNavigation
import teka.android.smsmanager.presentation.recipient_screen.RecipientDataCollectionScreen
import teka.android.smsmanager.presentation.recipient_screen.RecipientScreen
import teka.android.smsmanager.presentation.recipient_screen.RecipientViewModel
import teka.android.smsmanager.presentation.recipient_screen.RecipientViewModelFactory
import teka.android.smsmanager.presentation.send_sms_screen.SendSmsScreen
import teka.android.smsmanager.presentation.send_sms_screen.SmsViewModel
import teka.android.smsmanager.presentation.send_sms_screen.SmsViewModelFactory

import teka.android.smsmanager.ui.theme.SmsManagerTheme
import teka.android.smsmanager.ui.theme.greenColor





class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()


            SmsManagerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {


                    Scaffold(
                        topBar = {
                            TopAppBar(backgroundColor = greenColor,
                                title = {
                                    Text(
                                        text = "Teka Dev",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                })
                        },

                        bottomBar = {
                            BottomNavigation {
                                val navController = navController
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val currentRoute = navBackStackEntry?.destination?.route

                                BottomNavigationItem(
                                    selected = currentRoute == Screen.SendSms.route,
                                    onClick = {
                                        navController.navigate(Screen.SendSms.route) {
                                            launchSingleTop = true
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            painter = painterResource(R.drawable.baseline_sms_24),
                                            contentDescription = "Send SMS"
                                        )
                                    },
                                    label = {
                                        Text(text = "Send SMS")
                                    }
                                )

                                BottomNavigationItem(
                                    selected = currentRoute == Screen.Recipients.route,
                                    onClick = {
                                        navController.navigate(Screen.Recipients.route) {
                                            launchSingleTop = true
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            painter = painterResource(R.drawable.baseline_contacts_24),
                                            contentDescription = "Add Contact List"
                                        )
                                    },
                                    label = {
                                        Text(text = "Recipients")
                                    }
                                )

                                BottomNavigationItem(
                                    selected = currentRoute == Screen.addRecipient.route,
                                    onClick = {
                                        navController.navigate(Screen.addRecipient.route) {
                                            launchSingleTop = true
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            painter = painterResource(R.drawable.baseline_perm_contact_calendar_24),
                                            contentDescription = "Contact List"
                                        )
                                    },
                                    label = {
                                        Text(text = "Add Recipient")
                                    }
                                )
                            }
                        }

                    ) {


                        val database = DBProvider.getDatabase(applicationContext)
                        val recipientDao = database.recipientsDao()

                        val recipientViewModel = ViewModelProvider(
                            this,
                            RecipientViewModelFactory(recipientDao)
                        )[RecipientViewModel::class.java]

                        val smsViewModel = ViewModelProvider(
                            this,
                            SmsViewModelFactory(recipientDao)
                        )[SmsViewModel::class.java]

                        //val smsViewModel: SmsViewModel = viewModel()

                        myNavigation(navHostController = navController,recipientViewModel = recipientViewModel, smsViewModel = smsViewModel)


                    }
                }
            }
        }
    }
}




//bottom navigation boiler plate code
data class BottomNavigationItem(
    val route: String,
    val icon: Int,
    val contentDescription: String,
    val label: String
)


val bottomNavigationItems = listOf(
    BottomNavigationItem(
        route = Screen.SendSms.route,
        icon = R.drawable.baseline_sms_24,
        contentDescription = "Send SMS",
        label = "Send SMS"
    ),
    BottomNavigationItem(
        route = Screen.Recipients.route,
        icon = R.drawable.baseline_contacts_24,
        contentDescription = "Add Contact List",
        label = "Recipients"
    ),
    BottomNavigationItem(
        route = Screen.addRecipient.route,
        icon = R.drawable.baseline_perm_contact_calendar_24,
        contentDescription = "Contact List",
        label = "Other Screen"
    )
)

