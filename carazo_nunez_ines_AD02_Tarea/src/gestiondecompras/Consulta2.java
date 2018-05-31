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
public class Consulta2 {

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
            //2. Nombre de sección y precio medio de sus productos, teniendo en 
            //cuenta únicamente aquellos productos cuyo pvp>11.
            ResultSet result = sentencia.executeQuery("SELECT S.NOMBRE, AVG(P.PVP)  FROM  SECCIONES S, PRODUCTOS P WHERE S.P_SECCION=P.A_SECCION and P.PVP>11  GROUP BY S.NOMBRE;");
            
            System.out.println("\n     NOMBRE     PVP(MEDIO)");
            System.out.println("     ******     *********\n");
            
            while (result.next()) {
                System.out.println("     " + result.getString(1) + "     "+ result.getFloat(2));
            }
            
            sentencia.close();
            
            conexion.close();
            
        } catch (SQLException e) {
        }
    }
}
