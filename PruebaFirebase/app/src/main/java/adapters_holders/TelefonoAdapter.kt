package adapters_holders

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import clases.Telefono
import com.example.pruebafirebase.R

class TelefonoAdapter(
    val contexto: Activity,val elementos:ArrayList<Telefono>):
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
    }

    override fun getItemCount(): Int {
        return elementos.size
    }
}