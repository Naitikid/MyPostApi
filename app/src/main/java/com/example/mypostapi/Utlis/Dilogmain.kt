package com.example.mypostapi.Utlis

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.res.TypedArrayUtils.getText
import com.example.mypostapi.Acivity.HomeActivity
import com.example.mypostapi.Acivity.LoginActivity
import com.example.mypostapi.Acivity.MainActivity
import com.example.mypostapi.R
import com.example.mypostapi.Acivity.SighupActivity
import com.example.mypostapi.Sharedpreferences.SharedpreferencesApi
import com.google.android.material.dialog.MaterialAlertDialogBuilder

private lateinit var sharedpre: SharedpreferencesApi

class Dilogmain(val activity: Activity) {

    fun dialog(title: Int, massage: String, type: String, isCheck: Int) {
        val materialAlertDialog = MaterialAlertDialogBuilder(activity)
        materialAlertDialog.setTitle(title)
        materialAlertDialog.setIcon(R.drawable.logo)
        materialAlertDialog.setMessage(massage)
        materialAlertDialog.setPositiveButton(activity.resources.getString(R.string.ok)) { A, b ->

            if (type == "Login") {
                if (isCheck == 1) {
                    activity.startActivity(Intent(activity, HomeActivity::class.java))
                    activity.finishAffinity()
                }
            } else if (type == "Signin") {
                if (isCheck == 1) {
                    activity.startActivity(Intent(activity, LoginActivity::class.java))
                    activity.finishAffinity()
                }
            }
        }
        materialAlertDialog.show()
    }

    @SuppressLint("RestrictedApi")
    fun diloglogout(title: Int, massage: Int) {
        sharedpre = SharedpreferencesApi(activity)
        val materialAlertDiloglogout = MaterialAlertDialogBuilder(activity)
        materialAlertDiloglogout.setTitle(title)
        materialAlertDiloglogout.setIcon(R.drawable.logo)
        materialAlertDiloglogout.setMessage(massage)
        materialAlertDiloglogout.setNegativeButton("Cancel",null)
        materialAlertDiloglogout.setPositiveButton("Ok") { A, B ->
            sharedpre.setPrefBoolean("userlogin", false)
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivity(intent)
            Toast.makeText(activity, R.string.logout, Toast.LENGTH_SHORT).show()
        }
        materialAlertDiloglogout.show()
    }
}