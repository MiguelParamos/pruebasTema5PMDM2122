package com.example.pruebatema5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
            val tarea=
                auth.signInWithEmailAndPassword(
                email.text.toString(),
                contraseña.text.toString()
            )
            tarea.addOnCompleteListener{
                if(it.isSuccessful){
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
}