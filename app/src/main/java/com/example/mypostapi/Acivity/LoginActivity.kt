package com.example.mypostapi.Acivity

import android.app.ProgressDialog
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mypostapi.Model.LoginResponse
import com.example.mypostapi.R
import com.example.mypostapi.Sharedpreferences.SharedpreferencesApi
import com.example.mypostapi.Utlis.Dilogmain
import com.example.mypostapi.Utlis.Utils
import com.example.mypostapi.Webservice.RetrofitClient
import com.example.mypostapi.Webservice.UserwebServiceApi
import com.example.mypostapi.databinding.ActivityLoginBinding
import com.example.mypostapi.emailPattern
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var dilogmain: Dilogmain
    private lateinit var sharedpre: SharedpreferencesApi
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedpre = SharedpreferencesApi(this)
        progressDialog = ProgressDialog(this)
        dilogmain = Dilogmain(this)
        binding.btnLogin.setOnClickListener{
            validationlogin()
        }
        binding.sighuplink.setOnClickListener {
            startActivity(Intent(this, SighupActivity::class.java))
            finish()
        }
    }
                              /*  below is validation code*/

    private fun validationlogin() {
        binding.apply {
              //  Utils().hideSoftKeyBoard(this@LoginActivity)
                val emaillogin = emailEditLayoutLogin.editText?.text.toString()
                val passwordlogin = passwordEditLayoutLogin.editText?.text.toString()
                emailEditLayoutLogin.error = null
                passwordEditLayoutLogin.error = null

                if (emaillogin!!.isEmpty()) {
                    emailEditLayoutLogin.error = getString(R.string.enteremail)
                } else if (!emaillogin.matches(emailPattern.toRegex())) {
                    emailEditLayoutLogin.error = getString(R.string.enterecorrect)
                } else if (passwordlogin!!.isEmpty()) {
                    passwordEditLayoutLogin.error = getString(R.string.enterepassword)
                } else {
                    logincallingapi(emaillogin, passwordlogin)
                }
        }
    }
                       /*  Below is live API Calling code*/

    private fun logincallingapi(emaillogin: String, passwordlogin: String) {
        if (Utils().isNetworkAvailable(this@LoginActivity)) {
            progressDialog.setMessage(getString(R.string.Loding))
            progressDialog.setCancelable(false)
            progressDialog.show()
            RetrofitClient.getRetrofit().create(UserwebServiceApi::class.java)
                .loginUser(emaillogin, passwordlogin)
                .enqueue(object : Callback<LoginResponse?> {
                    override fun onResponse(
                        call: Call<LoginResponse?>, response: Response<LoginResponse?>
                    ) {
                        if (response.isSuccessful) {
                            val data = response.body()
                            if (data!!.status) {
                                data.data.id
                                Log.e("TAG", "onResponse: " + data.data.id)
                                sharedpre.setPrefString("id", "${data.data.id}")
                                dilogmain.dialog(R.string.app_name, data.message, resources.getString(R.string.loginkey_Dilog), 1)
                                sharedpre.setPrefBoolean("userlogin", true)
                                binding.emailEditLayoutLogin.editText?.text?.clear()
                                binding.passwordEditLayoutLogin.editText?.text?.clear()
                                Log.e("LOGIN", "onCreate:${sharedpre.getPrefBoolean("userlogin")}",)
                            } else
                            {
                                dilogmain.dialog(R.string.app_name, data.message, resources.getString(R.string.loginkey_Dilog), 0)
                            }
                        } else { Toast.makeText(
                            this@LoginActivity,
                            "server is not Working Please Wait",
                            Toast.LENGTH_SHORT
                        ).show()
                        }
                        progressDialog.dismiss()
                    }
                    override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                        progressDialog.dismiss()
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG)
                            .show()
                    }
                })
        }else{
            Toast.makeText(this,R.string.pleaseturninternet, Toast.LENGTH_SHORT).show()
        }
    }
}