/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondecompras;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Inés Carazo Núñez
 */
public class Consulta5 {

    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
         //Creamos la conexión
                Connection conexion = Conexiones.conexionMySQL();
                //Creamos la sentencia
         Statement sentencia = conexion.createStatement();
         //Usar la base de datos
                String usarBD = "USE Supermercado_r;";
                sentencia.executeUpdate(usarBD);
                //Creamos la sentencia SELECT para la consulta:
                //5. Igual que la primera de tipo preparada. De tal forma que el 
                //programa nos solicita que le demos el nombre de la sección de la 
                //que queremos los datos y el programa nos devuelve los datos por 
                //consola. Si el nombre de sección no existe nos lo comunica mediante 
                //mensaje y nos sigue pidiendo nombres.
                //El programa termina cuando tecleemos retorno vacío.
 
        String nombreSeccion = "";
        boolean stop = false;
        boolean exists = false;
        while (!stop) {

            //Pedimos el nombre de la sección
            try {
                exists = false;
                System.out.print("\n**Introduzca el nombre de la Sección\n");
                nombreSeccion = br.readLine();
                if (nombreSeccion.equals("")) {
                    stop = true;
                    break;
                }
               
                ResultSet result = sentencia.executeQuery("SELECT S.NOMBRE, P.DESCRIPCION, P.PVP, P.STOCK  FROM SECCIONES S, PRODUCTOS P WHERE (S.P_SECCION=P.A_SECCION AND P.PVP>11)AND NOMBRE='" + nombreSeccion + "'");

                //Mostramos el resultado de la consulta
                if (result.isBeforeFirst()) {
                    System.out.println("\n     NOMBRE     DESCRIPCION     PVP     STOCK");
                    System.out.println("     ******     ***********     ***     *****");

                    while (result.next()) {
                        System.out.println("     " + result.getString(1) + "     " + result.getString(2) + "          " + result.getFloat(3) + "      " + result.getInt(4));

                        exists = true;
                    }
                }

                System.out.println("     ****************************************");

                sentencia.close();

                conexion.close();
                //Mensaje que se muestra cuando no existe la Sección
                if (!exists) {
                    System.out.println("**La Sección con nombre *" + nombreSeccion + "* no existe ***");
                    System.out.println("PRUEBA OTRA VEZ");
                    System.out.println("     ****************************************\n");
                }

            } catch (SQLException e) {

            } catch (IOException ex) {
                Logger.getLogger(Consulta5.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
