package com.example.android_jetpack_compose

import android.app.*
import android.content.*
import android.os.*
import android.util.*
import androidx.activity.*
import androidx.activity.compose.*
import androidx.annotation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.lifecycle.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.android_jetpack_compose.data.category.*
import com.example.android_jetpack_compose.data.method.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.ui.setting_remind_input.view.*
import com.example.android_jetpack_compose.ui.theme.*
import com.example.android_jetpack_compose.util.*

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val route: String? = intent.getStringExtra("route")
        InitAppRoute.instance.setInitRoute(route)

        AppUser.getInstance().preferencesManager =
            EncryptedSharedPreferencesManager(context = this)
        setContent {
            val navController = rememberNavController()
            AndroidjetpackcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigateView(navController)
                }
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NavigateView(navHostController: NavHostController) {
    Scaffold { innerPadding ->
        AppNavHost(
            navController = navHostController,
            modifier = Modifier.padding(innerPadding),
        )
    }
}
