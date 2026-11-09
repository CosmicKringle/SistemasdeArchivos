import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JPanel;

public class PanelArbol extends JPanel {
    private final ArbolArchivos sistema;

    public PanelArbol(ArbolArchivos sistema) {
        this.sistema = sistema;
        setBackground(Color.WHITE);
    }
    @Override 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarNodo(g, sistema.getRaiz(), getWidth()/2, 50, getWidth()/4);
    }
    private void dibujarNodo(Graphics g, Nodo nodo, int x, int y, int separacion) {
        if (nodo == null) {
            return;
        }
        int ancho = 130;
        int alto = 30;
        int siguienteY = y + 80;
        int siguienteSeparacion = Math.max(separacion/2, 35);

        g.setColor(Color.GRAY);
        if (nodo.getIzquierdo() != null) {
            g.drawLine(x, y + alto, x - separacion, siguienteY);
            dibujarNodo(g, nodo.getIzquierdo(), x - separacion, siguienteY, siguienteSeparacion);
        }

        g.setColor(Color.GRAY);
        if (nodo.getDerecho() != null) {
            g.drawLine(x, y + alto, x + separacion, siguienteY);
            dibujarNodo(g, nodo.getDerecho(), x + separacion, siguienteY, siguienteSeparacion);
        }

        g.setColor(nodo.isDirectorio() ? new Color(210, 230, 255) : new Color(215, 245, 215));
        g.fillRoundRect(x - ancho/2, y, ancho, alto, 12, 12);
        g.setColor(nodo.isDirectorio() ? new Color(30, 100, 200) : new Color(40, 130, 40));
        g.drawRoundRect(x - ancho/2, y, ancho, alto, 12, 12);

        String texto = nodo.isDirectorio() ? nodo.getNombre() : nodo.getNombre() + "." + nodo.getExtension();
        FontMetrics medidas = g.getFontMetrics();

        g.drawString(texto, x - medidas.stringWidth(texto)/2, y + medidas.getAscent());

    }
}
