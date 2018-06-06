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
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
public class VerPlanning extends javax.swing.JPanel {

    /**
     * Creates new form VerPlanning
     */
    public VerPlanning() {
        initComponents();
        this.setBackground(Color.WHITE);
        Fillcombo();
    }

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

    private void Fillcombo() {
        try {
            Connection conexion = connection();

            try (
                    //Creamos la sentencia
                    Statement sentencia = conexion.createStatement()) {
                //Usar la base de datos
                String usarBD = "USE chachachachi;";
                sentencia.executeUpdate(usarBD);
                ResultSet result = sentencia.executeQuery("SELECT nombre FROM empleado");

                while (result.next()) {
                    cbEmpleados.addItem(result.getString(1));
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void getTareasDiarias() {

        String nombre = cbEmpleados.getSelectedItem().toString();

        try ( //Creamos la conexión
                Connection conexion = connection()) {
            DefaultListModel<String> listaTareasDiarias = new DefaultListModel<>();

            //Creamos la sentencia
            Statement sentencia = conexion.createStatement();

            //Usar la base de datos
            String usarBD = "USE ChachaChachi;";
            sentencia.executeUpdate(usarBD);
            ResultSet result = sentencia.executeQuery("SELECT t.texto \n"
                    + "FROM empleado e, tarea t, empleado_sala_tarea est \n"
                    + "WHERE e.P_empleado = est.A_empleado AND est.A_tarea = t.P_tarea \n"
                    + "AND e.nombre = '" + nombre + "' AND DATE(fecha) = CURDATE()");

            while (result.next()) {
                listaTareasDiarias.addElement(result.getString(1));
            }
            this.listaTareasPlan.setModel(listaTareasDiarias);
            sentencia.close();
        } catch (SQLException ex) {
            Logger.getLogger(Planificar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getTareasSemanales() {

        String nombre = cbEmpleados.getSelectedItem().toString();

        try ( //Creamos la conexión
                Connection conexion = connection()) {
            DefaultListModel<String> listaTareasSemanales = new DefaultListModel<>();

            //Creamos la sentencia
            Statement sentencia = conexion.createStatement();
            /*SELECT e.nombre, est.fecha, t.texto "
                    + "FROM empleado e, tarea t, empleado_sala_tarea est "
                    + "WHERE e.P_empleado = est.A_empleado AND est.A_tarea = t.P_tarea "
                    + "AND e.nombre = '" + nombre + "'*/
            //Usar la base de datos
            String usarBD = "USE ChachaChachi;";
            sentencia.executeUpdate(usarBD);
            ResultSet result = sentencia.executeQuery("SELECT t.texto \n"
                    + "FROM empleado e, tarea t, empleado_sala_tarea est \n"
                    + "WHERE e.P_empleado = est.A_empleado AND est.A_tarea = t.P_tarea \n"
                    + "AND e.nombre = '" + nombre + "' AND YEARWEEK(`fecha`, 1) = YEARWEEK(CURDATE(), 1)");

            while (result.next()) {
                listaTareasSemanales.addElement(result.getString(1));
            }
            this.listaTareasPlan.setModel(listaTareasSemanales);
            sentencia.close();
        } catch (SQLException ex) {
            Logger.getLogger(Planificar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getFechaTarea() {

        String nombre = cbEmpleados.getSelectedItem().toString();
        String tarea = listaTareasPlan.getSelectedValue().toString();

        try ( //Creamos la conexión
                Connection conexion = connection()) {
            DefaultListModel<String> listaTareasSemanales = new DefaultListModel<>();

            //Creamos la sentencia
            Statement sentencia = conexion.createStatement();
            /*SELECT e.nombre, est.fecha, t.texto "
                    + "FROM empleado e, tarea t, empleado_sala_tarea est "
                    + "WHERE e.P_empleado = est.A_empleado AND est.A_tarea = t.P_tarea "
                    + "AND e.nombre = '" + nombre + "'*/
            //Usar la base de datos
            String usarBD = "USE ChachaChachi;";
            sentencia.executeUpdate(usarBD);
            ResultSet result = sentencia.executeQuery("SELECT est.fecha\n"
                    + "FROM empleado e, tarea t, empleado_sala_tarea est \n"
                    + "WHERE e.P_empleado = est.A_empleado AND est.A_tarea = t.P_tarea\n"
                    + "AND e.nombre = '" + nombre + "' AND t.texto = '" + tarea + "'");

            while (result.next()) {
                
               // dcFecha.setDate(date);
               // dcFecha
                //listaTareasSemanales.addElement(result.getString(1));
                
                System.out.println("***********" + result.getString(1));
                //dcFecha.setDateFormatString(result.getString(1));
                
                SimpleDateFormat Date_Format = new SimpleDateFormat("dd-MM-yyyy"); 
                Date_Format.format(result.getString(1));
                dcFecha.setDateFormatString(Date_Format.toString());
                System.out.println("---------" + Date_Format.toString());
                
                
            }
            this.listaTareasPlan.setModel(listaTareasSemanales);
            sentencia.close();
        } catch (SQLException ex) {
            Logger.getLogger(Planificar.class.getName()).log(Level.SEVERE, null, ex);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        listaTareasPlan = new javax.swing.JList<>();
        btnSemanal = new javax.swing.JButton();
        btnDiario = new javax.swing.JButton();
        dcFecha = new com.toedter.calendar.JDateChooser();
        btnVerTareas = new javax.swing.JButton();

        jLabel2.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        jLabel2.setText("Empleados:");

        cbEmpleados.setFont(new java.awt.Font("Calibri Light", 0, 14)); // NOI18N
        cbEmpleados.setName(""); // NOI18N

        listaTareasPlan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaTareasPlanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listaTareasPlan);

        btnSemanal.setText("SEMANAL");
        btnSemanal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSemanalActionPerformed(evt);
            }
        });

        btnDiario.setText("DIARIO");
        btnDiario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiarioActionPerformed(evt);
            }
        });

        btnVerTareas.setText("VER TAREAS");
        btnVerTareas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerTareasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel2)
                        .addGap(17, 17, 17)
                        .addComponent(cbEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDiario)
                            .addComponent(btnSemanal))
                        .addGap(73, 73, 73)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(btnVerTareas)))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(cbEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnDiario)
                        .addGap(65, 65, 65)
                        .addComponent(btnSemanal))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(dcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(btnVerTareas)))
                .addContainerGap(48, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSemanalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSemanalActionPerformed
        // TODO add your handling code here:

        getTareasSemanales();
        if (listaTareasPlan.getModel().getSize() == 0) {
            JOptionPane.showMessageDialog(null, "No hay tareas realizadas en esta semana");
        }
    }//GEN-LAST:event_btnSemanalActionPerformed

    private void btnDiarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiarioActionPerformed
        // TODO add your handling code here:
        getTareasDiarias();
        if (listaTareasPlan.getModel().getSize() == 0) {
            JOptionPane.showMessageDialog(null, "No hay tareas realizadas hoy");
        }
    }//GEN-LAST:event_btnDiarioActionPerformed

    private void btnVerTareasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerTareasActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_btnVerTareasActionPerformed

    private void listaTareasPlanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaTareasPlanMouseClicked
        // TODO add your handling code here:
        getFechaTarea();
    }//GEN-LAST:event_listaTareasPlanMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDiario;
    private javax.swing.JButton btnSemanal;
    private javax.swing.JButton btnVerTareas;
    private javax.swing.JComboBox<String> cbEmpleados;
    private com.toedter.calendar.JDateChooser dcFecha;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> listaTareasPlan;
    // End of variables declaration//GEN-END:variables
}
