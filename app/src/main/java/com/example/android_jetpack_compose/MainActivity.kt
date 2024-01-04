package com.example.android_jetpack_compose

import android.Manifest
import android.content.*
import android.content.pm.*
import android.os.*
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.*
import androidx.annotation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.core.app.*
import androidx.core.content.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.ui.theme.*
import com.example.android_jetpack_compose.util.*

class MainActivity : ComponentActivity() {

    val PERMISSION_REQUEST_CODE = 1001

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val route: String? = intent.getStringExtra("route")
        InitAppRoute.instance.setInitRoute(route)

        AppUser.getInstance().preferencesManager =
            SharedPreferencesManager(context = this)
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


        if (!hasPermissions())
            requestPermissions()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun hasPermissions(): Boolean {
        for (permission in permissionsToRequest) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false // At least one permission is not granted
            }
        }
        return true // All permissions are granted
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val permissionsToRequest = arrayOf(
        Manifest.permission.SET_ALARM,
        Manifest.permission.POST_NOTIFICATIONS,
    )

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun requestPermissions() {
        val permissionsNotGranted = permissionsToRequest.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()

        if (permissionsNotGranted.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsNotGranted,
                PERMISSION_REQUEST_CODE
            )
        }
    }

    // Handle the permission request result
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            var allPermissionsGranted = true

            for (grantResult in grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false
                    break
                }
            }

            if (!allPermissionsGranted) {
                "Permission Denied: Alarm will not work".showToast(this)
            }
        }
    }
}

fun String.showToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
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
