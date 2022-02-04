package sqlite

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.pruebatema5.PantallaCRUD

class TelefonoOpenHelper(contexto:PantallaCRUD) :
    SQLiteOpenHelper(contexto,"bdTelefonos",null,1) {
    companion object{
        val tablaUsuarios:String="usuarios"
        val columnaEmail:String="email"
        val columnaContrasenia:String="contrasenia"
        val tablaTelefonos:String="telefonos"
        val columnaModelo:String="modelo"
        val columnaPrecio:String="precio"
        val columnaEsNuevo:String="esNuevo"
        val columnaCuandoComprado:String="cuandoComprado"
        val columnaPropietario:String = "propietario"
    }
    override fun onCreate(p0: SQLiteDatabase?) {
        //Solo se ejecuta al llamar por primera vez a
        // getWritableDatabase / getReadableDatabase tras instalar la app
        p0?.execSQL("create table "+tablaUsuarios+"(" +
                columnaEmail+" varchar(200) primary key," +
                columnaContrasenia+" varchar(100));");

        p0?.execSQL("create table "+ Companion.tablaTelefonos+ " ("+
                columnaModelo+" varchar(100)," +
                columnaPrecio+" int(4)," +
                columnaEsNuevo+" boolean," +
                columnaCuandoComprado+" date," +
                columnaPropietario+" varchar(200)," +
                "foreign key ("+ columnaPropietario+
                ") references usuarios("+ columnaEmail+")" +
                "primary key("+ columnaPropietario+","+ columnaModelo+"));");
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        //Solo se ejecuta al llamar por primera vez a
        //getWritableDatabase / getReadableDatabase tras cambiar el nº de versión
        //En el constructor
    }
}