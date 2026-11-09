import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaArchivos extends JFrame {
    private final ArbolArchivos sistema;

    public VentanaArchivos() {
        sistema = new ArbolArchivos("Documentos");

        setTitle("Sistema de Archivos ABB");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrinicpal = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Sistema de Archivos con Arbol Binario de Busqueda", SwingConstants.CENTER);
        JLabel estado = new JLabel("Directorio raiz: " + sistema.getRaiz().getNombre());

        panelPrinicpal.add(titulo, BorderLayout.NORTH);
        panelPrinicpal.add(estado, BorderLayout.SOUTH);

        JPanel panelControles = new JPanel(new GridLayout(0, 1, 5, 5));
        panelControles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,10));
        JTextField campoNombre = new JTextField(15);
        JComboBox<String> selectorTipo = new JComboBox<>(new String[] {"Directorio", "Archivo"});
        JTextField campoExtension = new JTextField(15);
        JTextField campoTamanio= new JTextField("0", 15);
        JButton botonInsertar = new JButton("Insertar");

        campoExtension.setEnabled(false);
        campoTamanio.setEnabled(false);

        panelControles.add(new JLabel("Nombre:"));
        panelControles.add(campoNombre);
        panelControles.add(new JLabel("Tipo:"));
        panelControles.add(selectorTipo);
        panelControles.add(new JLabel("Extension (sin punto):"));
        panelControles.add(campoExtension);
        panelControles.add(new JLabel("Tamano en bytes"));
        panelControles.add(campoTamanio);
        panelControles.add(botonInsertar);

        JButton botonBuscar = new JButton("Buscar");
        panelControles.add(botonBuscar);

        botonBuscar.addActionListener(evento -> {
            String nombre = JOptionPane.showInputDialog(this, "Nombre del elemento a buscar:");

            if (nombre == null) {
                return;
            }
            try {
                Nodo encontrado = sistema.buscar(nombre);

                if (encontrado == null) {
                    JOptionPane.showMessageDialog(this, "El elemento no existe en el sistema de archivos.", "Busqueda", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String informacion = "Nombre: " + encontrado.getNombre() + "\nExtension: " + (encontrado.isDirectorio() ? "-" : encontrado.getExtension()) + "\nTipo: " + (encontrado.isDirectorio() ? "Directorio" : "Archivo") + "\nTamano: " +encontrado.getTamanio() + " bytes";
                    JOptionPane.showMessageDialog(this, informacion, "Informacion del elemento", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IllegalArgumentException error) {
                JOptionPane.showMessageDialog(this, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        PanelArbol panelArbol = new PanelArbol(sistema);
        panelPrinicpal.add(panelArbol, BorderLayout.CENTER);

        selectorTipo.addActionListener(evento -> {
            boolean esArchivo = "Archivo".equals(selectorTipo.getSelectedItem());

            campoExtension.setEnabled(esArchivo);
            campoTamanio.setEnabled(esArchivo);
        });

        botonInsertar.addActionListener(evento -> {
            try {
                boolean esDirectorio = "Directorio".equals(selectorTipo.getSelectedItem());
                String extension = esDirectorio ? "" : campoExtension.getText();
                Long tamanio = esDirectorio ? 0 : Long.parseLong(campoTamanio.getText().trim());
                sistema.insertar(campoNombre.getText(), extension, tamanio, esDirectorio);

                panelArbol.repaint();

                JOptionPane.showMessageDialog(this, "Elemento insertado correctamente");

                campoNombre.setText("");
                campoExtension.setText("");
                campoTamanio.setText("0");
            } catch (NumberFormatException error) {
                JOptionPane.showMessageDialog(this, "El tamano debe ser un numero entero valido.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException error) {
                JOptionPane.showMessageDialog(this, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton botonEliminar = new JButton("Eliminar");
        panelControles.add(botonEliminar);

        botonEliminar.addActionListener(evento -> {
            String nombre = JOptionPane.showInputDialog(this, "Nombre del elemento a eliminar:");

            if (nombre == null) {
                return;
            }
            try {
                Nodo encontrado = sistema.buscar(nombre);

                if(encontrado == null) {
                    JOptionPane.showMessageDialog(this, "El elemento no exite en el sistema de archivos", "Eliminar", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                int respuesta = JOptionPane.showConfirmDialog(this, "Desea eliminar \"" + encontrado.getNombre() + "\"?", "Confirmar eliminacion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (respuesta != JOptionPane.YES_OPTION) {
                    return;
                }
                if (sistema.eliminar(nombre)) {
                    panelArbol.repaint();

                    JOptionPane.showMessageDialog(this, "Elemento eliminado correctamente");
                }
            } catch (IllegalArgumentException error) {
                JOptionPane.showMessageDialog(this, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton botonInorden = new JButton("Inorden");
        JButton botonPreorden = new JButton("Preorden");
        JButton botonPostorden = new JButton("Postorden");

        panelControles.add(botonInorden);
        panelControles.add(botonPreorden);
        panelControles.add(botonPostorden);

        botonInorden.addActionListener(evento -> mostrarRecorrido("Inorden", sistema.inorden()));
        botonPreorden.addActionListener(evento -> mostrarRecorrido("Preorden", sistema.preorden()));
        botonPostorden.addActionListener(evento -> mostrarRecorrido("Postorden", sistema.postorden()));

        JButton botonSalir = new JButton("Salir");
        panelControles.add(botonSalir);

        botonSalir.addActionListener(evento -> {
            int respuesta = JOptionPane.showConfirmDialog(this, "Desea salir? Los Datos de esta sesion se perderan", "Salir del sistema", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override 
            public void windowClosing(WindowEvent evento) {
                botonSalir.doClick();
            }
        });

        JPanel panelLateral = new JPanel(new BorderLayout());
        panelLateral.add(panelControles, BorderLayout.NORTH);

        panelPrinicpal.add(panelLateral, BorderLayout.WEST);
        setContentPane(panelPrinicpal);
    }

    private void mostrarRecorrido(String titulo, List<Nodo> nodos) {
        String[] columnas = {"Nombre", "Extension", "Tamano (bytes)", "Tipo"};

        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override 
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        for (Nodo nodo : nodos) {
            modelo.addRow(new Object[] {
                nodo.getNombre(), nodo.isDirectorio() ? "-" : nodo.getExtension(), nodo.getTamanio(), nodo.isDirectorio() ? "Directorio" : "Archivo"
            });
        }
        JTable tabla = new JTable(modelo);
        JScrollPane desplazamiento = new JScrollPane(tabla);

        JOptionPane.showMessageDialog(this, desplazamiento, "Recorrido " + titulo, JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaArchivos ventana = new VentanaArchivos();
            ventana.setVisible(true);
        });
    }
    
}
