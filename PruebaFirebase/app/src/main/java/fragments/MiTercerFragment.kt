package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.pruebatema5.R

class MiTercerFragment() : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.mitercerfragment_layout,container,
        false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val botonNuclear: Button = view.findViewById(R.id.botonNuclear)
        botonNuclear.setOnClickListener {
                val fragmentManager: FragmentManager?= this.activity?.supportFragmentManager
                val transaccion = fragmentManager?.beginTransaction()
                transaccion?.remove(
                    fragmentManager.findFragmentByTag("miTercerFragment")!!)
                transaccion?.remove(
                    fragmentManager.findFragmentByTag("miPrimerFragment")!!)

                transaccion?.commit()
        }
    }
}