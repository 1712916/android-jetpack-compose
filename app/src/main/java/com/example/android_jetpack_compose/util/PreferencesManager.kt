package com.example.android_jetpack_compose.util

import android.content.*
import android.security.keystore.*
import androidx.security.crypto.*

abstract class PreferencesManager(context: Context) {
    abstract fun saveData(key: String, value: String)

    abstract fun getData(key: String, defaultValue: String? = null): String?
    abstract fun remove(key: String)
}

fun PreferencesManager.currentEmailKey(): String {
    return "email"
}

class SharedPreferencesManager(context: Context) : PreferencesManager(context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()
    override fun saveData(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    override fun getData(key: String, defaultValue: String?): String? {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    override fun remove(key: String) {
        editor.remove(key).apply()
    }
}

class EncryptedSharedPreferencesManager(context: Context) : PreferencesManager(context) {
    //    private var masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    var spec = KeyGenParameterSpec.Builder(
        MasterKey.DEFAULT_MASTER_KEY_ALIAS,
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    )
        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        .setKeySize(256)
        .build()

    var masterKey = MasterKey.Builder(context)
        .setKeyGenParameterSpec(spec)
        .build()

    private var sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secret_shared_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val editor = sharedPreferences.edit()

    override fun saveData(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    override fun getData(key: String, defaultValue: String?): String? {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    override fun remove(key: String) {
        editor.remove(key).apply()
    }
}
