package com.example.tp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class crearUsuario : AppCompatActivity() {

    private lateinit var etCorreo: EditText
    private lateinit var etUsuario: EditText
    private lateinit var etPass: EditText
    private lateinit var etRepetirPass: EditText
    private lateinit var btnCrear: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_usuario)

        etCorreo=(findViewById(R.id.etCorreo))
        etUsuario=findViewById(R.id.etUsuario)
        etPass=(findViewById(R.id.etPass))
        etRepetirPass=(findViewById(R.id.etRepetirPass))
        btnCrear=findViewById(R.id.btnCrear)

        btnCrear.setOnClickListener{
            var mensaje = "Crear Usuario"
            var nombreUsuario= etUsuario.text.toString()
            val password = etPass.text.toString()
            val repetirPassword = etRepetirPass.text.toString()

            if(nombreUsuario.isEmpty() || etPass.text.toString().isEmpty() || etRepetirPass.text.toString().isEmpty() || etCorreo.text.toString().isEmpty()){
                mensaje= " Faltan Datos"

            }else{
                //mensaje += " - Datos Correctos"

                if (password.length >= 6 && password.any { it.isLetter() } && password.any { it.isDigit() } && password == repetirPassword) {
                    mensaje=" Usuario Creado Correctamente"
                    val intentMain = Intent(this, Login::class.java)
                    startActivity(intentMain)
                    finish()
                } else {
                    mensaje = "- Contraseña incorrecta o no coincide"
                }
            }

            Toast.makeText(this,mensaje, Toast.LENGTH_SHORT).show()
        }

    }
}