/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondecompras;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Inés Carazo Núñez
 */
public class Consulta1 {

    public static void main(String[] args) {
        try {
            //Creamos la conexión
            Connection conexion = Conexiones.conexionMySQL();
            //Creamos la sentencia
            Statement sentencia = conexion.createStatement();
            //Usar la base de datos
                String usarBD = "USE Supermercado_r;";
                sentencia.executeUpdate(usarBD);
            //Creamos la sentencia SELECT para la consulta:
            //1. Nombre de sección, descripción de producto, pvp, stock de los 
            //productos para productos cuyo pvp>11.
            ResultSet result = sentencia.executeQuery("SELECT S.NOMBRE, P.DESCRIPCION, P.PVP, P.STOCK FROM SECCIONES S, PRODUCTOS P WHERE S.P_SECCION=P.A_SECCION AND P.PVP>11");

            System.out.println("\n     NOMBRE       DESCRIPCIÓN     PVP     STOCK");
            System.out.println("     ******       ***********     ***     *****\n");

            while (result.next()) {
                System.out.println("     " + result.getString(1) + "        " + result.getString(2) + "         " + result.getFloat(3) + "   " + result.getInt(4));
            }
            
            sentencia.close();
            
            conexion.close();
            
        } catch (SQLException e) {
        }
    }
}
