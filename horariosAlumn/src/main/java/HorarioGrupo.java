import java.util.Map;
import java.awt.Dimension;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.*;
import java.awt.*;

public class HorarioGrupo extends javax.swing.JFrame {
    public int matSelec;
    conexion con = new conexion();
    static int cantMat;
    static int grupo;
    static String cuatrimestre;
public HorarioGrupo(int cantMat,int grupo,String cuatrimestre) {
    this.grupo=grupo;
    this.cantMat = cantMat;
    this.cuatrimestre=cuatrimestre;
    initComponents();
    aplicarEstiloTabla();
    ArrayList<Materia> materias = con.seleccionarMaterias(this.grupo,this.cuatrimestre);
    panelMaterias.setLayout(new BoxLayout(panelMaterias, BoxLayout.Y_AXIS));
    panelMaterias.setPreferredSize(new Dimension(285, 774));

    for (int i = 0; i < cantMat; i++) {
        agregarMateria(materias.get(i).nombre, materias.get(i).horasSem, i);
    }
    updateTableData();

    // Agregar un MouseListener para la tabla
    jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mousePressed(java.awt.event.MouseEvent evt) {
            int row = jTable1.rowAtPoint(evt.getPoint());
            int column = jTable1.columnAtPoint(evt.getPoint());

            // Verificar que la celda no sea de la fila 0 ni de la columna 0
            if (column > 0) {
                // Obtener el texto que se quiere insertar
                String nuevoValor = datosMateria();

                // Verificar si hay una materia seleccionada y que el nuevo valor no esté vacío
                if (matSelec != -1 && nuevoValor != null && !nuevoValor.trim().isEmpty()) {
                    jTable1.setValueAt(nuevoValor, row, column);
                }

                Component[] components = panelMaterias.getComponents();

                // Buscar y eliminar el botón con la materia seleccionada si las horas son 0 o menos
                for (Component component : components) {
                    if (component instanceof botonMaterias) {
                        botonMaterias boton = (botonMaterias) component;
                        if (boton.numMat == matSelec) {
                            if (boton.horasSem <= 0) {
                                panelMaterias.remove(boton); // Elimina el botón del panel
                                panelMaterias.revalidate();  // Actualiza el diseño del panel
                                panelMaterias.repaint();     // Vuelve a dibujar el panel
                                break; // Salir del bucle una vez eliminado
                            }
                        }
                    }
                }
            }
        }
    });
}

    // Crear un mapa de popup para almacenar la referencia de cada uno por numMat
private Map<Integer, popUpMaestros> popups = new HashMap<>();

public void mostrarMaestros(int numMat) {
    popUpMaestros popup = new popUpMaestros(con.seleccionarMaestros().size(), numMat,this);

    int index = -1;
    Component[] components = panelMaterias.getComponents();

    // Buscar el índice del botón con el numMat específico
    for (int i = 0; i < components.length; i++) {
        if (components[i] instanceof botonMaterias) {
            botonMaterias boton = (botonMaterias) components[i];
            if (boton.numMat == numMat) {
                index = i;
                break;
            }
        }
    }

    if (index != -1) {
        // Insertar el popup justo después del botón
        panelMaterias.add(Box.createRigidArea(new Dimension(0, 10)), index + 1); // Espaciado opcional
        panelMaterias.add(popup, index + 2);
        popup.setPreferredSize(new Dimension(242, 431)); // Ajusta según sea necesario
        popup.setMaximumSize(new Dimension(242, 431));
        popup.setMinimumSize(new Dimension(242, 431));
        popup.setSize(242, 431);

        // Guardar el popup en el mapa
        popups.put(numMat, popup);

        panelMaterias.revalidate();
        panelMaterias.repaint();
        panelMaterias.doLayout();
    }
}
public void eliminarMateria() {
    Component[] components = panelMaterias.getComponents();

    // Buscar y eliminar el botón con la materia seleccionada
    for (Component component : components) {
        if (component instanceof botonMaterias) {
            botonMaterias boton = (botonMaterias) component;
            if (boton.numMat == matSelec) {
                panelMaterias.remove(boton); // Elimina el botón del panel
                panelMaterias.revalidate();  // Actualiza el diseño del panel
                panelMaterias.repaint();     // Vuelve a dibujar el panel
                break; // Salir del bucle una vez eliminado
            }
        }
    }
}

public String datosMateria() {
    String materia = "";
    Component[] components = panelMaterias.getComponents();
    
    // Buscar el botón con la materia seleccionada
    for (Component component : components) {
        if (component instanceof botonMaterias) {
            botonMaterias boton = (botonMaterias) component;
            if (boton.numMat == matSelec) {
                boton.horasSem-=1;
                boton.cambiarHoras(); {
        
    };
                // Formatear con HTML para soportar saltos de línea
                materia = "<html>" + boton.materia + "<br><br>" + boton.docente + "</html>";
                break;
            }
        }
    }
    return materia;
}

public void cambiarNombreMaestro(String nombre, int numMat) {
    int index=-1;
     Component[] components = panelMaterias.getComponents();
    // Buscar el índice del botón con el numMat específico
        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof botonMaterias) {
                botonMaterias boton = (botonMaterias) components[i];
                boton.nombreDocente(nombre);
                if (boton.numMat == numMat) {
                    index = i;
                    break;
                }
            }
        }
        
}


public void ocultarMaestros(int numMat) {
    int index = -1;
    Component[] components = panelMaterias.getComponents();

    // Buscar el índice del popup con el numMat específico
    for (int i = 0; i < components.length; i++) {
        if (components[i] instanceof popUpMaestros) {
            popUpMaestros popup = (popUpMaestros) components[i];
            if (popup.numPop == numMat) {  // Suponiendo que popUpMaestros tiene un método getNumMat
                index = i;
                break;
            }
        }
    }

    // Si se encontró el popup, eliminarlo y ajustar el panel
    if (index != -1) {
        // Eliminar el popup
        panelMaterias.remove(index);

        // Eliminar el espacio que se agregó antes del popup
        if (index - 1 >= 0 && components[index - 1] instanceof Box.Filler) {
            panelMaterias.remove(index - 1);
        }

        // Actualizar el panel sin cambiar la posición de los demás botones
        panelMaterias.revalidate();
        panelMaterias.repaint();
    }
}
    private void aplicarEstiloTabla() {
    // Cambiar el color de fondo de la tabla
    jTable1.setBackground(new java.awt.Color(240, 240, 240));  // Color gris claro
    
    // Cambiar el color de la línea de la cuadrícula (bordes)
    jTable1.setGridColor(new java.awt.Color(200, 200, 200)); // Gris claro
    
    // Cambiar la apariencia de las cabeceras
    jTable1.getTableHeader().setBackground(new java.awt.Color(100, 149, 237)); // Color azul
    jTable1.getTableHeader().setForeground(java.awt.Color.WHITE); // Color blanco para el texto de las cabeceras
    jTable1.getTableHeader().setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14)); // Fuente en negrita
    
    // Cambiar la apariencia de las celdas
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) jTable1.getCellRenderer(0, 0);  // Acceder a la celda
    renderer.setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto
    renderer.setBackground(new java.awt.Color(255, 255, 255)); // Color de fondo blanco para las celdas
    renderer.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12)); // Fuente estándar para el texto
    jTable1.setRowHeight(100); // Establecer la altura de las filas
    
    // Aplicar bordes personalizados
    jTable1.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 1)); // Borde negro fino
    
    // Cambiar el color del texto de las celdas
    jTable1.setForeground(new java.awt.Color(0, 0, 0)); // Texto en color negro
}


    private void agregarMateria(String materia1, int horas, int numMat) {
        // Crear un nuevo botón
        botonMaterias materia = new botonMaterias(materia1, horas,this,numMat);
        materia.setMaximumSize(new Dimension(230, 80)); // Tamaño fijo para evitar que se estire
        materia.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar en el panel

        if (panelMaterias.getComponentCount() == 0) { // Si es el primer botón, agrega el espacio de 20px
            panelMaterias.add(Box.createRigidArea(new Dimension(0, 20))); // Espaciado de 20px antes del primer botón
        }

        panelMaterias.add(materia);
        panelMaterias.add(Box.createRigidArea(new Dimension(0, 20))); // Espaciado de 20px entre botones
        
        panelMaterias.revalidate();
        panelMaterias.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        panelMaterias = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "", "LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        panelMaterias.setBackground(new java.awt.Color(255, 185, 118));

        javax.swing.GroupLayout panelMateriasLayout = new javax.swing.GroupLayout(panelMaterias);
        panelMaterias.setLayout(panelMateriasLayout);
        panelMateriasLayout.setHorizontalGroup(
            panelMateriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 285, Short.MAX_VALUE)
        );
        panelMateriasLayout.setVerticalGroup(
            panelMateriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 774, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(panelMaterias, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1018, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelMaterias, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
     // Método para actualizar los datos de la tabla

    private void updateTableData() {
        String[] horas = {
            "7:00 - 8:00", "8:00 - 9:00", "9:00 - 10:00", "10:00 - 11:00",
            "11:00 - 12:00", "12:00 - 13:00", "13:00 - 14:00", "14:00 - 15:00"
        };

        // Llenamos la segunda columna (LUNES) con las horas
        for (int i = 0; i < horas.length; i++) {
            jTable1.setValueAt(horas[i], i, 0); // Establecer las horas en la columna LUNES (índice 1)
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
                new HorarioGrupo(cantMat,grupo,cuatrimestre).setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel panelMaterias;
    // End of variables declaration//GEN-END:variables
}
