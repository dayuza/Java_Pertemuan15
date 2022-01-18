package com.informatika19100064.databarang

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.informatika19100064.databarang.model.ResponseAdmin
import com.informatika19100064.databarang.network.koneksi
import com.informatika19100064.databarang.service.SessionPreferences
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btn_clean
import kotlinx.android.synthetic.main.activity_login.btn_submit
import kotlinx.android.synthetic.main.activity_login.et_password
import kotlinx.android.synthetic.main.activity_login.et_username
import kotlinx.android.synthetic.main.activity_login.tv_disini
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var sessionPreferences: SessionPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_register)
        btn_submit.setOnClickListener {
            val userName = et_username.text.toString()
            val password = et_password.text.toString()

            if (userName.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Form Tidak Boleh kosong!", Toast.LENGTH_LONG).show()
            }else{
                actionData(userName, password)
            }
        }
        btn_clean.setOnClickListener {
            formClear()
        }
        tv_disini.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
    fun actionData(username : String, password : String){
        koneksi.service.login(username, password).enqueue(object : Callback<ResponseAdmin> {
            override fun onFailure(call: Call<ResponseAdmin>, t: Throwable) {
                Log.d("pesan1", t.localizedMessage)
            }

            override fun onResponse(call: Call<ResponseAdmin>, response: Response<ResponseAdmin>) {
                if (response.isSuccessful){
                    val resbody = response.body()
                    val resStatus = resbody?.status
                    val resUserName = resbody?.data?.get(0)?.username
                    Log.d("pesan", resUserName.toString())
                    if (resStatus == true){
                        sessionPreferences = SessionPreferences(this@RegisterActivity)
                        sessionPreferences.actionLogin(resUserName.toString())
                        val i = Intent(this@RegisterActivity, MainActivity::class.java)
                        startActivity(i)
                        finish()
                    }else if (resStatus == false){
                        Toast.makeText(this@RegisterActivity,
                            "Username atau Password salah!", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
    fun formClear(){
        et_username.text.clear()
        et_password.text.clear()
    }
}