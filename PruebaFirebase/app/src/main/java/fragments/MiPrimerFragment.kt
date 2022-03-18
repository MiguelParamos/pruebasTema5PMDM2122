package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.pruebatema5.R
import com.example.pruebatema5.databinding.ActivityDistribuidorBinding
import com.example.pruebatema5.databinding.MiprimerfragmentLayoutBinding

class MiPrimerFragment() : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //El primer argumento es lo que se infla
        //El segundo argumento es donde se infla, que como no lo sabemos, porque depende
        //de desde donde se invoque la función será en una actividad u otra.
        //El tercer argumento siempre es false para que lo instancie android por dentro donde le toque

        return inflater.inflate(R.layout.miprimerfragment_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val boton:Button = view.findViewById(R.id.botonTelefono)
        val campo:EditText= view.findViewById<EditText>(R.id.campoTelefono)

        boton.setOnClickListener {
            Toast.makeText(this.context, campo.text.toString(), Toast.LENGTH_SHORT).show()
            val fragmentManager:FragmentManager?=this.activity?.supportFragmentManager
            val transaccion=fragmentManager?.beginTransaction()
            transaccion?.replace(R.id.huecoFragmentInferior,MiTercerFragment(),
                "miTercerFragment")
            transaccion?.commit()

        }

    }
}