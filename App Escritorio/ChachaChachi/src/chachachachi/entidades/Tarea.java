/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chachachachi.entidades;

/**
 *
 * @author user
 */
public class Tarea {
    int p_tarea;
    String texto;
    int duracion;
    String icono="icono.png";

    public Tarea(int p_tarea, String texto, int duracion) {
        this.p_tarea = p_tarea;
        this.texto = texto;
        this.duracion = duracion;
    }

    public int getP_tarea() {
        return p_tarea;
    }

    public String getTexto() {
        return texto;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setP_tarea(int p_tarea) {
        this.p_tarea = p_tarea;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
    
    @Override
    public String toString() {
        return texto;
    }
}
