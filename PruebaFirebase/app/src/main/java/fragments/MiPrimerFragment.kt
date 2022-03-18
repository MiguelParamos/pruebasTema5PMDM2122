package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pruebatema5.R

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
}