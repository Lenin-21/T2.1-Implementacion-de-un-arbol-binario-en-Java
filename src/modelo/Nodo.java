package modelo;

public class Nodo<T> {

    private T dato;
    private Nodo<T> izquierdo;
    private Nodo<T> derecho;

    public Nodo(T dato) {
        this.dato = dato;
    }

    // Una hoja es un nodo que no tiene hijos.
    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }

    // El grado indica cuántos hijos tiene el nodo.
    public int grado() {
        int cantidad = 0;

        if (izquierdo != null) {
            cantidad++;
        }

        if (derecho != null) {
            cantidad++;
        }

        return cantidad;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public Nodo<T> getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(Nodo<T> izquierdo) {
        this.izquierdo = izquierdo;
    }

    public Nodo<T> getDerecho() {
        return derecho;
    }

    public void setDerecho(Nodo<T> derecho) {
        this.derecho = derecho;
    }
}