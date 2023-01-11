package com.example.mypostapi.Acivity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mypostapi.Model.ProfileResponse
import com.example.mypostapi.R
import com.example.mypostapi.Sharedpreferences.SharedpreferencesApi
import com.example.mypostapi.Utlis.Dilogmain
import com.example.mypostapi.Webservice.RetrofitClient
import com.example.mypostapi.Webservice.UserwebServiceApi
import com.example.mypostapi.databinding.ActivityHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var sharedpre: SharedpreferencesApi
    private lateinit var dilogmain: Dilogmain
    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedpre = SharedpreferencesApi(this)
        dilogmain= Dilogmain(this)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        id = sharedpre.getPrefString("id", "")
        profiledataget()
        Log.e("HOME", "onCreate:${sharedpre.getPrefBoolean("userlogin")}", )
        Log.e("TAG", "onCreate: " + id)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                Dilogmain(this@HomeActivity).diloglogout(R.string.app_name,R.string.areyousure)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun profiledataget() {
        RetrofitClient.getRetrofit().create(UserwebServiceApi::class.java)
            .profileuser(id.toString())
            .enqueue(object : Callback<ProfileResponse?> {
                override fun onResponse(
                    call: Call<ProfileResponse?>,
                    response: Response<ProfileResponse?>
                ) {
                    val data: ProfileResponse? = response.body()

                    binding.usernameid.setText("${data?.data?.id}")
                    binding.nametext.setText("${data?.data?.name}")
                    binding.Emailtext.setText("${data?.data?.email}")
                    Log.d("TAG", "onResponse: $id")
                   /* Toast.makeText(applicationContext, "status:-  " + data?.status, Toast.LENGTH_SHORT).show()
                    Toast.makeText(applicationContext, "message:-  " + data?.message, Toast.LENGTH_SHORT).show()*/
                }

                override fun onFailure(call: Call<ProfileResponse?>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG)
                        .show()
                }
            })
    }
}