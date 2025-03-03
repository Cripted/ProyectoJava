
import javax.swing.Box;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.BoxLayout;

public class popUpMaestros extends javax.swing.JPanel {
    public int numPop;
    public int i= 0;
    static int cantMae;
    conexion con = new conexion();
    HorarioGrupo horario;
    public popUpMaestros(int cantMae,int numPop,HorarioGrupo horario) {
        initComponents();
        this.cantMae=cantMae;
        this.horario=horario;
        this.numPop=numPop;
        ArrayList<Maestro> maestros= con.seleccionarMaestros();
        jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.Y_AXIS));
        jPanel1.setPreferredSize(new Dimension(242, 431));
            // Asegurar que jPanel1 está en el JPanel principal
    this.setLayout(new java.awt.BorderLayout());
    this.add(jPanel1, java.awt.BorderLayout.CENTER);
        for (int i = 0; i < cantMae; i++) {
            agregarMaestro(maestros.get(i).nomCom, maestros.get(i).contrato, maestros.get(i).numEmp );
        }
        }
    
    private void agregarMaestro(String nombre_completo,String contrato,String numEmp){
        botonMaestro maestro = new botonMaestro(nombre_completo,contrato,numEmp,this,i);
        i++;
        maestro.setMaximumSize(new Dimension(242, 70)); // Tamaño fijo para evitar que se estire
        maestro.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar en el panel

        if (jPanel1.getComponentCount() == 0) { // Si es el primer botón, agrega el espacio de 20px
            jPanel1.add(Box.createRigidArea(new Dimension(0, 20))); // Espaciado de 20px antes del primer botón
        }

        jPanel1.add(maestro);
        jPanel1.add(Box.createRigidArea(new Dimension(0, 20))); // Espaciado de 20px entre botones
        jPanel1.revalidate();
        jPanel1.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 242, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 431, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    public String nomMae(String nom){
        this.horario.cambiarNombreMaestro(nom, numPop);
        this.horario.ocultarMaestros(numPop);
        return nom;
    }
    public void borrarMaestro(int numMae){
                        Component[] components = jPanel1.getComponents();

                // Buscar y eliminar el botón con la materia seleccionada si las horas son 0 o menos
                for (Component component : components) {
                    if (component instanceof botonMaterias) {
                        botonMaestro maestro = (botonMaestro) component;
                        if (maestro.numMae == numMae) {
                           
                                jPanel1.remove(maestro); // Elimina el botón del panel
                                jPanel1.revalidate();  // Actualiza el diseño del panel
                                jPanel1.repaint();     // Vuelve a dibujar el panel
                                break; // Salir del bucle una vez eliminado
                           
                        }
                    }
                }
    }

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
            java.util.logging.Logger.getLogger(HorarioGrupo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HorarioGrupo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HorarioGrupo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HorarioGrupo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
