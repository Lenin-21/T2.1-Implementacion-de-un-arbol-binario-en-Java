package app;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Scanner;
import modelo.Nodo;
import negocio.ArbolBinario;

public class Main {

    public static void main(String[] args) {
        if (args.length > 0 && "--pruebas".equals(args[0])) {
            ejecutarPruebas();
        } else if (args.length > 0
                && "--demostracion".equals(args[0])) {
            ejecutarDemostracion();
        } else {
            ejecutarMenu();
        }
    }

    private static void ejecutarMenu() {
        ArbolBinario<String> arbol = new ArbolBinario<>();

        try (Scanner scanner = new Scanner(System.in, "UTF-8")) {
            int opcion = 0;

            while (opcion != 8) {
                mostrarMenu();

                if (!scanner.hasNextLine()) {
                    System.out.println(
                            "\nFin de la entrada. Programa finalizado.");
                    break;
                }

                try {
                    opcion = Integer.parseInt(
                            scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println(
                            "Ingrese un número entre 1 y 8.");
                    continue;
                }

                switch (opcion) {
                    case 1:
                        if (arbol.esVacio()) {
                            arbol = crearArbolInicial();
                            System.out.println(
                                    "Árbol inicial creado correctamente.");
                        } else {
                            System.out.println(
                                    "El árbol inicial ya fue creado.");
                        }
                        break;

                    case 2:
                        arbol.mostrarArbol();
                        break;

                    case 3:
                        System.out.println(
                                "Número de nodos: " + arbol.contarNodos());
                        break;

                    case 4:
                        System.out.println(
                                "Número de hojas: " + arbol.contarHojas());
                        break;

                    case 5:
                        System.out.println(
                                "Altura en aristas: " + arbol.altura());
                        break;

                    case 6:
                    case 7:
                        consultarNodo(arbol, scanner, opcion);
                        break;

                    case 8:
                        System.out.println("Programa finalizado.");
                        break;

                    default:
                        System.out.println(
                                "Opción inválida. Ingrese un número entre 1 y 8.");
                }
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n=== ORGANIZACIÓN UNIVERSITARIA ===");
        System.out.println("1. Crear el árbol inicial");
        System.out.println("2. Visualizar el árbol");
        System.out.println("3. Consultar el número de nodos");
        System.out.println("4. Consultar el número de hojas");
        System.out.println("5. Consultar la altura");
        System.out.println("6. Consultar el grado de un nodo");
        System.out.println("7. Verificar si un nodo es hoja");
        System.out.println("8. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void consultarNodo(
            ArbolBinario<String> arbol,
            Scanner scanner,
            int opcion) {

        if (arbol.esVacio()) {
            System.out.println(
                    "El árbol está vacío. Primero cree el árbol inicial.");
            return;
        }

        System.out.print("Nombre de la dependencia: ");

        if (!scanner.hasNextLine()) {
            System.out.println("No se recibió un nombre.");
            return;
        }

        Nodo<String> nodo = buscarPorNombre(
                arbol.getRaiz(), scanner.nextLine());

        if (nodo == null) {
            System.out.println("No se encontró esa dependencia.");
        } else if (opcion == 6) {
            System.out.println(
                    "Grado de " + nodo.getDato() + ": " + nodo.grado());
        } else {
            System.out.println(
                    nodo.getDato()
                    + (nodo.esHoja() ? " sí es hoja." : " no es hoja."));
        }
    }

    private static Nodo<String> buscarPorNombre(
            Nodo<String> nodo, String nombre) {

        if (nodo == null) {
            return null;
        }

        if (normalizar(nodo.getDato()).equals(normalizar(nombre))) {
            return nodo;
        }

        Nodo<String> encontrado = buscarPorNombre(
                nodo.getIzquierdo(), nombre);

        if (encontrado != null) {
            return encontrado;
        }

        return buscarPorNombre(nodo.getDerecho(), nombre);
    }

    // Permite consultar usando mayúsculas, minúsculas o sin tildes.
    private static String normalizar(String texto) {
        return Normalizer.normalize(texto.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toUpperCase(Locale.ROOT);
    }

    private static ArbolBinario<String> crearArbolInicial() {
        ArbolBinario<String> arbol = new ArbolBinario<>();

        Nodo<String> universidad = arbol.crearRaiz("UNIVERSIDAD");

        Nodo<String> fisei =
                arbol.agregarIzquierdo(universidad, "FISEI");

        Nodo<String> dide =
                arbol.agregarDerecho(universidad, "DIDE");

        Nodo<String> software =
                arbol.agregarIzquierdo(fisei, "SOFTWARE");

        arbol.agregarDerecho(fisei, "TI");
        arbol.agregarIzquierdo(dide, "INVEST.");
        arbol.agregarDerecho(dide, "VINCULACIÓN");
        arbol.agregarIzquierdo(software, "NIVEL1");
        arbol.agregarDerecho(software, "NIVEL3");

        return arbol;
    }

    private static void ejecutarDemostracion() {
        ArbolBinario<String> arbol = crearArbolInicial();

        System.out.println(
                "=== ÁRBOL INICIAL: ORGANIZACIÓN UNIVERSITARIA ===");

        arbol.mostrarArbol();

        System.out.println("Número de nodos: " + arbol.contarNodos());
        System.out.println("Número de hojas: " + arbol.contarHojas());
        System.out.println("Altura en aristas: " + arbol.altura());

        Nodo<String> fisei =
                buscarPorNombre(arbol.getRaiz(), "FISEI");

        Nodo<String> software =
                buscarPorNombre(arbol.getRaiz(), "SOFTWARE");

        Nodo<String> nivel1 =
                buscarPorNombre(arbol.getRaiz(), "NIVEL1");

        System.out.println("Grado de FISEI: " + fisei.grado());
        System.out.println("Grado de SOFTWARE: " + software.grado());
        System.out.println("FISEI es hoja: " + fisei.esHoja());
        System.out.println("NIVEL1 es hoja: " + nivel1.esHoja());
    }

    private static void ejecutarPruebas() {
        System.out.println("=== CASOS DE PRUEBA AUTOMÁTICOS ===");

        ArbolBinario<String> vacio = new ArbolBinario<>();

        comprobar(
                vacio.esVacio()
                && vacio.contarNodos() == 0
                && vacio.contarHojas() == 0
                && vacio.altura() == -1,
                "Árbol vacío: 0 nodos, 0 hojas y altura -1");

        ArbolBinario<String> unico = new ArbolBinario<>();
        Nodo<String> raizUnica = unico.crearRaiz("ÚNICO");

        comprobar(
                !unico.esVacio()
                && unico.contarNodos() == 1
                && unico.contarHojas() == 1
                && unico.altura() == 0
                && raizUnica.esHoja()
                && raizUnica.grado() == 0,
                "Un solo nodo: 1 nodo, 1 hoja, altura 0 y grado 0");

        ArbolBinario<String> arbol = crearArbolInicial();

        comprobar(
                arbol.contarNodos() == 9
                && arbol.contarHojas() == 5
                && arbol.altura() == 3,
                "Árbol inicial: 9 nodos, 5 hojas y altura 3");

        Nodo<String> fisei =
                buscarPorNombre(arbol.getRaiz(), "FISEI");

        Nodo<String> software =
                buscarPorNombre(arbol.getRaiz(), "SOFTWARE");

        comprobar(
                fisei != null && fisei.grado() == 2,
                "Grado de FISEI: 2");

        comprobar(
                software != null && software.grado() == 2,
                "Grado de SOFTWARE: 2");

        Nodo<String> izquierdoOriginal = fisei.getIzquierdo();
        boolean rechazoIzquierdo = false;

        try {
            arbol.agregarIzquierdo(fisei, "NUEVO");
        } catch (IllegalStateException e) {
            rechazoIzquierdo = true;
            System.out.println("  Mensaje: " + e.getMessage());
        }

        comprobar(
                rechazoIzquierdo
                && fisei.getIzquierdo() == izquierdoOriginal
                && arbol.contarNodos() == 9
                && arbol.contarHojas() == 5
                && arbol.altura() == 3,
                "Inserción izquierda ocupada rechazada; árbol conservado");

        Nodo<String> derechoOriginal = fisei.getDerecho();
        boolean rechazoDerecho = false;

        try {
            arbol.agregarDerecho(fisei, "NUEVO");
        } catch (IllegalStateException e) {
            rechazoDerecho = true;
            System.out.println("  Mensaje: " + e.getMessage());
        }

        comprobar(
                rechazoDerecho
                && fisei.getDerecho() == derechoOriginal
                && arbol.contarNodos() == 9
                && arbol.contarHojas() == 5
                && arbol.altura() == 3,
                "Inserción derecha ocupada rechazada; árbol conservado");

        boolean rechazoRaiz = false;

        try {
            arbol.crearRaiz("OTRA RAÍZ");
        } catch (IllegalStateException e) {
            rechazoRaiz = true;
        }

        comprobar(
                rechazoRaiz
                && "UNIVERSIDAD".equals(arbol.getRaiz().getDato())
                && arbol.contarNodos() == 9,
                "Una raíz existente no se reemplaza");

        Nodo<String> ajeno = new Nodo<>("AJENO");
        boolean rechazoAjeno = false;

        try {
            arbol.agregarIzquierdo(ajeno, "HIJO");
        } catch (IllegalArgumentException e) {
            rechazoAjeno = true;
        }

        comprobar(
                rechazoAjeno
                && ajeno.esHoja()
                && arbol.contarNodos() == 9,
                "Un padre ajeno al árbol es rechazado");

        boolean rechazoNulo = false;

        try {
            arbol.agregarDerecho(null, "HIJO");
        } catch (IllegalArgumentException e) {
            rechazoNulo = true;
        }

        comprobar(
                rechazoNulo && arbol.contarNodos() == 9,
                "Un padre nulo es rechazado");

        Nodo<String> vinculacion =
                buscarPorNombre(arbol.getRaiz(), "  vinculacion  ");

        comprobar(
                vinculacion != null
                && vinculacion.esHoja()
                && buscarPorNombre(arbol.getRaiz(), "inexistente") == null,
                "Búsqueda tolera mayúsculas, tildes y espacios; "
                + "detecta nombre inexistente");

        unico.agregarDerecho(raizUnica, "HIJO");

        comprobar(
                raizUnica.grado() == 1
                && !raizUnica.esHoja()
                && unico.contarNodos() == 2
                && unico.contarHojas() == 1
                && unico.altura() == 1,
                "Nodo con un hijo: grado 1 y altura 1");

        System.out.println("RESULTADO: 12 de 12 casos aprobados.");
    }

    private static void comprobar(
            boolean condicion, String descripcion) {

        if (!condicion) {
            throw new IllegalStateException("FALLÓ: " + descripcion);
        }

        System.out.println("APROBADO: " + descripcion);
    }
}