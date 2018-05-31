/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondecompras;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Inés Carazo Núñez
 */
public class CrearDatos {

    public static void main(String[] args) {
        int n = 0;
        try {
            //Iniciamos la sentencia
            try ( //Creamos la conexion con la clase Conexiones
                    Connection conexion = Conexiones.conexionMySQL()) {
                //Iniciamos la sentencia
                Statement sentencia = conexion.createStatement();

                //CREACIÓN DE LA BASE DE DATOS si no existe
                n = sentencia.executeUpdate("CREATE DATABASE IF NOT EXISTS Supermercado_r;");

                //Usar la base de datos
                String usarBD = "USE Supermercado_r;";
                n = sentencia.executeUpdate(usarBD);

                System.out.println("Se ha creado la base de datos");

                //CREACIÓN DE LA TABLA USUARIOS
                sentencia = conexion.createStatement();
                String usuarios = "CREATE TABLE IF NOT EXISTS USUARIOS(P_USUARIO INT(8) NOT NULL PRIMARY KEY,NOMBRE VARCHAR(10),CONTRASENA VARCHAR(8))ENGINE=InnoDB;";
                n = sentencia.executeUpdate(usuarios);

                //CREACIÓN DE LA TABLA CARRITOS
                sentencia = conexion.createStatement();
                String carritos = "CREATE TABLE IF NOT EXISTS CARRITOS(P_CARRITO INT(8) NOT NULL PRIMARY KEY,A_USUARIO INT(8),FECHA DATE,FOREIGN KEY(A_USUARIO)REFERENCES USUARIOS(P_USUARIO)ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;";
                n = sentencia.executeUpdate(carritos);

                //CREACIÓN DE LA TABLA SECCIONES
                sentencia = conexion.createStatement();
                String secciones = "CREATE TABLE IF NOT EXISTS SECCIONES(P_SECCION INT(8) NOT NULL PRIMARY KEY,NOMBRE VARCHAR(10))ENGINE=InnoDB;";
                n = sentencia.executeUpdate(secciones);

                //CREACIÓN DE LA TABLA PRODUCTOS
                sentencia = conexion.createStatement();
                String productos = "CREATE TABLE IF NOT EXISTS PRODUCTOS(P_PRODUCTO INT(8) NOT NULL PRIMARY KEY,A_SECCION INT(8),DESCRIPCION VARCHAR(20),PVP FLOAT(6,2),STOCK INT(4),FOREIGN KEY(A_SECCION)REFERENCES SECCIONES(P_SECCION)ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;";
                n = sentencia.executeUpdate(productos);

                //CREACIÓN DE LA TABLA CARR_PRO
                sentencia = conexion.createStatement();
                String carr_pro = "CREATE TABLE IF NOT EXISTS CARR_PRO(P_CARR_PRO INT(8) NOT NULL PRIMARY KEY,A_CARRITO INT(8),A_PRODUCTO INT(8),CANTIDAD INT(4),FOREIGN KEY(A_CARRITO)REFERENCES CARRITOS(P_CARRITO)ON DELETE CASCADE,FOREIGN KEY(A_PRODUCTO)REFERENCES PRODUCTOS(P_PRODUCTO) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;";
                n = sentencia.executeUpdate(carr_pro);
                sentencia.executeUpdate("commit");

                if (n == 0) {
                    System.out.println("Se han creado las tablas");
                }
                //INTRODUCCIÓN DE DATOS

                Statement sentenciaInsert = conexion.createStatement();
                sentenciaInsert.executeUpdate("INSERT INTO USUARIOS VALUES (11,'USUARIO1','SECRETO');");
                sentenciaInsert.executeUpdate("INSERT INTO USUARIOS VALUES (22,'USUARIO2','OCULTO');");
                sentenciaInsert.executeUpdate("INSERT INTO USUARIOS VALUES (33,'USUARIO3','CUIDADO');");
                sentenciaInsert.executeUpdate("INSERT INTO CARRITOS VALUES (11,11,'2013/01/01');");
                sentenciaInsert.executeUpdate("INSERT INTO CARRITOS VALUES (22,11,'2013/01/01');");
                sentenciaInsert.executeUpdate("INSERT INTO CARRITOS VALUES (33,22,'2013/02/02');");
                sentenciaInsert.executeUpdate("INSERT INTO CARRITOS VALUES (44,22,'2013/02/03');");
                sentenciaInsert.executeUpdate("INSERT INTO CARRITOS VALUES (55,33,'2013/02/04');");
                sentenciaInsert.executeUpdate("INSERT INTO CARRITOS VALUES (66,33,'2013/01/05');");
                sentenciaInsert.executeUpdate("INSERT INTO SECCIONES VALUES (11,'SECCION1');");
                sentenciaInsert.executeUpdate("INSERT INTO SECCIONES VALUES (22,'SECCION2');");
                sentenciaInsert.executeUpdate("INSERT INTO SECCIONES VALUES (33,'SECCION3');");
                sentenciaInsert.executeUpdate("INSERT INTO PRODUCTOS VALUES (11,11,'S1P1',11,20);");
                sentenciaInsert.executeUpdate("INSERT INTO PRODUCTOS VALUES (22,11,'S1P2',12,13);");
                sentenciaInsert.executeUpdate("INSERT INTO PRODUCTOS VALUES (33,22,'S2P1',21,23);");
                sentenciaInsert.executeUpdate("INSERT INTO PRODUCTOS VALUES (44,22,'S2P2',22,71);");
                sentenciaInsert.executeUpdate("INSERT INTO PRODUCTOS VALUES (55,33,'S3P1',31,23);");
                sentenciaInsert.executeUpdate("INSERT INTO PRODUCTOS VALUES (66,33,'S3P2',32,12);");
                sentenciaInsert.executeUpdate("INSERT INTO CARR_PRO VALUES (11,11,11,6);");
                sentenciaInsert.executeUpdate("INSERT INTO CARR_PRO VALUES (22,11,33,4);");
                sentenciaInsert.executeUpdate("INSERT INTO CARR_PRO VALUES (33,22,22,5);");
                sentenciaInsert.executeUpdate("INSERT INTO CARR_PRO VALUES (44,22,44,4);");
                sentenciaInsert.executeUpdate("INSERT INTO CARR_PRO VALUES (55,33,11,3);");
                sentenciaInsert.executeUpdate("INSERT INTO CARR_PRO VALUES (66,33,44,7);");
                sentenciaInsert.executeUpdate("INSERT INTO CARR_PRO VALUES (77,44,55,4);");
                sentenciaInsert.executeUpdate("INSERT INTO CARR_PRO VALUES (88,44,66,3);");
                sentenciaInsert.executeUpdate("INSERT INTO CARR_PRO VALUES (99,55,22,5);");
                sentenciaInsert.executeUpdate("INSERT INTO CARR_PRO VALUES (100,55,55,6);");
                sentenciaInsert.executeUpdate("INSERT INTO CARR_PRO VALUES (111,66,66,8);");
                sentenciaInsert.executeUpdate("INSERT INTO CARR_PRO VALUES (122,66,44,3);");

                System.out.println("Se han insertado los registros");

                sentenciaInsert.executeUpdate("commit");

                sentenciaInsert.close();
            }
        } catch (SQLException e) {
        }
    }
}
