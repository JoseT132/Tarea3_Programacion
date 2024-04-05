
package tarea3_programacion;

public class Arbol_B {
  private Nodo raiz;
    private int t; // Grado mínimo del árbol B

    // Constructor
    public Arbol_B(int grado) {
        this.t = grado;
        this.raiz = null;
    }

    // Método para insertar una clave en el árbol B
    public void insertar(int clave) {
        if (raiz == null) {
            raiz = new Nodo(t, true);
            raiz.claves[0] = clave;
            raiz.n = 1;
        } else {
            if (raiz.n == (2 * t - 1)) {
                Nodo nuevaRaiz = new Nodo(t, false);
                nuevaRaiz.hijos[0] = raiz;
                dividir(nuevaRaiz, 0, raiz);
                int i = 0;
                if (nuevaRaiz.claves[0] < clave) {
                    i++;
                }
                insertarNoLleno(nuevaRaiz.hijos[i], clave);
                raiz = nuevaRaiz;
            } else {
                insertarNoLleno(raiz, clave);
            }
        }
    }

    // Método auxiliar para insertar en un nodo que no está lleno
    private void insertarNoLleno(Nodo nodo, int clave) {
        int i = nodo.n - 1;
        if (nodo.esHoja) {
            while (i >= 0 && nodo.claves[i] > clave) {
                nodo.claves[i + 1] = nodo.claves[i];
                i--;
            }
            nodo.claves[i + 1] = clave;
            nodo.n++;
        } else {
            while (i >= 0 && nodo.claves[i] > clave) {
                i--;
            }
            if (nodo.hijos[i + 1].n == (2 * t - 1)) {
                dividir(nodo, i + 1, nodo.hijos[i + 1]);
                if (nodo.claves[i + 1] < clave) {
                    i++;
                }
            }
            insertarNoLleno(nodo.hijos[i + 1], clave);
        }
    }

    // Método para dividir un nodo
    private void dividir(Nodo padre, int indiceHijo, Nodo hijo) {
        Nodo nuevoNodo = new Nodo(t, hijo.esHoja);
        nuevoNodo.n = t - 1;

        for (int i = 0; i < t - 1; i++) {
            nuevoNodo.claves[i] = hijo.claves[i + t];
        }

        if (!hijo.esHoja) {
            for (int i = 0; i < t; i++) {
                nuevoNodo.hijos[i] = hijo.hijos[i + t];
            }
        }

        hijo.n = t - 1;

        for (int i = padre.n; i >= indiceHijo + 1; i--) {
            padre.hijos[i + 1] = padre.hijos[i];
        }

        padre.hijos[indiceHijo + 1] = nuevoNodo;

        for (int i = padre.n - 1; i >= indiceHijo; i--) {
            padre.claves[i + 1] = padre.claves[i];
        }

        padre.claves[indiceHijo] = hijo.claves[t - 1];

        padre.n++;
    }

    // Método para mostrar el árbol B por niveles
    public void mostrarArbol() {
        if (raiz == null) {
            System.out.println("El árbol está vacío");
            return;
        }

        Queue<Nodo> cola = new LinkedList<>();
        cola.offer(raiz);

        while (!cola.isEmpty()) {
            int nivelSize = cola.size();
            for (int i = 0; i < nivelSize; i++) {
                Nodo nodoActual = cola.poll();
                nodoActual.mostrarNodo();
                if (!nodoActual.esHoja) {
                    for (int j = 0; j <= nodoActual.n; j++) {
                        if (nodoActual.hijos[j] != null) {
                            cola.offer(nodoActual.hijos[j]);
                        }
                    }
                }
                if (i < nivelSize - 1) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
        }
    }

    // Clase interna para representar un nodo del árbol B
    private class Nodo {

        private int n;
        private int[] claves;
        private Nodo[] hijos;
        private boolean esHoja;

        public Nodo(int grado, boolean hoja) {
            this.n = 0;
            this.claves = new int[2 * grado - 1];
            this.hijos = new Nodo[2 * grado];
            this.esHoja = hoja;
        }

        // Método para mostrar el nodo
        public void mostrarNodo() {
            for (int i = 0; i < n; i++) {
                System.out.print(claves[i] + " ");
            }
        }
    }

    public void eliminarclave(int clave) {
        if (raiz == null) {
            System.out.println("El arbol está vacio");
            return;
        }
        boolean eliminada = eliminarR(raiz, clave);
        if (eliminada) {
            System.out.println("La clave " + clave + " se elimino correctamente del arbol.");
        } else {
            System.out.println("La clave " + clave + " no se encontro en el áarbol.");
        }
        // Si la raíz queda vacía luego de eliminar se debe actualizar
        if (raiz.n == 0) {
            if (raiz.esHoja) {
                raiz = null;
            } else {
                raiz = raiz.hijos[0];
            }
        }
    }  
}
