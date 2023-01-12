package com.candra.mysimpleloginencryption

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.securepreferences.SecurePreferences

class SessionManager(context: Context){
    companion object {
        const val KEY_LOGIN = "isLogin"
        const val KEY_USERNAME = "username"
    }

    @SuppressLint("ObsoleteSdkInt")
    private var pref: SharedPreferences =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // enkripsi dengan Jetpack Security
            val spec = KeyGenParameterSpec.Builder(
                MasterKey.DEFAULT_MASTER_KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                 // setBlockNodes menggunakan BLOCK_MODE_GCM
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                 // setKeySize menggunakan DEFAULT_AES_GCM_MASTER_KEY_SIZE
                .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
                 // alternatif untuk menambahkan hash dengan menggunakan fungsi setEncryptionPaddings
                 // menggunakan ENCRYPTION_PADDING_NONE
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()

            // Membuat master keynya
            val masterKey = MasterKey.Builder(context)
                .setKeyGenParameterSpec(spec)
                .build()

            // Membuat enkripsi dengan Library EncryptionSharedPreference
            EncryptedSharedPreferences.create(
                context,
                "Session", // nama file
                masterKey, // master  key sendiri dengan algoritma AES GCM 256
                // Membuat enksripis dengan algoritma AES256_SIV dan AES256_GCM
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }else{
            // Enskripsi dengan SecurityPreference
            // Ada 3 constructor yang digunakan untuk inisialisasi menggunakan SecurePreference, yaitu Context, password, dan nama file.
            SecurePreferences(context,"candra","Session")
        }
    private var editor: SharedPreferences.Editor = pref.edit()

    fun createLoginSession() {
        editor.putBoolean(KEY_LOGIN, true)
            .commit()
    }

    fun logout() {
        editor.clear()
        editor.commit()
    }

    val isLogin: Boolean = pref.getBoolean(KEY_LOGIN, false)

    fun saveToPreference(key: String, value: String) = editor.putString(key, value).commit()

    fun getFromPreference(key: String) = pref.getString(key, "")

}

/**
 * kesimpulan
 * Di sini Anda mengecek versi Android yang digunakan,
 * apabila Marshmallow ke atas maka kita akan menggunakan Jetpack Security, jika di bawahnya kita menggunakan SecurityPreference
 */