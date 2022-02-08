package sqlite

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.pruebatema5.PantallaCRUD

class TelefonoOpenHelper(contexto:PantallaCRUD) :
    SQLiteOpenHelper(contexto,"bdTelefonos",null,3) {
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
                columnaPrecio+" float," +
                "columnaFalsa varchar(2) default null,"+
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
        var versionInicial=p1;
        if(versionInicial==1){ //Actualización de v1 a v2
           //Hay que montar este pitoste porque SQLite no tiene cambiar tipos a las tablas
               //Creo tabla auxiliar
               p0?.execSQL("create table "+ Companion.tablaTelefonos+ "Aux ("+
                       columnaModelo+" varchar(100)," +
                       columnaPrecio+" int(4)," +
                       columnaEsNuevo+" boolean," +
                       columnaCuandoComprado+" date," +
                       columnaPropietario+" varchar(200)," +
                       "foreign key ("+ columnaPropietario+
                       ") references usuarios("+ columnaEmail+")" +
                       "primary key("+ columnaPropietario+","+ columnaModelo+"));")
              //Vuelco todo  a la tabla auxiliar
              p0?.execSQL("insert into "+Companion.tablaTelefonos+"Aux select * from "
                      +Companion.tablaTelefonos)
              //borro la tabla original
              p0?.execSQL("drop table "+ Companion.tablaTelefonos)
            //Vuelvo a crear la tabla original con el nuevo tipo
            p0?.execSQL("create table "+ Companion.tablaTelefonos+ " ("+
                    columnaModelo+" varchar(100)," +
                    columnaPrecio+" float," +
                    columnaEsNuevo+" boolean," +
                    columnaCuandoComprado+" date," +
                    columnaPropietario+" varchar(200)," +
                    "foreign key ("+ columnaPropietario+
                    ") references usuarios("+ columnaEmail+")" +
                    "primary key("+ columnaPropietario+","+ columnaModelo+"));")

            //Vuelco los datos desde la auxiliar a la nueva
            p0?.execSQL("insert into "+Companion.tablaTelefonos+" select * from "
                    +Companion.tablaTelefonos+"Aux")
            //Borro la tabla auxiliar
            p0?.execSQL("drop table " +Companion.tablaTelefonos+"Aux"
            )
            versionInicial=2;
        }
        if(versionInicial==2){
            p0?.execSQL("alter table "+Companion.tablaTelefonos+
                    " add column columnaFalsa varchar(2) default null")
            versionInicial=3;
        }


    }
}