package com.example.pruebatema5

import adapters_holders.TelefonoAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import clases.Telefono
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

open abstract class PantallaCRUD : AppCompatActivity() {

    protected val botonInsertar: Button by lazy { findViewById(R.id.botonInsertar) }
    protected val campoModelo: EditText by lazy { findViewById(R.id.campoModelo) }
    protected val campoNuevo: CheckBox by lazy { findViewById(R.id.campoNuevo) }
    protected val campoCuandoComprado: EditText by lazy { findViewById(R.id.campoCuandoComprado) }
    protected val campoPrecio: EditText by lazy { findViewById(R.id.campoPrecio) }
    protected val lista: RecyclerView by lazy { findViewById(R.id.lista) };
    protected lateinit var adapter: TelefonoAdapter
    protected lateinit var listaTelefonos: ArrayList<Telefono>
   //PropietarioActual almacena el propietario para el telefono
    // que se va a insertar/Modificar
    protected var propietarioActual:String?=null;
    protected var modeloAnterior:String?=null;

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

    abstract fun consultarBD();

    abstract fun borrarElementoBD(pk:String);


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}