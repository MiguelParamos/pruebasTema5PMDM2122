package com.example.pruebatema5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import fragments.MiSegundoFragment

class ActividadFragments : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad_fragments)
        //Para poner un fragment desde código lo primero es traer
        //una variable FragmentManager que lo gestione
        var fragmentManager:FragmentManager= this.supportFragmentManager

        //Después creo el fragment que quiera poner en la activdad
        val fragmentTipo2:MiSegundoFragment= MiSegundoFragment();

        //Cuarto, inicializamos una transacción
        //Las transacciones aseguran que si tenemos que cambiar varios fragment a la vez,
        //Lo hagamos realmente a la vez
        val transaccion: FragmentTransaction=fragmentManager.beginTransaction()
        transaccion.add(R.id.huecoFragmentInferior,fragmentTipo2,
            "fragment2inferior")
        transaccion.commit()

    }
}