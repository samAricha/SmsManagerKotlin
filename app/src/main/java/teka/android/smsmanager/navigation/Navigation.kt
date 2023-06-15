package teka.android.smsmanager.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import teka.android.smsmanager.presentation.recipient_screen.RecipientDataCollectionScreen
import teka.android.smsmanager.presentation.recipient_screen.RecipientScreen
import teka.android.smsmanager.presentation.recipient_screen.RecipientViewModel
import teka.android.smsmanager.presentation.send_sms_screen.SendSmsScreen
import teka.android.smsmanager.presentation.send_sms_screen.SmsViewModel


@Composable
fun myNavigation(
    navHostController: NavHostController,
    recipientViewModel: RecipientViewModel,
    smsViewModel: SmsViewModel
){
    NavHost(navController = navHostController, startDestination = Screen.SendSms.route ){
        composable(route = Screen.SendSms.route) {
            SendSmsScreen(viewModel = smsViewModel, navController = navHostController)
        }

        composable(route = Screen.Recipients.route) {
            RecipientScreen(recipientViewModel = recipientViewModel, navController = navHostController)
        }

        composable(route = Screen.addRecipient.route){
            RecipientDataCollectionScreen(recipientViewModel = recipientViewModel, navController = navHostController)
        }

    }

}