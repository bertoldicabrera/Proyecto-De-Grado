package com.primerproyecto.raeco2


public class Animal(
    private var nombre: String?,
    private  var descripcion: String?,
    private var urlPrincipal:String?,
    private var urlBackUp:String?,
    private var objeto3D:String?,
    private var region: Int?
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
    fun obtenerUrlAnimal(): String? {

        return this.urlPrincipal
    }
    fun obtenerUrlBackUpAnimal(): String? {

        return this.urlBackUp
    }
    fun obtenerObjetoAnimal(): String? {

        return this.objeto3D
    }

    fun obtenerRegionAnimal(): Int? {

        return this.region
    }

    fun setearNombreAnimal(name :String?) {

        this.nombre =name
    }
    fun setearDescripcionAnimal(desc: String?) {

        this.descripcion =desc
    }
    fun setearUrlAnimal(link: String?) {

        this.urlPrincipal =link
    }
    fun setearUrlBackUpAnimal(link: String?) {

        this.urlBackUp =link
    }
    fun setearObjetoAnimal(obj: String?) {

        this.objeto3D =obj
    }

    fun setearRegionAnimal(idregion: Int?) {

        this.region =idregion
    }

}