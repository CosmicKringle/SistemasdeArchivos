import java.util.ArrayList;
import java.util.List;

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

    public Nodo buscar(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        nombre = nombre.trim();
        Nodo actual = raiz;

        while (actual != null) {
            int comparacion =nombre.compareToIgnoreCase(actual.getNombre());
            if (comparacion == 0) {
                return actual;
            }
            if(comparacion < 0) {
                actual = actual.getIzquierdo();
            } else {
                actual = actual.getDerecho();
            }
        }
        return null;
    }
    public List<Nodo> inorden() {
        List<Nodo> resultado = new ArrayList<>();
        inorden(raiz, resultado);
        return resultado;
    }

    private void inorden(Nodo actual, List<Nodo> resultado) {
        if (actual == null) {
            return;
        }
        inorden(actual.getIzquierdo(), resultado);
        resultado.add(actual);
        inorden(actual.getDerecho(), resultado);
    }
    public List<Nodo> preorden() {
        List<Nodo> resultado = new ArrayList<>();
        preorden(raiz, resultado);
        return resultado;
    }

    private void preorden(Nodo actual, List<Nodo> resultado) {
        if (actual == null) {
            return;
        }
        resultado.add(actual);
        preorden(actual.getIzquierdo(), resultado);
        preorden(actual.getDerecho(), resultado);
    }
    public List<Nodo> postorden() {
        List<Nodo> resultado = new ArrayList<>();
        postorden(raiz, resultado);
        return resultado;
    }

    public void postorden(Nodo actual, List<Nodo> resultado) {
        if (actual == null) {
            return;
        }
        postorden(actual.getIzquierdo(), resultado);
        postorden(actual.getDerecho(), resultado);
        resultado.add(actual);
    }

    public boolean eliminar(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        nombre = nombre.trim();
        if (nombre.compareToIgnoreCase(raiz.getNombre()) == 0) {
            throw new IllegalArgumentException("no se puede eliminar el directorio raiz");
        }
        Nodo actual = raiz;
        Nodo padre = null;

        while (actual != null) {
            int comparacion = nombre.compareToIgnoreCase(actual.getNombre());

            if (comparacion == 0) {
                break;
            }
            padre = actual;

            if (comparacion < 0) {
                actual = actual.getIzquierdo();
            } else {
                actual = actual.getDerecho();
            }
        }
        if (actual == null) {
            return false;
        }

        if (actual.getIzquierdo() != null || actual.getDerecho() != null) {
            throw new IllegalArgumentException("No se puede eliminar un directorio con elementos");
        }

        if (padre.getIzquierdo() == actual) {
            padre.setIzquierdo(null);
        } else {
            padre.setDerecho(null);
        }
        return true;
    }
}
