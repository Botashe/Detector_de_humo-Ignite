package com.example.loginapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText

class CreateActivity : AppCompatActivity() {

    private lateinit var textInputEditTextNombre: TextInputEditText
    private lateinit var textInputEditTextCorreo: TextInputEditText
    private lateinit var textInputEditTextPassword: TextInputEditText
    private lateinit var textError: TextView
    private lateinit var botonCrear: Button
    private lateinit var botonBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        // Inicialización de vistas
        textInputEditTextNombre = findViewById(R.id.user)
        textInputEditTextCorreo = findViewById(R.id.email)
        textInputEditTextPassword = findViewById(R.id.password)
        textError = findViewById(R.id.text_error)  // Asegúrate de que este ID esté correcto en activity_create.xml
        botonCrear = findViewById(R.id.button_create)
        botonBack = findViewById(R.id.button_back)

        // Configura el listener para el botón de registro
        botonCrear.setOnClickListener {
            val nombre = textInputEditTextNombre.text.toString()
            val correo = textInputEditTextCorreo.text.toString()
            val password = textInputEditTextPassword.text.toString()

            // Validar campos antes de enviar la solicitud
            if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty()) {
                textError.text = "Por favor completa todos los campos."
                textError.visibility = View.VISIBLE
                return@setOnClickListener
            }

            val queue: RequestQueue = Volley.newRequestQueue(this)
            val url = "http://192.168.0.23/loginapp_server/register.php"  // Cambia la URL según sea necesario

            val stringRequest = object : StringRequest(Request.Method.POST, url,
                Response.Listener<String> { response ->
                    // Procesa la respuesta del servidor
                    if (response.equals("Éxito en el registro", ignoreCase = true)) {
                        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        textError.text = response
                        textError.visibility = View.VISIBLE
                    }
                }, Response.ErrorListener { error ->
                    textError.text = "Error en la conexión: ${error.message}"
                    textError.visibility = View.VISIBLE
                }) {
                override fun getParams(): Map<String, String> {
                    return mapOf(
                        "nombre" to nombre,
                        "email" to correo,
                        "password" to password
                    )
                }
            }
            queue.add(stringRequest)
        }

        // Configura el listener para el botón de regreso
        botonBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()  // Opcional: cierra la actividad actual
        }
    }
}
