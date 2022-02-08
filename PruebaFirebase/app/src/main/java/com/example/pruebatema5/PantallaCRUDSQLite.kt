package com.example.pruebatema5

import adapters_holders.TelefonoAdapter
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.core.database.getStringOrNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import clases.Telefono
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import sqlite.TelefonoOpenHelper

class PantallaCRUDSQLite : PantallaCRUD() {
    val openHelper:TelefonoOpenHelper = TelefonoOpenHelper(this)


   override fun consultarBD() {
        val db:SQLiteDatabase=openHelper.writableDatabase
        val cursor: Cursor =db.query(TelefonoOpenHelper.tablaTelefonos,
            null,null,null,
        null,null,TelefonoOpenHelper.columnaModelo+" desc")
       listaTelefonos.clear()
        while(cursor.moveToNext()){
            val modelo:String=cursor.getString(
                cursor.getColumnIndexOrThrow(TelefonoOpenHelper.columnaModelo))
            val precio:Float=cursor.getFloat(
                cursor.getColumnIndexOrThrow(TelefonoOpenHelper.columnaPrecio))
            val cuandoComprado:String=cursor.getString(
                cursor.getColumnIndexOrThrow(TelefonoOpenHelper.columnaCuandoComprado)
            )
            //El !=0 es porque en SQL 0 es False, y true es cualquier otra cosa
             val esNuevo:Boolean=cursor.getInt(
                cursor.getColumnIndexOrThrow(TelefonoOpenHelper.columnaEsNuevo))!=0
            val propietario:String? =cursor.getStringOrNull(
                cursor.getColumnIndexOrThrow(TelefonoOpenHelper.columnaPropietario)
            )

            val tel:Telefono= Telefono(modelo,precio.toFloat(),
                cuandoComprado,esNuevo,propietario?:"")
            this.listaTelefonos.add(tel)
        }
        this.rellenaElementosLista()
    }

    override fun borrarElementoBD(pk: String) {
        val emailModelo:List<String> =pk.split(" - ")
        val db:SQLiteDatabase=openHelper.writableDatabase

        //db.delete(tabla, where sin where, sustitución de ? )
        val devuelto:Int=db.delete(TelefonoOpenHelper.tablaTelefonos, //"propietario = ? and modelo = ?"
            TelefonoOpenHelper.columnaPropietario+" = ? and "+
        TelefonoOpenHelper.columnaModelo+" = ?",
            emailModelo.toTypedArray()
        );
        Toast.makeText(this, "Borrados: "+devuelto, Toast.LENGTH_SHORT).show()
        this.actualizarAdapter()
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
                    val db:SQLiteDatabase=openHelper.writableDatabase
                    val valores:ContentValues= ContentValues()
                    valores.put(TelefonoOpenHelper.columnaModelo,
                        campoModelo.text.toString())
                    valores.put(
                        TelefonoOpenHelper.columnaPrecio,
                        campoPrecio.text.toString().toFloat()
                    )
                    valores.put(
                        TelefonoOpenHelper.columnaCuandoComprado,
                        campoCuandoComprado.text.toString()
                    )
                    valores.put(
                        TelefonoOpenHelper.columnaEsNuevo
                        , campoNuevo.isChecked)
                    //No pongo propietario hasta que no tengamos uno registrado


                    //ARREGLO CHAPUCERO PARA PODER PROBAR DELETE
                    var cv2:ContentValues= ContentValues()
                    cv2.put(TelefonoOpenHelper.columnaEmail,"hola@hola.com")
                    cv2.put(TelefonoOpenHelper.columnaContrasenia,"123123")
                    db.insert(TelefonoOpenHelper.tablaUsuarios,
                    null,cv2);
                    this.actualizarAdapter()
                    //FIN DEL ARREGLO CHAPUCERO PARA PODER PROBAR DELETE

                    valores.put(TelefonoOpenHelper.columnaPropietario,"hola@hola.com")

                    db.insert(TelefonoOpenHelper.tablaTelefonos,
                        null,valores)




                }else{ //Editar el teléfono
                  val db:SQLiteDatabase=openHelper.writableDatabase
                    var valores:ContentValues=ContentValues()
                    valores.put(TelefonoOpenHelper.columnaModelo,
                        campoModelo.text.toString())
                    valores.put(
                        TelefonoOpenHelper.columnaPrecio,
                        campoPrecio.text.toString().toFloat()
                    )
                    valores.put(
                        TelefonoOpenHelper.columnaCuandoComprado,
                        campoCuandoComprado.text.toString()
                    )
                    valores.put(
                        TelefonoOpenHelper.columnaEsNuevo
                        , campoNuevo.isChecked)
                    db.update(TelefonoOpenHelper.tablaTelefonos,valores,
                    //ÑAPA PORQUE NO TENGO CREACIÓN DE USUARIOS; Y  PONGO UNO A PELO
                    TelefonoOpenHelper.columnaPropietario+"= \"hola@hola.com\" and "+
                    TelefonoOpenHelper.columnaModelo+" = \""+modeloAnterior+"\"",null)
                    this.actualizarAdapter()
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
        adapter = TelefonoAdapter(this, this.listaTelefonos)
        lista.layoutManager = LinearLayoutManager(this)
        lista.adapter = adapter
    }
}