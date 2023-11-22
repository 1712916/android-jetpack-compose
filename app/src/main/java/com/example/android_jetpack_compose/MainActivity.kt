package com.example.android_jetpack_compose

import android.os.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.*
import com.example.android_jetpack_compose.ui.theme.AndroidjetpackcomposeTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.android_jetpack_compose.data.category.*
import com.example.android_jetpack_compose.data.method.*

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
