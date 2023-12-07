package com.example.android_jetpack_compose.data.mapping_firebase_object

import com.google.firebase.firestore.*

abstract class MappingFirebaseObject<F, T> {
    abstract fun saving(sourceObject: F): T
    abstract suspend fun getting(sourceObject: DocumentSnapshot): F
}
