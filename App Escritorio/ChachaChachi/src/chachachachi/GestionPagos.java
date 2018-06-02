/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chachachachi;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
/*SELECT e.nombre, est.fecha, est.pagada 
FROM empleado e, empleado_sala_tarea est
WHERE e.P_empleado = est.A_empleado 
AND e.nombre = 'Ignacio'*/
public class GestionPagos extends javax.swing.JPanel {

    public Connection connection() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conexion
                    = DriverManager.getConnection("jdbc:mysql://localhost/ChachaChachi",
                            "root", "");
            return conexion;
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Planificar.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Creates new form GestionPagos
     */
    public GestionPagos() {
        initComponents();
        this.setBackground(Color.WHITE);
        this.txtFechaPago.setEnabled(false);
        this.txtFechaPago.setEditable(false);
        this.txtTiempoPago.setEnabled(false);
        this.txtTiempoPago.setEditable(false);
        this.txtPrecio.setEnabled(false);
        this.txtPrecio.setEditable(false);

        Fillcombo();

    }

    private void Fillcombo() {
        try {
            Connection conexion = connection();

            try (
                    Statement sentencia = conexion.createStatement()) {

                String usarBD = "USE chachachachi;";
                sentencia.executeUpdate(usarBD);
                ResultSet result = sentencia.executeQuery("SELECT nombre FROM empleado");

                while (result.next()) {
                    cbEmpleados.addItem(result.getString(1));
                    System.out.println("name " + result.getString(1));
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    private void getDate() {
        String name = cbEmpleados.getSelectedItem().toString();
        System.out.println(name);
        try {
            Connection conexion = connection();
            System.out.println("getDate  " + name);

            Statement sentencia = conexion.createStatement();

            String usarBD = "USE chachachachi;";
            sentencia.executeUpdate(usarBD);
            ResultSet result = sentencia.executeQuery("SELECT est.fecha\n"
                    + "FROM empleado e, empleado_sala_tarea est\n"
                    + "WHERE e.P_empleado = est.A_empleado \n"
                    + "AND e.nombre = '" + name + "'");

            while (result.next()) {
                this.txtFechaPago.setText(result.getString(1));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    private void precioHora() {
        String name = cbEmpleados.getSelectedItem().toString();
        System.out.println(name);
        try {
            Connection conexion = connection();
            System.out.println("getDate  " + name);

            Statement sentencia = conexion.createStatement();

            String usarBD = "USE chachachachi;";
            sentencia.executeUpdate(usarBD);
            ResultSet result = sentencia.executeQuery("SELECT e.precioHora \n"
                    + "FROM empleado e \n"
                    + "WHERE e.nombre = '" + name + "'");

            while (result.next()) {
                this.txtPrecio.setText(result.getString(1));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    private void tiempoTrabajado() {
        String name = cbEmpleados.getSelectedItem().toString();
        System.out.println(name);
        try {
            Connection conexion = connection();
            System.out.println("getDate  " + name);

            Statement sentencia = conexion.createStatement();

            String usarBD = "USE chachachachi;";
            sentencia.executeUpdate(usarBD);
            //tiempotrabajado desde la fecha
            ResultSet result = sentencia.executeQuery("SELECT SUM(est.duración) \n"
                    + "FROM empleado_sala_tarea est, empleado e \n"
                    + "WHERE e.nombre = '" + name + "'");

            while (result.next()) {
                this.txtTiempoPago.setText(result.getString(1));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void pagar() throws SQLException {
        try {
//Creamos la conexión
            Connection conexion = connection();

            //Creamos la sentencia
            Statement sentencia = conexion.createStatement();

            //Usar la base de datos
            String usarBD = "USE chachachachi;";
            sentencia.executeUpdate(usarBD);

            conexion.setAutoCommit(false);
            String name = cbEmpleados.getSelectedItem().toString();
            String tiempoTrabajado = txtTiempoPago.getText();
            String fecha = txtFechaPago.getText();
            
            /*sentencia.executeUpdate("INSERT INTO tarea_realizada (" + i + "," + fecha + ",1," + tiempoTrabajado +"
                SELECT P_tarea_realizada, fecha, pagada, duracion
                FROM tarea_realizada
                WHERE e.P_empleado = est.A_empleado 
                AND e.nombre = '" + name + "' 
                AND est.fecha='" + fecha + "' 
                AND est.duración = '" + tiempoTrabajado + "';");*/
            
            JOptionPane.showMessageDialog(null, "Tabla modificada!");
            
            

            /*ResultSet result = sentencia.executeQuery("SELECT est.fecha\n"
                    + "FROM empleado e, empleado_sala_tarea est\n"
                    + "WHERE e.P_empleado = est.A_empleado \n"
                    + "AND e.nombre = '" + name + "' AND est.fecha='" + fecha + "';");// AND est.duración = '" + tiempoTrabajado + "';");
            while (result.next()) {
                
                System.out.println("@@@@@@@@@@@@@");
                System.out.println(result.getString(1));
            }*/

           // sentencia.executeUpdate("INSERT INTO tarea_realizada VALUES(" + fecha + ",1," + tiempoTrabajado + ")");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        cbEmpleados = new javax.swing.JComboBox<>();
        txtFechaPago = new javax.swing.JTextField();
        txtTiempoPago = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        btnPagar = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        jLabel2.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        jLabel2.setText("Seleccione un empleado: ");

        cbEmpleados.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        cbEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEmpleadosActionPerformed(evt);
            }
        });

        txtFechaPago.setEditable(false);
        txtFechaPago.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        txtFechaPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaPagoActionPerformed(evt);
            }
        });

        txtTiempoPago.setEditable(false);
        txtTiempoPago.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N

        txtPrecio.setEditable(false);
        txtPrecio.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        txtPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioActionPerformed(evt);
            }
        });

        btnPagar.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        btnPagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cash.png"))); // NOI18N
        btnPagar.setText("PAGAR");
        btnPagar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPagarMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        jLabel3.setText("Fecha del último pago:");

        jLabel4.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        jLabel4.setText("Tiempo trabajado:");

        jLabel5.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        jLabel5.setText("Precio por horas:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel2)
                        .addGap(94, 94, 94)
                        .addComponent(cbEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtFechaPago, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTiempoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(btnPagar))
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFechaPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtTiempoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addComponent(btnPagar)
                .addGap(46, 46, 46))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtFechaPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaPagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaPagoActionPerformed

    private void txtPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioActionPerformed

    private void cbEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEmpleadosActionPerformed
        // TODO add your handling code here:
        this.txtFechaPago.setText("");
        this.txtPrecio.setText("");
        this.txtTiempoPago.setText("");
        getDate();
        precioHora();
        tiempoTrabajado();
    }//GEN-LAST:event_cbEmpleadosActionPerformed

    private void btnPagarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPagarMouseClicked
        try {
            // TODO add your handling code here:
            pagar();
        } catch (SQLException ex) {
            Logger.getLogger(GestionPagos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnPagarMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnPagar;
    private javax.swing.JComboBox<String> cbEmpleados;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField txtFechaPago;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtTiempoPago;
    // End of variables declaration//GEN-END:variables
}
