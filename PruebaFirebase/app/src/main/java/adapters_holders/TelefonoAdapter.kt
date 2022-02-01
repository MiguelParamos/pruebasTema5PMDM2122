package adapters_holders

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import clases.Telefono
import com.example.pruebafirebase.PantallaCRUDFirebase
import com.example.pruebafirebase.R
import com.google.firebase.firestore.FirebaseFirestore

class TelefonoAdapter(
    val contexto: PantallaCRUDFirebase,
    val elementos:ArrayList<Telefono>,
    val bd:FirebaseFirestore):
    RecyclerView.Adapter<TelefonosHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TelefonosHolder {
        return TelefonosHolder(contexto.layoutInflater.inflate(
            R.layout.elemento_telefonos,parent,false));
    }

    override fun onBindViewHolder(holder: TelefonosHolder, position: Int) {
        holder.txtModelo.text=elementos.get(position).modelo
        holder.txtFecha.text=elementos.get(position).fechaCompra
        holder.txtPrecio.text=""+elementos.get(position).precio+" €"
        holder.txtPropietario.text=elementos.get(position).propietario
        if(elementos.get(position).esNuevo){
            holder.txtEsNuevo.text=contexto.
            getString(R.string.nuevo);
        }else{
            holder.txtEsNuevo.text=contexto.
                getString(R.string.usado)
        }

        holder.botonBorrar.setOnClickListener {
            Toast.makeText(contexto,holder.txtPropietario.text.toString(),
                Toast.LENGTH_LONG).show()
            bd.collection("telefonos").
            document(holder.txtPropietario.text.toString()+" - "
            +holder.txtModelo.text.toString()).delete()
            contexto.actualizarAdapter()

        }

        holder.botonEditar.setOnClickListener {
            contexto.rellenarCampos(
                elementos.get(position).modelo,
                elementos.get(position).precio,
                elementos.get(position).fechaCompra,
                elementos.get(position).esNuevo,
                elementos.get(position).propietario
            )
        }
    }

    override fun getItemCount(): Int {
        return elementos.size
    }
}