public class ArbolArchivos {
    private Nodo raiz;

    public ArbolArchivos(String nombreRaiz) {
        if (nombreRaiz == null || nombreRaiz.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del directorio raiz no puede estar vacio");
        }
        raiz = new Nodo(nombreRaiz.trim(), "", 0, true);
    }
    
    public Nodo getRaiz() {
        return raiz;
    }

    public void insertar(String nombre, String extension, long tamanio, boolean esDirectorio) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }
        nombre = nombre.trim();
        extension = extension == null ? "" : extension.trim();

        if (esDirectorio) {
            if(!extension.isEmpty()) {
                throw new IllegalArgumentException("Un directorio no debe tener extension");
            }
            tamanio = 0;
        } else {
            if (extension.isEmpty()) {
                throw new IllegalArgumentException("Un archivo debe tener extension");
            }
            if (tamanio <0) {
                throw new IllegalArgumentException("El tamano no puede ser negativo");
            }
        }
        Nodo actual = raiz;
        while (true) { 
            int comparacion = nombre.compareToIgnoreCase(actual.getNombre());
            if (comparacion == 0) {
                throw new IllegalArgumentException("Ya existe un elemento con ese nombre");
            }
            if (!actual.isDirectorio()) {
                throw new IllegalArgumentException("Un archivo no puede contener otros elementos");
            }
            if (comparacion < 0) {
                if (actual.getIzquierdo() == null) {
                    actual.setIzquierdo(new Nodo(nombre, extension, tamanio, esDirectorio));
                    return;
                }
                actual = actual.getIzquierdo();
            } else {
                if (actual.getDerecho() == null) {
                    actual.setDerecho(new Nodo(nombre, extension, tamanio, esDirectorio));
                    return;
                }
                actual = actual.getDerecho();
            }
        }
    }
}
