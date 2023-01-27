package com.primerproyecto.raeco2

class Configuracion(
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
    fun obtenerTitulo(): Boolean? {
        return this.titulo

    }
    fun obtenerLink(): Boolean? {
        return this.link

    }
    fun obtenerTamano(): Boolean? {
        return this.tamano
    }

    fun obtenerSonido(): Boolean? {
        return this.sonido
    }



}