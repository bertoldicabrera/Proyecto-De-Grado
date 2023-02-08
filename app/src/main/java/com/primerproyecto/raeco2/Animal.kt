package com.primerproyecto.raeco2


public class Animal(
    private var nombre: String?,
    private  var descripcion: String?,
    private var link:String?,
    private var objetoBackUp:String?,
    private var objeto3D:String?,
    private var region: String?,
    private var sonido: String?
)
{

    fun obtenerAnimal(): Animal {

        return this
    }

    fun obtenerNombreAnimal(): String? {

        return this.nombre
    }
    fun obtenerDescripcionAnimal(): String? {

        return this.descripcion
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

    fun obtenerRegionAnimal(): String? {

        return this.region
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

    fun setearRegionAnimal(idRegion: String?) {

        this.region =idRegion
    }

}