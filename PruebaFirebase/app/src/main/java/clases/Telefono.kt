package clases

class Telefono(val modelo:String,
               val precio:Float,
               val fechaCompra:String,
               val esNuevo:Boolean,
               val propietario:String) {

    constructor():this("",0f,"",false,""){

    }
}