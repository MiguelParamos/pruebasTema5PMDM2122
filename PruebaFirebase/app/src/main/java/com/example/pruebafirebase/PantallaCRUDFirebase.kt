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
    //PropietarioActual almacena el propietario para el telefono
    // que se va a insertar/Modificar
    private var propietarioActual=
        FirebaseAuth.getInstance().currentUser?.email.toString();
    private var modeloAnterior:String?=null;

    fun rellenarCampos(
        mod:String, prec:Float ,cc:String,
    nuevo:Boolean,prop:String){
        modeloAnterior=mod
        campoModelo.setText(mod)
        campoCuandoComprado.setText(cc)
        campoNuevo.isChecked=nuevo
        campoPrecio.setText(""+prec)
        botonInsertar.text=resources.getString(R.string.editar)
        this.propietarioActual=prop;
    }

    fun actualizarAdapter(){
        this.consultarBD()
        adapter.notifyDataSetChanged()
    }

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
                }else{ //Editar el teléfono
                    val auth: FirebaseAuth = FirebaseAuth.getInstance()
                    val telefono:Telefono=Telefono(
                        campoModelo.text.toString(),
                        campoPrecio.text.toString().toFloat(),
                        campoCuandoComprado.text.toString(),
                        campoNuevo.isChecked,
                       this.propietarioActual
                    )
                    firestore.collection("telefonos").document(
                    this.propietarioActual+" - "+
                            this.modeloAnterior).set(telefono)
                    botonInsertar.setText(
                        resources.getString(R.string.insertarMovil))
                    this.actualizarAdapter()
                propietarioActual=
                    FirebaseAuth.getInstance().currentUser?.email.toString()
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

fun rellenaElementosLista() {
        adapter = TelefonoAdapter(this, this.listaTelefonos,
            this.firestore)
        lista.layoutManager = LinearLayoutManager(this)
        lista.adapter = adapter
    }
}