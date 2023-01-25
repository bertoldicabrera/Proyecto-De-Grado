package com.primerproyecto.raeco2

class voAnimal (
    private var nombre: String?,
    private var descripcion: String?,
    private var url:String?,
    private var objeto3D:String?,
    private var region: Int?,
    private var sonido: String?
)
{

    fun obtenerAnimal(): voAnimal {

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