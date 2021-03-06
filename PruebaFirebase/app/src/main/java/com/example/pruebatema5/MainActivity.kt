package com.example.pruebatema5

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*

class MainActivity : AppCompatActivity() {

    val botonLogin:Button by lazy{
        findViewById<Button>(R.id.botonLogin)
    }
    val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val email:EditText=findViewById<EditText>(R.id.campoEmail)
        val contraseña:EditText=findViewById<EditText>(R.id.campoContraseña)
        //Obtenemos las preferencias para leer
        val prefs:SharedPreferences=
            this.getSharedPreferences(
            "com.example.pruebatema5.PreferenciasDisenioPropio", MODE_PRIVATE)

            //Si no encuentra alguno de los dos, pone el valor del segundo argumento
            //que es la cadena vacía
            email.setText(prefs.getString("usuarioPorDefecto",""))
            contraseña.setText(prefs.getString("contraseñaPorDefecto",""))


        val botonRegistro: Button =
            findViewById<Button>(R.id.botonRegistro)
        botonRegistro.setOnClickListener {
            if(!email.text.isBlank()&&!contraseña.text.isBlank()) {
               val tarea = auth.createUserWithEmailAndPassword(
                email.text.toString(), contraseña.text.toString())

                    tarea.addOnCompleteListener(this,
                        object : OnCompleteListener<AuthResult> {
                            override fun onComplete(p0: Task<AuthResult>) {

                                if (tarea.isSuccessful) {
                                    Toast.makeText(
                                        this@MainActivity,
                                        getString(R.string.registroCompletado)
                                                +" "+auth.currentUser?.email.toString(),
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    try {
                                        throw tarea.exception!!
                                    } catch (e: FirebaseAuthWeakPasswordException) {
                                        Toast.makeText(
                                            this@MainActivity,
                                            R.string.contraseñaDebil,
                                            Toast.LENGTH_LONG
                                        ).show()
                                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                                        Toast.makeText(
                                            this@MainActivity,
                                            R.string.credencialesInvalidos,
                                            Toast.LENGTH_LONG
                                        ).show()
                                    } catch (e: FirebaseAuthUserCollisionException) {
                                        Toast.makeText(
                                            this@MainActivity,
                                            R.string.colisionUsuario,
                                            Toast.LENGTH_LONG
                                        ).show()
                                    } catch (e: Exception) {
                                        Toast.makeText(
                                            this@MainActivity,
                                            R.string.errorDesconocido,
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }


                            }
                        })
                }else{
                    Toast.makeText(
                        this@MainActivity,
                        R.string.camposRegistroNoRellenos,
                        Toast.LENGTH_LONG
                    ).show()
                }

        }

        botonLogin.setOnClickListener{
            //Si el usuario con el que hago login no es el que ya tenía guardado,

            val tarea=
                auth.signInWithEmailAndPassword(
                email.text.toString(),
                contraseña.text.toString()
            )
            tarea.addOnCompleteListener{
                if(it.isSuccessful){
                    //Si he podido iniciar sesión con un email que no es el de preferencias,
                    //borro el de preferencias por seguridad del otro usuario
                    if(!prefs.getString("usuarioPorDefecto","").equals(email.text.toString())){
                        val prefsEditor:SharedPreferences.Editor=prefs.edit()
                        prefsEditor.remove("usuarioPorDefecto");
                        prefsEditor.remove("contraseñaPorDefecto")
                    }
                    val user=auth.currentUser;
                    Toast.makeText(this@MainActivity
                        , getString(R.string.loginOk)+" "+
                        user?.email.toString(), Toast.LENGTH_LONG)
                        .show()
                    val intent: Intent =
                        Intent(this@MainActivity,
                    Distribuidor::class.java)
                    startActivity(intent)
                }else{
                    try{
                        throw tarea.exception!!;
                    }catch (ex:FirebaseAuthInvalidUserException){
                        Toast.makeText(this@MainActivity,
                            R.string.usuarioNoExiste,
                            Toast.LENGTH_LONG).show()
                    }catch(ex:FirebaseAuthInvalidCredentialsException){
                        Toast.makeText(this@MainActivity,
                            R.string.contraseñaInvalida,
                            Toast.LENGTH_LONG).show()
                    }
                }
            }

        }

        val botonRecuperarContraseña=
            findViewById<Button>(R.id.botonRecuperarContraseña)
            botonRecuperarContraseña.setOnClickListener {
            val tarea=auth.sendPasswordResetEmail(email.text.toString())
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(this@MainActivity,
                            R.string.emailRecuperacionEnviado,
                            Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this@MainActivity,
                            R.string.emailRecuperacionFallo,
                            Toast.LENGTH_LONG).show()
                    }
                }
        }




    }

    override fun onStart() {
        super.onStart()
        val prefs:SharedPreferences=
            this.getSharedPreferences(
                "com.example.pruebatema5.PreferenciasDisenioPropio",
                MODE_PRIVATE)

        if(prefs.getBoolean("modoOscuro",false)){
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}