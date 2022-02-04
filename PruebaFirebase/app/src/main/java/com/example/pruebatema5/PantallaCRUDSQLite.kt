package com.example.pruebatema5

import adapters_holders.TelefonoAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import clases.Telefono
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PantallaCRUDSQLite : PantallaCRUD() {

   override fun consultarBD() {

    }

    override fun borrarElementoBD(pk: String) {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_crudfirebase)

        this.consultarBD()

        botonInsertar.setOnClickListener {
            if (!campoModelo.text.isBlank()
                && !campoPrecio.text.isBlank() &&
                !campoCuandoComprado.text.isBlank()
            ) {
                if(!botonInsertar.text.toString().
                    equals(getString(R.string.editar))) {
                    val valoresTlf: HashMap<String, Any> =
                        HashMap<String, Any>()
                    valoresTlf.put("modelo", campoModelo.text.toString())
                    valoresTlf.put(
                        "precio",
                        campoPrecio.text.toString().toFloat()
                    )
                    valoresTlf.put(
                        "fechaCompra",
                        campoCuandoComprado.text.toString()
                    )
                    valoresTlf.put("esNuevo", campoNuevo.isChecked)
                    val auth: FirebaseAuth = FirebaseAuth.getInstance()
                    valoresTlf.put(
                        "propietario",
                        auth.currentUser?.email.toString()
                    )
                  //INSERTAR
                }else{ //Editar el teléfono
                  //EDITAR
                }
            } else {
                Toast.makeText(
                    this@PantallaCRUDSQLite,
                    R.string.rellenaTodo, Toast.LENGTH_LONG
                ).show()
            }

        }

        val botonLogout: Button = findViewById(R.id.botonLogout)
        botonLogout.setOnClickListener {
            val auth: FirebaseAuth = FirebaseAuth.getInstance()
            auth.signOut()
        }
    }

    fun rellenaElementosLista() {
     //llamada a adapter
        // adapter = TelefonoAdapter(this, this.listaTelefonos)
        lista.layoutManager = LinearLayoutManager(this)
        lista.adapter = adapter
    }
}