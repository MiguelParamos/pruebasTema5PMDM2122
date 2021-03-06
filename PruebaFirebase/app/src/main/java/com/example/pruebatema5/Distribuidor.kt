package com.example.pruebatema5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pruebatema5.databinding.ActivityDistribuidorBinding
import servicios.ServicioMolestador

class Distribuidor : AppCompatActivity() {

    val binding by lazy{
        ActivityDistribuidorBinding.inflate(this.layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.botonPruebaFirebase.setOnClickListener {
            val intent: Intent =Intent(
                this@Distribuidor,
            PantallaCRUDFirebase::class.java)
            startActivity(intent)
        }

        binding.botonPruebasSQLite.setOnClickListener {
            val intent: Intent=Intent(this@Distribuidor,
            PantallaCRUDSQLite::class.java)
            startActivity(intent)
        }

        binding.botonPruebasPreferencias.setOnClickListener {
            val intent: Intent=Intent(this@Distribuidor,
                PreferenciasDisenioPropio::class.java)
            startActivity(intent)
        }

        binding.botonIniciarServicio.setOnClickListener {
            val intent:Intent=Intent(this@Distribuidor,
                ServicioMolestador::class.java)
            this.startService(intent)
        }

        binding.botonPruebasFragments.setOnClickListener {
            val intent:Intent=Intent(this@Distribuidor,
                ActividadFragments::class.java)
            this.startActivity(intent)
        }
    }
}