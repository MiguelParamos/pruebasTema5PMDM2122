package com.example.pruebatema5

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.pruebatema5.databinding.ActivityPreferenciasDisenioPropioBinding

class PreferenciasDisenioPropio : AppCompatActivity() {

    val binding by lazy{
        ActivityPreferenciasDisenioPropioBinding.inflate(this.layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //Este sirve para leer preferencias
        var preferencias: SharedPreferences =
            this.getPreferences(MODE_PRIVATE)

        //Este vale para escribir preferencias
        var prefEditor: SharedPreferences.Editor=
            preferencias.edit()

        val usuario:String=binding.campoUsuarioPorDefecto.text.toString()
        val contraseña:String=binding.campoContraseAPorDefecto.text.toString()

        binding.campoUsuarioPorDefecto.setText(
            preferencias.getString("usuarioPorDefecto",""))
        binding.campoContraseAPorDefecto.setText(
            preferencias.getString("contraseñaPorDefecto",""))

        binding.botonAplicarPrefencias.setOnClickListener {


            prefEditor.putString("usuarioPorDefecto",binding.campoUsuarioPorDefecto.text.toString());
            prefEditor.putString("contraseñaPorDefecto",binding.campoContraseAPorDefecto.text.toString());

            prefEditor.commit()

            Toast.makeText(this,R.string.preferenciasAplicadas,
                Toast.LENGTH_LONG).show()

        }

        binding.botonPreferenciasAlternativas.setOnClickListener {
            val intent: Intent =Intent(this@PreferenciasDisenioPropio,
            PreferenciasAlternativasActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        val prefs:SharedPreferences=this.getPreferences(MODE_PRIVATE)
        if(prefs.getBoolean("modoOscuro",false)){
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}