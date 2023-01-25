package com.primerproyecto.raeco2

class voConfiguracion (
    private var titulo: Boolean?,
    private var link: Boolean?,
    private var tamano: Boolean?,
    private var sonido: Boolean?

) {

    fun seterTitulo(varTitulo: Boolean?){
        this.titulo =varTitulo

    }
    fun seterLink(varLink: Boolean?){
        this.link = varLink

    }
    fun seterTamano(varTamano: Boolean?){
        this.tamano = varTamano
    }

    fun seterSonido(varSonido: Boolean?){
        this.sonido = varSonido
    }
    fun getTitulo(): Boolean? {
        return this.titulo

    }
    fun getLink(): Boolean? {
        return this.link

    }
    fun getTamano(): Boolean? {
        return this.tamano
    }

    fun getSonido(): Boolean? {
        return this.sonido
    }



}