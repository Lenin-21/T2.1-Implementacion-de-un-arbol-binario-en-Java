package negocio;

import java.util.Arrays;
import modelo.Nodo;

public class ArbolBinario<T> {

    private Nodo<T> raiz;

    public Nodo<T> getRaiz() {
        return raiz;
    }

    public boolean esVacio() {
        return raiz == null;
    }

    public Nodo<T> crearRaiz(T dato) {
        if (!esVacio()) {
            throw new IllegalStateException(
                    "El árbol ya tiene una raíz.");
        }

        raiz = new Nodo<>(dato);
        return raiz;
    }

    public Nodo<T> agregarIzquierdo(Nodo<T> padre, T dato) {
        validarPadre(padre);

        if (padre.getIzquierdo() != null) {
            throw new IllegalStateException(
                    "La posición izquierda ya está ocupada.");
        }

        Nodo<T> hijo = new Nodo<>(dato);
        padre.setIzquierdo(hijo);
        return hijo;
    }

    public Nodo<T> agregarDerecho(Nodo<T> padre, T dato) {
        validarPadre(padre);

        if (padre.getDerecho() != null) {
            throw new IllegalStateException(
                    "La posición derecha ya está ocupada.");
        }

        Nodo<T> hijo = new Nodo<>(dato);
        padre.setDerecho(hijo);
        return hijo;
    }

    private void validarPadre(Nodo<T> padre) {
        if (padre == null || !pertenece(raiz, padre)) {
            throw new IllegalArgumentException(
                    "El padre debe pertenecer a este árbol.");
        }
    }

    private boolean pertenece(Nodo<T> actual, Nodo<T> buscado) {
        if (actual == null) {
            return false;
        }

        return actual == buscado
                || pertenece(actual.getIzquierdo(), buscado)
                || pertenece(actual.getDerecho(), buscado);
    }

    public int contarNodos() {
        return contarNodos(raiz);
    }

    private int contarNodos(Nodo<T> nodo) {
        if (nodo == null) {
            return 0;
        }

        return 1
                + contarNodos(nodo.getIzquierdo())
                + contarNodos(nodo.getDerecho());
    }

    public int contarHojas() {
        return contarHojas(raiz);
    }

    private int contarHojas(Nodo<T> nodo) {
        if (nodo == null) {
            return 0;
        }

        if (nodo.esHoja()) {
            return 1;
        }

        return contarHojas(nodo.getIzquierdo())
                + contarHojas(nodo.getDerecho());
    }

    public int altura() {
        return altura(raiz);
    }

    private int altura(Nodo<T> nodo) {
        if (nodo == null) {
            return -1;
        }

        return 1 + Math.max(
                altura(nodo.getIzquierdo()),
                altura(nodo.getDerecho()));
    }

    public void mostrarArbol() {
        if (esVacio()) {
            System.out.println("El árbol está vacío.");
            return;
        }

        // Alterna filas de nombres y filas de conexiones.
        char[][] lienzo =
                new char[2 * altura() + 1][anchoSubarbol(raiz)];

        for (char[] fila : lienzo) {
            Arrays.fill(fila, ' ');
        }

        dibujarNodo(raiz, lienzo, 0, 0);

        System.out.println();

        for (char[] fila : lienzo) {
            int fin = fila.length;

            while (fin > 0 && fila[fin - 1] == ' ') {
                fin--;
            }

            System.out.println(new String(fila, 0, fin));
        }
    }

    // Calcula el espacio necesario para los nombres del subárbol.
    private int anchoSubarbol(Nodo<T> nodo) {
        if (nodo == null) {
            return 0;
        }

        return anchoSubarbol(nodo.getIzquierdo())
                + String.valueOf(nodo.getDato()).length() + 2
                + anchoSubarbol(nodo.getDerecho());
    }

    // Coloca un nodo y sus hijos; devuelve su columna central.
    private int dibujarNodo(
            Nodo<T> nodo, char[][] lienzo, int fila, int inicio) {

        String texto = String.valueOf(nodo.getDato());
        int columna = inicio + anchoSubarbol(nodo.getIzquierdo());
        int centro = columna + texto.length() / 2;

        for (int i = 0; i < texto.length(); i++) {
            lienzo[fila][columna + i] = texto.charAt(i);
        }

        if (nodo.getIzquierdo() != null) {
            int centroHijo = dibujarNodo(
                    nodo.getIzquierdo(), lienzo, fila + 2, inicio);

            lienzo[fila + 1][(centroHijo + centro) / 2] = '/';
        }

        if (nodo.getDerecho() != null) {
            int inicioDerecho = columna + texto.length() + 2;

            int centroHijo = dibujarNodo(
                    nodo.getDerecho(), lienzo, fila + 2,
                    inicioDerecho);

            lienzo[fila + 1][(centro + centroHijo) / 2] = '\\';
        }

        return centro;
    }
}