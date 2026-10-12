public class Nodo {
    private String nombre;
    private String extension;
    private long tamanio;
    private boolean esDirectorio;
    private Nodo izquierdo;
    private Nodo derecho;

    public Nodo(String nombre, String extension, long tamanio, boolean esDirectorio) {
        this.nombre = nombre;
        this.extension = extension;
        this.tamanio = tamanio;
        this.esDirectorio = esDirectorio;
        this.izquierdo = null;
        this.derecho = null;
    }
    public String getNombre() {
        return nombre;
    }
    public String getExtension() {
        return extension;
    }
    public long getTamanio() {
        return tamanio;
    }
    public boolean isDirectorio() {
        return esDirectorio;
    }
    public Nodo getIzquierdo() {
        return izquierdo;
    }
    public Nodo getDerecho() {
        return derecho;
    }
    public void setIzquierdo(Nodo izquierdo) {
        this.izquierdo = izquierdo;
    }
    public void setDerecho(Nodo derecho) {
        this.derecho = derecho;
    }
}