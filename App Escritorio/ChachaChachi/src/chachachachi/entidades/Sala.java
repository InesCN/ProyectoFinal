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
public class Sala {
    int p_sala;
    String nombre;

    public Sala(int p_sala, String nombre) {
        this.p_sala = p_sala;
        this.nombre = nombre;
    }

    public int getP_sala() {
        return p_sala;
    }

    public void setP_sala(int p_sala) {
        this.p_sala = p_sala;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}
