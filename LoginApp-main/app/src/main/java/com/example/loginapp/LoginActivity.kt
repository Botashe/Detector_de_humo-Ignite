package com.example.loginapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val editTextEmail = findViewById<EditText>(R.id.email)
        val editTextPassword = findViewById<EditText>(R.id.password)
        val buttonLogin = findViewById<Button>(R.id.submit)
        val createButton = findViewById<Button>(R.id.create)

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                login(email, password)
            } else {
                Toast.makeText(this, "Por favor ingresa un Email y Contraseña", Toast.LENGTH_SHORT).show()
            }
        }

        createButton.setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(email: String, password: String) {
        val url = "http://192.168.0.23/loginApp_server/login.php"
        val stringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    if (!jsonObject.getBoolean("error")) {
                        Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, RegistroActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    Toast.makeText(this, "Response parsing error", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                Toast.makeText(this, "Network error: " + error.message, Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["email"] = email
                params["password"] = password
                return params
            }
        }
        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }
}
