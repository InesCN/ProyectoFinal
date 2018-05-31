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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Inés Carazo Núñez
 */
public class GestiondeCompras extends javax.swing.JFrame {

    /**
     * Creates new form GestiondeCompras
     */
    public void login() {
        try {
            //Creamos la conexión
            Connection conexion = Conexiones.conexionMySQL();

            //Creamos la sentencia
            Statement sentencia = conexion.createStatement();

            //Usar la base de datos
            String usarBD = "USE Supermercado_r;";
            sentencia.executeUpdate(usarBD);

            boolean correct = false;
            int car = 0;
            Date date;

            String user = jUsuario.getText();
            String password = jPassword.getText();

            ResultSet result = sentencia.executeQuery("SELECT U.NOMBRE, U.CONTRASENA FROM USUARIOS U;");

            jNumCar.setText("");
            jFecha.setText("");
            jMensajesError.setText("");

            while (result.next()) {

                if (result.getString(1).equals(user) && result.getString(2).equals(password)) {

                    ResultSet result2 = sentencia.executeQuery("SELECT MAX(P_CARRITO) FROM CARRITOS;");

                    while (result2.next()) {
                        car = result2.getInt(1);
                    }
                    car = car + 2;
                    jNumCar.setText(String.valueOf(car));

                    date = new Date();
                    String year = String.valueOf(date).substring(24, 28);
                    String month = String.valueOf(date).substring(4, 7);

                    if (month.equalsIgnoreCase("jan")) {
                        month = "/01";
                    } else if (month.equalsIgnoreCase("feb")) {
                        month = "/02";
                    } else if (month.equalsIgnoreCase("mar")) {
                        month = "/03";
                    } else if (month.equalsIgnoreCase("apr")) {
                        month = "/04";
                    } else if (month.equalsIgnoreCase("may")) {
                        month = "/05";
                    } else if (month.equalsIgnoreCase("jun")) {
                        month = "/06";
                    } else if (month.equalsIgnoreCase("jul")) {
                        month = "/07";
                    } else if (month.equalsIgnoreCase("aug")) {
                        month = "/08";
                    } else if (month.equalsIgnoreCase("sep")) {
                        month = "/09";
                    } else if (month.equalsIgnoreCase("oct")) {
                        month = "/10";
                    } else if (month.equalsIgnoreCase("nov")) {
                        month = "/11";
                    } else if (month.equalsIgnoreCase("dec")) {
                        month = "/12";
                    }

                    String day = "/" + String.valueOf(date).substring(8, 10);
                    String stringDate = year + month + day;
                    jFecha.setText(stringDate);
                    correct = true;
                    break;
                }

            }

            if (correct == false) {
                jMensajesError.setText("El usuario y/o la contraseña introducidos son incorrectos");
            }
            sentencia.close();
            conexion.close();
        } catch (SQLException e) {
        }
    }

    public void metodoConsultar() {
        try {
            //Creamos la conexión
            Connection conexion = Conexiones.conexionMySQL();

            //Creamos la sentencia
            Statement sentencia = conexion.createStatement();

            //Usar la base de datos
            String usarBD = "USE Supermercado_r;";
            sentencia.executeUpdate(usarBD);
            boolean exists = false;

            String user = jUsuario.getText();

            ResultSet result = sentencia.executeQuery("SELECT DESCRIPCION, PVP FROM PRODUCTOS;");

            while (result.next()) {

                if (jDescripcion.getText().equals(result.getString(1))) {

                    jPvp.setText(String.valueOf(result.getFloat(2)));
                    jMensajesError.setText("");
                    exists = true;
                    break;

                } else if (exists == false) {

                    jPvp.setText("");
                    jMensajesError.setText("El articulo no existe");

                }
            }

            sentencia.close();

            conexion.close();

        } catch (SQLException e) {
        }
    }

    public void comprar() throws SQLException {
        //Creamos la conexión
        Connection conexion = Conexiones.conexionMySQL();

        //Creamos la sentencia
        Statement sentencia = conexion.createStatement();

        //Usar la base de datos
        String usarBD = "USE Supermercado_r;";
        sentencia.executeUpdate(usarBD);
        try {
            conexion = Conexiones.conexionMySQL();
            sentencia = conexion.createStatement();
            conexion.setAutoCommit(false);
            sentencia = conexion.createStatement();
            //String descripcion = jDescripcion.getText();

            int cant = 0;
            cant = Integer.parseInt(jCantidad.getText());

            int stock = 0;
            int carr_pro = 0;
            int product = 0;
            int user = 0;
            boolean hayCar = false;

            ResultSet result = sentencia.executeQuery("SELECT STOCK FROM PRODUCTOS WHERE DESCRIPCION='" + jDescripcion.getText() + "';");

            while (result.next()) {
                stock = result.getInt(1);
            }

            if ((stock >= cant) && (cant > 0)) {

                jMensajesError.setText("");
                result = sentencia.executeQuery("SELECT P_USUARIO FROM USUARIOS WHERE NOMBRE='" + jUsuario.getText() + "';");

                while (result.next()) {
                    user = result.getInt(1);
                }

                result = sentencia.executeQuery("SELECT MAX(P_CARR_PRO) FROM CARR_PRO;");

                while (result.next()) {
                    carr_pro = result.getInt(1);
                }

                carr_pro = carr_pro + 2;

                result = sentencia.executeQuery("SELECT P_PRODUCTO FROM PRODUCTOS WHERE DESCRIPCION='" + jDescripcion.getText() + "'");

                while (result.next()) {
                    product = result.getInt(1);
                }

                result = sentencia.executeQuery("SELECT P_CARRITO FROM CARRITOS WHERE P_CARRITO='" + Integer.parseInt(jNumCar.getText()) + "'");

                while (result.next()) {
                    hayCar = true;
                }

                if (!hayCar) {

                    int row1 = sentencia.executeUpdate("INSERT INTO CARRITOS VALUES(" + Integer.parseInt(jNumCar.getText()) + "," + user + ",'" + jFecha.getText() + "')");

                }

                int row2 = sentencia.executeUpdate("INSERT INTO CARR_PRO VALUES(" + carr_pro + "," + Integer.parseInt(jNumCar.getText()) + "," + product + "," + cant + ")");

                stock = stock - cant;

                int row3 = sentencia.executeUpdate("UPDATE PRODUCTOS SET STOCK=" + stock + " WHERE P_PRODUCTO=" + product);

            } else {

                if (cant <= 0) {

                    this.jMensajesError.setText("El valor debe ser mayor que 0");
                    this.jCantidad.setText("");

                } else {

                    if (stock == 0) {

                        metodoConsultar();

                    } else {

                        jMensajesError.setText("Cantidad excesiva, Stock disponible: " + stock);

                    }
                }
            }

            conexion.commit();

        } catch (SQLException e) {
            try {
                conexion.rollback();
            } catch (SQLException e1) {
            }
        } catch (NumberFormatException e2) {

            jMensajesError.setText("Introduzca un número, por favor");
            jCantidad.setText("");

        } finally {

            try {

                conexion.setAutoCommit(true);
                sentencia.close();
                conexion.close();

            } catch (SQLException e3) {
            }
        }
    }

    public GestiondeCompras() throws SQLException {

//        //Creamos la conexión
//        Connection conexion = Conexiones.conexionMySQL();
//
//        //Creamos la sentencia
//        Statement sentencia = conexion.createStatement();
//
//        //Usar la base de datos
//        String usarBD = "USE Supermercado_r;";
//        sentencia.executeUpdate(usarBD);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jUsuario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPassword = new javax.swing.JPasswordField();
        btnLogueo = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jNumCar = new javax.swing.JTextField();
        jFecha = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jDescripcion = new javax.swing.JTextField();
        btnConsultar = new javax.swing.JButton();
        jPvp = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jCantidad = new javax.swing.JTextField();
        btnComprar = new javax.swing.JButton();
        jMensajesError = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestión de Compras");

        jLabel1.setText("USUARIO");

        jLabel2.setText("CONTRASEÑA");

        btnLogueo.setText("Login");
        btnLogueo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogueoActionPerformed(evt);
            }
        });

        jLabel3.setText("Nº CARRITO");

        jLabel4.setText("FECHA");

        jNumCar.setEditable(false);
        jNumCar.setBackground(new java.awt.Color(204, 204, 255));

        jFecha.setEditable(false);
        jFecha.setBackground(new java.awt.Color(204, 204, 255));

        jLabel5.setText("Desc. Articulo");

        jLabel6.setText("PVP");

        btnConsultar.setText("Consultar");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

        jPvp.setEditable(false);
        jPvp.setBackground(new java.awt.Color(204, 204, 255));

        jLabel7.setText("Cantidad");

        btnComprar.setText("Comprar");
        btnComprar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jMensajesError)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jNumCar, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                                    .addComponent(jUsuario))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnConsultar)
                                .addGap(31, 31, 31)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPvp, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(61, 61, 61)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPassword)
                                    .addComponent(jFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(30, 30, 30)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnLogueo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnComprar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLogueo))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jNumCar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConsultar)
                    .addComponent(jPvp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnComprar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addComponent(jMensajesError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogueoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogueoActionPerformed
        // TODO add your handling code here:
        login();
    }//GEN-LAST:event_btnLogueoActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        // TODO add your handling code here:
        metodoConsultar();
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void btnComprarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprarActionPerformed
        // TODO add your handling code here:
        try {
            comprar();
        } catch (SQLException ex) {
            Logger.getLogger(GestiondeCompras.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnComprarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GestiondeCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestiondeCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestiondeCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestiondeCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new GestiondeCompras().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(GestiondeCompras.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnComprar;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnLogueo;
    private javax.swing.JTextField jCantidad;
    private javax.swing.JTextField jDescripcion;
    private javax.swing.JTextField jFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField jMensajesError;
    private javax.swing.JTextField jNumCar;
    private javax.swing.JPasswordField jPassword;
    private javax.swing.JTextField jPvp;
    private javax.swing.JTextField jUsuario;
    // End of variables declaration//GEN-END:variables
}
