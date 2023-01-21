package com.primerproyecto.raeco2


public class Animal(
    var nombre: String?,
    var descripcion: String?,
    var url:String?,
    var objeto3D:String?,
    var region: Int?
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

        return this.url
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

        this.url =link
    }
    fun setearObjetoAnimal(obj: String?) {

        this.objeto3D =obj
    }

    fun setearRegionAnimal(idregion: Int?) {

        this.region =idregion
    }

}