package com.primerproyecto.raeco2

public class Configuracion(
    private var titulo: Boolean,
    private var link: Boolean,
    private var renderizado: Boolean,
    private var sonido: Boolean

) {
    /*
    companion object {
        fun apply(
            titulo: Boolean? = null,
            link: Boolean? = null,
            renderizado: Boolean? = null,
            sonido: Boolean? = null
        ): Configuracion {
            return Configuracion(titulo, link, renderizado, sonido)
        }
    }
*/

    fun seterTitulo(varTitulo: Boolean){
        this.titulo =varTitulo

    }
    fun seterLink(varLink: Boolean){
        this.link = varLink

    }
    fun seterRenderizado(varTamano: Boolean){
        this.renderizado = varTamano
    }

    fun seterSonido(varSonido: Boolean){
        this.sonido = varSonido
    }
    fun esTituloActivado(): Boolean {
        return this.titulo

    }
    fun esLinkActivado(): Boolean {
        return this.link

    }
    fun esRenderizadoActivado(): Boolean {
        return this.renderizado
    }

    fun esSonidoActivado(): Boolean {
        return this.sonido
    }



}