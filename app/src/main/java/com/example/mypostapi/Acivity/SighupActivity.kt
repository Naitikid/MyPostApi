package com.example.mypostapi.Acivity

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mypostapi.Model.DefaultResponse
import com.example.mypostapi.R
import com.example.mypostapi.Utlis.Dilogmain
import com.example.mypostapi.Utlis.Utils
import com.example.mypostapi.Webservice.RetrofitClient
import com.example.mypostapi.Webservice.UserwebServiceApi
import com.example.mypostapi.databinding.ActivitySighupBinding
import com.example.mypostapi.emailPattern
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SighupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySighupBinding
    private lateinit var dilogmain: Dilogmain
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySighupBinding.inflate(layoutInflater)
        progressDialog = ProgressDialog(this)
        setContentView(binding.root)
        dilogmain = Dilogmain(this)
        binding.apply {
            btnsign.setOnClickListener {
                validation()
            }
            loginlinkforSign.setOnClickListener{
                startActivity(Intent(this@SighupActivity,LoginActivity::class.java))
                finish()
            }
        }

    }
                     /*  below is validation code*/

    private fun validation() {
        binding.apply {
           // Utils().hideSoftKeyBoard(this@SighupActivity)
            val usernamesigh = fullnamesighup.editText?.text.toString()
            val emailsigh = emailEditLayoutSign.editText?.text.toString()
            Log.d(TAG, "validationsighup: $emailsigh ")
            val passwordsigh = passwordEditLayoutSign.editText?.text.toString()

            fullnamesighup.error = null
            emailEditLayoutSign.error = null
            passwordEditLayoutSign.error = null

            if (usernamesigh.isEmpty()) {
                fullnamesighup.error = getString(R.string.entereUsername)
            } else if (emailsigh.isEmpty()) {
                emailEditLayoutSign.error = getString(R.string.enteremail)
            } else if (!emailsigh.matches(emailPattern.toRegex())) {
                emailEditLayoutSign.error = getString(R.string.enterecorrect)
            } else if (passwordsigh.isEmpty()) {
                passwordEditLayoutSign.error = getString(R.string.enterepassword)
            } else if (passwordsigh.length < 6) {
                passwordEditLayoutSign.error = getString(R.string.enterepassword6latter)
            } else {
                 sighcallingapi(usernamesigh, emailsigh, passwordsigh)
            }
        }
    }
                         /*  Below is live API Calling code*/

    private fun sighcallingapi(usernamesigh: String, emailsigh: String, passwordsigh: String) {
        binding.apply {

            if (Utils().isNetworkAvailable(this@SighupActivity)) {
                progressDialog.setMessage(getString(R.string.Loding))
                progressDialog.setCancelable(false)
                progressDialog.show()
                RetrofitClient.getRetrofit().create(UserwebServiceApi::class.java)
                    .registerUser(usernamesigh, emailsigh, passwordsigh)
                    .enqueue(object : Callback<DefaultResponse> {
                        override fun onResponse(
                            call: Call<DefaultResponse>,
                            response: Response<DefaultResponse>
                        ) {
                            progressDialog.dismiss()
                            if (response.isSuccessful) {
                                val data = response.body()
                                if (data != null) {
                                    if (data!!.status) {
                                        dilogmain.dialog(
                                            R.string.app_name,
                                            data.message,
                                            resources.getString(R.string.signinkey_Dilog),
                                            1
                                        )
                                        fullnamesighup.editText?.text?.clear()
                                        emailEditLayoutSign.editText?.text?.clear()
                                        passwordEditLayoutSign.editText?.text?.clear()
                                    } else {
                                        dilogmain.dialog(
                                            R.string.app_name,
                                            data.message,
                                            resources.getString(R.string.signinkey_Dilog),
                                            0
                                        )
                                    }
                                }
                            } else {
                                Toast.makeText(
                                    this@SighupActivity,
                                    R.string.Serverisnotworking,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }

                        override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                            progressDialog.dismiss()
                            Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    })
            } else {
                Toast.makeText(
                    this@SighupActivity,
                    R.string.pleaseturninternet,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}