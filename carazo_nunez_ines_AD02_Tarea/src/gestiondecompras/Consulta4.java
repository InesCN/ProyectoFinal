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
public class Consulta4 {

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
            //4. Nombre de la sección con el mayor precio medio.
            ResultSet result = sentencia.executeQuery("SELECT C3.NOMBRE, C2.MAXIMA FROM(SELECT MAX(C1.MEDIA) MAXIMA FROM (SELECT S.NOMBRE NOMBRE, AVG(P.PVP) MEDIA FROM  SECCIONES S, PRODUCTOS P WHERE S.P_SECCION=P.A_SECCION GROUP BY S.NOMBRE) C1)C2, (SELECT S.NOMBRE NOMBRE, AVG(P.PVP) MEDIA FROM  SECCIONES S, PRODUCTOS P WHERE S.P_SECCION=P.A_SECCION GROUP BY S.NOMBRE ORDER BY MEDIA)C3 WHERE C2.MAXIMA=C3.MEDIA;");

            System.out.println("\n     SECCIÓN     MEDIA(MÁX)");
            System.out.println("     *******     **********");

            while (result.next()) {
                System.out.println("     " + result.getString(1) + "     " + result.getFloat(2));
            }
            
            sentencia.close();
            
            conexion.close();
            
        } catch (SQLException e) {
        }
    }
}
