package adapters_holders

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebafirebase.R

class TelefonosHolder(it:View) : RecyclerView.ViewHolder(it) {
    val txtModelo: TextView by lazy{ it.findViewById(R.id.txtModelo)}
    val txtPrecio: TextView by lazy{ it.findViewById(R.id.txtPrecio)}
    val txtFecha: TextView by lazy{it.findViewById(R.id.txtFechaCompra)}
    val txtPropietario: TextView by lazy{it.findViewById(R.id.txtPropietario)}
    val txtEsNuevo: TextView by lazy{it.findViewById(R.id.txtEsNuevo)}
    val botonEditar: ImageView by lazy{it.findViewById(R.id.botonEditar)}
    val botonBorrar: ImageView by lazy{it.findViewById(R.id.botonBorrar)}
}