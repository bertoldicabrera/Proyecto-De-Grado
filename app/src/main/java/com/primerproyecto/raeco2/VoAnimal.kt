package com.primerproyecto.raeco2

class VoAnimal (
    private var nombre: String?,
    private  var descripcion: String?,
    private var link:String?,
    private var objetoBackUp:String?,
    private var objeto3D:String?,
    private var sonido: String?
)
{
    fun obtenerNombreAnimal(): String? {

        return this.nombre
    }

    fun obtenerLinkAnimal(): String? {

        return this.link
    }
    fun obtenerObjetoBackUpAnimal(): String? {

        return this.objetoBackUp
    }
    fun obtenerObjetoAnimal(): String? {

        return this.objeto3D
    }

    fun obtenerSonido(): String? {

        return this.sonido
    }

    fun setearSonido(son: String?) {

        this.sonido= son
    }

    fun setearNombreAnimal(name :String?) {

        this.nombre =name
    }
    fun setearDescripcionAnimal(desc: String?) {

        this.descripcion =desc
    }
    fun setearLinkAnimal(link: String?) {

        this.link =link
    }
    fun setearUrlBackUpAnimal(link: String?) {

        this.objetoBackUp =link
    }
    fun setearObjetoAnimal(obj: String?) {

        this.objeto3D =obj
    }



}