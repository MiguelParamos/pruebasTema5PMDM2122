package com.example.pruebafirebase

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

class PantallaCRUDFirebase : AppCompatActivity() {

    private val botonInsertar: Button by lazy { findViewById(R.id.botonInsertar) }
    private val campoModelo: EditText by lazy { findViewById(R.id.campoModelo) }
    private val campoNuevo: CheckBox by lazy { findViewById(R.id.campoNuevo) }
    private val campoCuandoComprado: EditText by lazy { findViewById(R.id.campoCuandoComprado) }
    private val campoPrecio: EditText by lazy { findViewById(R.id.campoPrecio) }
    private val lista: RecyclerView by lazy { findViewById(R.id.lista) };
    private lateinit var adapter: TelefonoAdapter
    private lateinit var listaTelefonos: ArrayList<Telefono>
    private val firestore by lazy { FirebaseFirestore.getInstance() }

    private fun consultarBD(){
        firestore.collection("telefonos")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    listaTelefonos=ArrayList<Telefono>()
                    for(el in it.result){
                        listaTelefonos.add(el.toObject(Telefono::class.java))
                    }
                    this.rellenaElementosLista()
                } else {
                    Log.d("mensajeError",""+
                            it.exception?.message)
                    Toast.makeText(
                        this@PantallaCRUDFirebase,
                        it.exception?.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
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
                        this.consultarBD()
                        adapter.notifyDataSetChanged()
                    } else {
                        try {
                            throw it.exception!!
                        } catch (e: io.grpc.StatusException) {
                            Toast.makeText(
                                this@PantallaCRUDFirebase,
                                R.string.noPuedesHacerSinLogin,
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }
                }

            } else {
                Toast.makeText(
                    this@PantallaCRUDFirebase,
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

    private fun rellenaElementosLista() {
        adapter = TelefonoAdapter(this, this.listaTelefonos)
        lista.layoutManager = LinearLayoutManager(this)
        lista.adapter = adapter
    }
}