package com.example.pruebafirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PantallaCRUDFirebase : AppCompatActivity() {

    val botonInsertar: Button by lazy{findViewById(R.id.botonInsertar)}
    val campoModelo: EditText by lazy{findViewById(R.id.campoModelo)}
    val campoNuevo: CheckBox by lazy{findViewById(R.id.campoNuevo)}
    val campoCuandoComprado: EditText by lazy{findViewById(R.id.campoCuandoComprado)}
    val campoPrecio: EditText by lazy{findViewById(R.id.campoPrecio)}
    val lista: RecyclerView by lazy{ findViewById(R.id.lista)};

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_crudfirebase)

        botonInsertar.setOnClickListener {
            if(!campoModelo.text.isBlank()
                &&!campoPrecio.text.isBlank()&&
                    !campoCuandoComprado.text.isBlank()) {
                val firestore: FirebaseFirestore =
                    FirebaseFirestore.getInstance()
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
                val tareaInsertar =
                    firestore.collection("telefonos").document(
                        auth.currentUser?.email.toString() + " - " +
                                campoModelo.text.toString()
                    ).set(valoresTlf);
                tareaInsertar.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            this@PantallaCRUDFirebase,
                            R.string.telefonoInsertado, Toast.LENGTH_LONG
                        ).show()
                    } else {
                        try {
                            throw it.exception!!
                        } catch (e: io.grpc.StatusException) {
                            Toast.makeText(
                                this@PantallaCRUDFirebase,
                                R.string.noPuedesHacerSinLogin,
                                Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }

            }else{
                Toast.makeText(this@PantallaCRUDFirebase,
                    R.string.rellenaTodo, Toast.LENGTH_LONG).show()
            }

        }

        val botonLogout:Button=findViewById(R.id.botonLogout)
        botonLogout.setOnClickListener {
            val auth:FirebaseAuth= FirebaseAuth.getInstance()
            auth.signOut()
        }
    }
}