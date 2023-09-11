package com.example.tp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import android.text.Editable
import androidx.room.RoomDatabase
import androidx.room.Database
import kotlinx.coroutines.newFixedThreadPoolContext

class  Login : AppCompatActivity() {
    private lateinit var etUsuario: EditText
    private lateinit var etPassword: EditText
    private  lateinit var cbRecordar: CheckBox
    private lateinit var btnRegistrar: Button
    private lateinit var btnIniciar: Button
    private lateinit var btnMostrar : Button
    private lateinit var etConsulta : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        saludarUsuario()

        etUsuario= findViewById(R.id.etUsuario)
        etPassword=findViewById(R.id.etPass)
        cbRecordar=findViewById(R.id.cbRecordar)
        btnIniciar=findViewById(R.id.botonIniciar)
        btnRegistrar=findViewById(R.id.botonRegistrar)
        btnMostrar=findViewById(R.id.btnMostrar)
        etConsulta=findViewById(R.id.etConsulta)

        btnRegistrar.setOnClickListener{
            val crear = Intent(this, crearUsuario::class.java)
            //intentMain.putExtra("nombre", nombreUsuario)
            startActivity(crear)
            finish()
        }

        btnIniciar.setOnClickListener{
            var mensaje = "Iniciar Sesion"
            var nombreUsuario= etUsuario.text.toString()

            if(nombreUsuario.isEmpty() || etPassword.text.toString().isEmpty()){
                mensaje+= "- Faltan Datos"


            }else{
                mensaje+= " - Datos OK"
                if(cbRecordar.isChecked)
                    mensaje+= "- Recordar Usuario"

                val intentMain = Intent(this, MainActivity::class.java)
                intentMain.putExtra("nombre", nombreUsuario)
                startActivity(intentMain)
                finish()
            }

            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show()
        }

            btnIniciar.setOnClickListener{
                var mensaje = "Iniciar Sesión"
                val nombreUsuario = etUsuario.text.toString()

                if (nombreUsuario.isEmpty() || etPassword.text.toString().isEmpty()) {
                    mensaje += "- Faltan Datos"
                } else {
                    mensaje += " - Datos OK"
                    if (cbRecordar.isChecked) {
                        mensaje += "- Recordar Usuario"
                    }

                    // Aquí deberías realizar la autenticación del usuario.
                    // Si la autenticación es exitosa, entonces puedes realizar la inserción en la base de datos.

                    // Por ejemplo, supongamos que tienes una función de autenticación que devuelve true si es exitosa.
                    if (autenticarUsuario(nombreUsuario, etPassword.text.toString())) {
                        val db = Room.databaseBuilder(
                            this@Login,
                            AppDataBase::class.java,
                            "BaseDeDatosI"
                        ).allowMainThreadQueries().build()

                        val usuarios = Usuarios(
                            etUsuario.text.toString(),
                            etPassword.text.toString()
                        )

                        val reg = db.usuariosDao().insert(usuarios)
                        Toast.makeText(applicationContext, reg.toString(), Toast.LENGTH_LONG).show()

                        val intentMain = Intent(this, MainActivity::class.java)
                        intentMain.putExtra("nombre", nombreUsuario)
                        startActivity(intentMain)
                        finish()
                        return@setOnClickListener
                    }
                }

                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
            }
            btnMostrar.setOnClickListener {
            val db = Room.databaseBuilder(
                applicationContext,
                AppDataBase::class.java,
                "BaseDeDatosI"
            ).allowMainThreadQueries().build()

            val lista = db.usuariosDao().getAll()
            var valores = ""

            for (i in 0 until lista.size) {
                valores += "${lista[i].id}: ${lista[i].getUsuario()} usuario: ${lista[i].usuario} pass: ${lista[i].password}\n"
            }

                etConsulta.text = Editable.Factory.getInstance().newEditable(valores)
        }

    }

    private fun saludarUsuario() {
        var bundle: Bundle? = intent.extras
        if(bundle != null){
            var usuario = bundle?.getString("nombre")
            Toast.makeText(this, "Bienvenido/a $usuario", Toast.LENGTH_LONG).show()
        }
    }
}
// Función de ejemplo para autenticar al usuario.
private fun autenticarUsuario(usuario: String, contraseña: String): Boolean {
    // Aquí deberías implementar la lógica de autenticación.
    // Devuelve true si la autenticación es exitosa, false en caso contrario.
    return true
}