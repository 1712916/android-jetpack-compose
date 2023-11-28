package com.example.android_jetpack_compose.util

import androidx.navigation.*

class InitAppRoute private constructor() {
    companion object {
        val instance = InitAppRoute()
        private var initRoute: String? = null
    }

    fun setInitRoute(route: String?) {
        initRoute = route
    }

    fun navToInitRoute(navController: NavController) {
        if (initRoute != null) {
            navController.navigate(initRoute!!)
            initRoute = null
        }
    }
}
