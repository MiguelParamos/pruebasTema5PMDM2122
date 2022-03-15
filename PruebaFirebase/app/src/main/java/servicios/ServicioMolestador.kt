package servicios

import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import com.example.pruebatema5.R
import java.util.*

class ServicioMolestador : Service() {

    override fun onBind(intent: Intent): IBinder {
        return onBind(intent);
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand
    (intent: Intent?,flags: Int, startId: Int): Int {

        val handler = Handler(Looper.getMainLooper())
        val delay = 1000 // 1000 milliseconds == 1 second


        handler.postDelayed(object : Runnable {
            override fun run() {
                Toast.makeText(
                    this@ServicioMolestador,
                    R.string.soyUnServicio,
                    Toast.LENGTH_LONG
                ).show()
                handler.postDelayed(this, delay.toLong())
            }
        }, delay.toLong())

        //Con esto, si en algún momento se necesita, se puede parar el servicio aquí
        //o desde la actividad.
        //this.stopSelf()


        return Service.START_STICKY
    }
}