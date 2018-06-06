/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chachachachi;

<<<<<<< HEAD
=======

>>>>>>> master
import chachachachi.entidades.Sala;
import chachachachi.entidades.Tarea;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author dam205
 */
public final class Planificar extends javax.swing.JPanel {

    String turno;
    String usuario;
    boolean guardado = false; //Cuando es igual a false no está guardado

    DefaultListModel listaTareasModelo = new DefaultListModel();
    DefaultListModel listaEstanciasModelo = new DefaultListModel();

    /**
     * Creates new form Tareas
     *
     */
    public Planificar() {
        initComponents();

        listaTareas.setModel(listaTareasModelo);
        listaEstancias.setModel(listaEstanciasModelo);

        decorarJTree();

        obtenerTareas();
        obtenerSalas();
        fillcombo();
    }

    private void decorarJTree() {
        jtPlanning.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) jtPlanning.getCellRenderer();

        ImageIcon closedIcon = new ImageIcon("icon.png");
        ImageIcon openIcon = new ImageIcon("icon.png");
        ImageIcon leafIcon = new ImageIcon(Planificar.class.getResource("key.png"));
        renderer.setClosedIcon(closedIcon);
        renderer.setOpenIcon(openIcon);
        renderer.setLeafIcon(leafIcon);

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

    private void fillcombo() {
        try {
            Connection conexion = connection();

            try ( //Creamos la sentencia
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

    public void obtenerTareas() {
        //this.turno = turno;
        try ( //Creamos la conexión
                Connection conexion = connection()) {
            DefaultListModel<String> listaTareas = new DefaultListModel<>();

            //Usar la base de datos
            try ( //Creamos la sentencia
                    Statement sentencia = conexion.createStatement()) {
                //Usar la base de datos
                String usarBD = "USE ChachaChachi;";
                sentencia.executeUpdate(usarBD);
                ResultSet result = sentencia.executeQuery("SELECT P_tarea, texto, duracion FROM tarea;");

                int posicion = 0;
                while (result.next()) {
                    listaTareasModelo.add(posicion, new Tarea(result.getInt(1), result.getString(2), result.getInt(3)));
                    posicion++;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Planificar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void obtenerSalas() {
        //this.turno = turno;
        try ( //Creamos la conexión
                Connection conexion = connection()) {
            DefaultListModel<String> listaSalas = new DefaultListModel<>();

            //Creamos la sentencia
            Statement sentencia = conexion.createStatement();

            //Usar la base de datos
            String usarBD = "USE ChachaChachi;";
            sentencia.executeUpdate(usarBD);
            ResultSet result = sentencia.executeQuery("SELECT P_sala, nombre FROM sala;");

            int posicion = 0;
            while (result.next()) {
                listaEstanciasModelo.add(posicion, new Sala(result.getInt(1), result.getString(2)));
                posicion++;
            }
            sentencia.close();
        } catch (SQLException ex) {
            Logger.getLogger(Planificar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void obtenerTareasAsignadas() throws SQLException, SQLException {
        String users = cbEmpleados.getSelectedItem().toString();
        System.out.println("### " + users);
        try ( //Creamos la conexión
                Connection conexion = connection()) {
            DefaultListModel<String> listaTareasAsignadas = new DefaultListModel<>();

            //Creamos la sentencia
            Statement sentencia = conexion.createStatement();

            //Usar la base de datos
            String usarBD = "USE ChachaChachi;";
            sentencia.executeUpdate(usarBD);
            ResultSet result = sentencia.executeQuery("SELECT t.texto FROM  \n"
                    + "tarea t, empleado e, empleado_sala_tarea est\n"
                    + "where e.P_empleado = est.A_empleado AND est.A_tarea = t.P_tarea\n"
                    + "AND e.nombre = \"" + users + "\";");

            while (result.next()) {
                listaTareasAsignadas.addElement(result.getString(1));
            }
            //this.listaTareasAsignadas.setModel(listaTareasAsignadas);
            sentencia.close();
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

        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        pArriba = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbEmpleados = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        pAbajo = new javax.swing.JPanel();
        pTareas = new javax.swing.JPanel();
        lblNombreTareas1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaTareas = new javax.swing.JList<>();
        pEstancias = new javax.swing.JPanel();
        lblNombreTareas3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listaEstancias = new javax.swing.JList<>();
        pPlanning = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtPlanning = new javax.swing.JTree();
        lblNombreTareas2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTiempoEstimado = new javax.swing.JLabel();
        btnAddJtree = new javax.swing.JLabel();
        btnRemoveJtree = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane4.setViewportView(jTextArea1);

        setBackground(new java.awt.Color(255, 255, 255));

        pArriba.setBackground(new java.awt.Color(255, 255, 255));
        pArriba.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        jLabel1.setText("Selecciona empleado:");
        pArriba.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 159, -1));

        cbEmpleados.setFont(new java.awt.Font("Calibri Light", 0, 14)); // NOI18N
        cbEmpleados.setName(""); // NOI18N
        pArriba.add(cbEmpleados, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, 160, -1));

        jLabel2.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        jLabel2.setText("Selecciona una fecha:");
        pArriba.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 30, 158, -1));

        jDateChooser1.setDateFormatString("dd-MM-yyyy");
        pArriba.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 30, 157, -1));

        pAbajo.setBackground(new java.awt.Color(255, 255, 255));

        pTareas.setBackground(new java.awt.Color(255, 255, 255));

        lblNombreTareas1.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        lblNombreTareas1.setText("TAREAS");

        jScrollPane2.setFont(new java.awt.Font("Calibri Light", 0, 14)); // NOI18N

        listaTareas.setFont(new java.awt.Font("Calibri Light", 0, 14)); // NOI18N
        listaTareas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaTareasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(listaTareas);

        javax.swing.GroupLayout pTareasLayout = new javax.swing.GroupLayout(pTareas);
        pTareas.setLayout(pTareasLayout);
        pTareasLayout.setHorizontalGroup(
            pTareasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTareasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pTareasLayout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(lblNombreTareas1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pTareasLayout.setVerticalGroup(
            pTareasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pTareasLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(lblNombreTareas1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        pEstancias.setBackground(new java.awt.Color(255, 255, 255));

        lblNombreTareas3.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        lblNombreTareas3.setText("ESTANCIAS");

        jScrollPane3.setFont(new java.awt.Font("Calibri Light", 0, 14)); // NOI18N

        listaEstancias.setFont(new java.awt.Font("Calibri Light", 0, 14)); // NOI18N
        listaEstancias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaEstanciasMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(listaEstancias);

        javax.swing.GroupLayout pEstanciasLayout = new javax.swing.GroupLayout(pEstancias);
        pEstancias.setLayout(pEstanciasLayout);
        pEstanciasLayout.setHorizontalGroup(
            pEstanciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pEstanciasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pEstanciasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblNombreTareas3)
                .addGap(69, 69, 69))
        );
        pEstanciasLayout.setVerticalGroup(
            pEstanciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pEstanciasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNombreTareas3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pPlanning.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Mi hogar");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Cocina");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Sala");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Baño");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Habitación 1");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Habitación 2");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Alacena");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Cuarto de estudio");
        treeNode1.add(treeNode2);
        jtPlanning.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jtPlanning.setMaximumSize(new java.awt.Dimension(233, 233));
        jtPlanning.setPreferredSize(new java.awt.Dimension(233, 233));
        jScrollPane1.setViewportView(jtPlanning);

        lblNombreTareas2.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        lblNombreTareas2.setText("PLANNING");

        javax.swing.GroupLayout pPlanningLayout = new javax.swing.GroupLayout(pPlanning);
        pPlanning.setLayout(pPlanningLayout);
        pPlanningLayout.setHorizontalGroup(
            pPlanningLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pPlanningLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(pPlanningLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pPlanningLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pPlanningLayout.createSequentialGroup()
                        .addComponent(lblNombreTareas2)
                        .addGap(97, 97, 97))))
        );
        pPlanningLayout.setVerticalGroup(
            pPlanningLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pPlanningLayout.createSequentialGroup()
                .addComponent(lblNombreTareas2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel4.setFont(new java.awt.Font("Calibri Light", 1, 14)); // NOI18N
        jLabel4.setText("Tiempo estimado:");

        txtTiempoEstimado.setFont(new java.awt.Font("Calibri Light", 0, 13)); // NOI18N
        txtTiempoEstimado.setText("txtTiempoEstimado");

        btnAddJtree.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/next.png"))); // NOI18N
        btnAddJtree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddJtreeMouseClicked(evt);
            }
        });

        btnRemoveJtree.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/remove.png"))); // NOI18N

        javax.swing.GroupLayout pAbajoLayout = new javax.swing.GroupLayout(pAbajo);
        pAbajo.setLayout(pAbajoLayout);
        pAbajoLayout.setHorizontalGroup(
            pAbajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pAbajoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pTareas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(pEstancias, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pAbajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pAbajoLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(btnRemoveJtree))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pAbajoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddJtree)))
                .addGap(18, 18, 18)
                .addGroup(pAbajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pPlanning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pAbajoLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTiempoEstimado)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        pAbajoLayout.setVerticalGroup(
            pAbajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pAbajoLayout.createSequentialGroup()
                .addGroup(pAbajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pAbajoLayout.createSequentialGroup()
                        .addGap(158, 158, 158)
                        .addComponent(btnAddJtree)
                        .addGap(63, 63, 63)
                        .addComponent(btnRemoveJtree)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pAbajoLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pAbajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pAbajoLayout.createSequentialGroup()
                                .addComponent(pPlanning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pAbajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtTiempoEstimado))
                                .addGap(1, 1, 1))
                            .addComponent(pTareas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pEstancias, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        btnSave.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        btnSave.setText("GUARDAR");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pAbajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pArriba, javax.swing.GroupLayout.PREFERRED_SIZE, 854, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(btnSave)
                .addGap(50, 50, 50))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pArriba, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pAbajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSave)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void listaTareasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaTareasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_listaTareasMouseClicked

    private void listaEstanciasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaEstanciasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_listaEstanciasMouseClicked

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:

        //Una vez ya guardado
        guardado = true;        
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnAddJtreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddJtreeMouseClicked
        // TODO add your handling code here:
        int[] tareasSeleccionadas = listaTareas.getSelectedIndices();
        int[] estanciasSeleccionadas = listaEstancias.getSelectedIndices();

        DefaultMutableTreeNode root = (DefaultMutableTreeNode) jtPlanning.getModel().getRoot();

        for (int i = 0; i < estanciasSeleccionadas.length; i++) {

            for (int j = 0; j < root.getChildCount(); j++) {

                System.out.println("Comparando " + root.getChildAt(j).
                        toString() + " con " + ((Object) listaEstancias.getModel().
                                getElementAt(i)).toString());

                if (root.getChildAt(j).toString().equals(((Object) listaEstancias.
                        getModel().getElementAt(estanciasSeleccionadas[i])).toString())) {

                    for (int h = 0; h < tareasSeleccionadas.length; h++) {

                        System.out.println("Añadiendo tarea " + ((Object) listaTareas.
                                getModel().getElementAt(tareasSeleccionadas[h])).
                                toString() + " a " + root.getChildAt(j).toString());

                        ((DefaultMutableTreeNode) root.getChildAt(j)).
                                add(new DefaultMutableTreeNode(((Object) listaTareas.getModel().
                                        getElementAt(tareasSeleccionadas[h])).toString()));

                        for (int k = 0; k < jtPlanning.getRowCount(); k++) {
                            jtPlanning.expandRow(k);
                        }
                        jtPlanning.repaint();
                    }
                }
            }
        }
        for (int z = 0; z < tareasSeleccionadas.length; z++) {

            for (int a = 0; a < root.getChildCount(); a++) {

                System.out.println("Comparando " + root.getChildAt(a).
                        toString() + " con " + ((Object) listaTareas.getModel().
                                getElementAt(z)).toString());

                if (root.getChildAt(a).toString().equals(((Object) listaTareas.
                        getModel().getElementAt(tareasSeleccionadas[z])).toString())) {

                    for (int b = 0; b < estanciasSeleccionadas.length; b++) {

                        System.out.println("Añadiendo tarea " + ((Object) listaEstancias.
                                getModel().getElementAt(estanciasSeleccionadas[b])).
                                toString() + " a " + root.getChildAt(a).toString());

                        ((DefaultMutableTreeNode) root.getChildAt(a)).
                                add(new DefaultMutableTreeNode(((Object) listaEstancias.getModel().
                                        getElementAt(estanciasSeleccionadas[b])).toString()));

                        for (int c = 0; c < jtPlanning.getRowCount(); c++) {
                            jtPlanning.expandRow(c);
                        }
                        jtPlanning.repaint();
                    }
                }
            }
        }
    }//GEN-LAST:event_btnAddJtreeMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnAddJtree;
    private javax.swing.JLabel btnRemoveJtree;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cbEmpleados;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTree jtPlanning;
    private javax.swing.JLabel lblNombreTareas1;
    private javax.swing.JLabel lblNombreTareas2;
    private javax.swing.JLabel lblNombreTareas3;
    private javax.swing.JList<String> listaEstancias;
    private javax.swing.JList<String> listaTareas;
    private javax.swing.JPanel pAbajo;
    private javax.swing.JPanel pArriba;
    private javax.swing.JPanel pEstancias;
    private javax.swing.JPanel pPlanning;
    private javax.swing.JPanel pTareas;
    private javax.swing.JLabel txtTiempoEstimado;
    // End of variables declaration//GEN-END:variables
}
