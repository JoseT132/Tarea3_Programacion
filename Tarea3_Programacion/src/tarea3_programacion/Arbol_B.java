
package tarea3_programacion;

import java.util.LinkedList;
import java.util.Queue;

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
    // Es necesario eliminar en forma recursiva
    private boolean eliminarR(Nodo nodo, int clave) {
        int i = 0;
        while (i < nodo.n && clave > nodo.claves[i]) {
            i++;
        }
        if (i < nodo.n && clave == nodo.claves[i]) {
            // si la clave está en el nodo
            if (nodo.esHoja) {
                eliminarH(nodo, i);
            } else {
                eliminarnoH(nodo, i);
            }
            return true; // La clave se eliminó correctamente
        } else {
            // si no es una hoja se reacomoda como hijo
            if (!nodo.esHoja) {
                boolean ultimo = (i == nodo.n);
                if (nodo.hijos[i].n < t) {
                    llenar(nodo, i);
                }
                if (ultimo && i > nodo.n) {
                    return eliminarR(nodo.hijos[i - 1], clave);
                } else {
                    return eliminarR(nodo.hijos[i], clave);
                }
            }
        }
        return false; // La clave no se encontró en el árbol
    }

    //metodo para eliminar clave de una hoja
    private void eliminarH(Nodo nodo, int indice) {
        for (int i = indice + 1; i < nodo.n; i++) {
            nodo.claves[i - 1] = nodo.claves[i];
        }
        nodo.n--;
    }
    //eliminar clave de un nodo no Hoja

    private void eliminarnoH(Nodo nodo, int indice) {
        int clave = nodo.claves[indice];
        if (nodo.hijos[indice].n >= t) {
            int anterior = obtenerAnterior(nodo, indice);
            nodo.claves[indice] = anterior;
            eliminarR(nodo.hijos[indice], anterior);
        } else if (nodo.hijos[indice + 1].n >= t) {
            int siguiente = obtenerSiguiente(nodo, indice);
            nodo.claves[indice] = siguiente;
            eliminarR(nodo.hijos[indice + 1], siguiente);
        } else {
            fusionar(nodo, indice);
            eliminarR(nodo.hijos[indice], clave);
        }
    }

    //obtener el valor anterior a la clave
    private int obtenerAnterior(Nodo nodo, int indice) {
        Nodo temp = nodo.hijos[indice];
        while (!temp.esHoja) {
            temp = temp.hijos[temp.n];
        }
        return temp.claves[temp.n - 1];
    }
    // obtener el valor siguiente a la clave

    private int obtenerSiguiente(Nodo nodo, int indice) {
        Nodo temp = nodo.hijos[indice + 1];
        while (!temp.esHoja) {
            temp = temp.hijos[0];
        }
        return temp.claves[0];
    }
    // llenar al hijo que tenga mas espacio 

    private void llenar(Nodo nodo, int indice) {
        if (indice != 0 && nodo.hijos[indice - 1].n >= t) {
            moverdesdeCAnterior(nodo, indice);
        } else {
            if (indice != nodo.n && nodo.hijos[indice + 1].n >= t) {
                moverdesdeCSiguiente(nodo, indice);
            } else {
                if (indice != nodo.n) {
                    fusionar(nodo, indice);
                } else {
                    fusionar(nodo, indice - 1);
                }
            }
        }
    }

    // mover clave del nodo anterior al actual
    private void moverdesdeCAnterior(Nodo nodo, int indice) {
        Nodo hijo = nodo.hijos[indice];
        Nodo hermano = nodo.hijos[indice - 1];

        for (int i = hijo.n - 1; i >= 0; i--) {
            hijo.claves[i + 1] = hijo.claves[i];
        }
        if (!hijo.esHoja) {
            for (int i = hijo.n; i >= 0; i--) {
                hijo.hijos[i + 1] = hijo.hijos[i];
            }
        }
        hijo.claves[0] = nodo.claves[indice - 1];
        if (!hermano.esHoja) {
            hijo.hijos[0] = hermano.hijos[hermano.n];
        }
        nodo.claves[indice - 1] = hermano.claves[hermano.n - 1];
        hijo.n++;
        hermano.n++;
    }

    private void moverdesdeCSiguiente(Nodo nodo, int indice) {
        Nodo hijo = nodo.hijos[indice];
        Nodo hermano = nodo.hijos[indice + 1];

        hijo.claves[hijo.n] = nodo.claves[indice];
        if (!hermano.esHoja) {
            hijo.hijos[hijo.n + 1] = hermano.hijos[0];
        }
        nodo.claves[indice] = hermano.claves[0];
        for (int i = 1; i < hermano.n; i++) {
            hermano.claves[i - 1] = hermano.claves[i];
        }
        if (!hermano.esHoja) {
            for (int i = 1; i <= hermano.n; i++) {
                hermano.hijos[i - 1] = hermano.hijos[i];
            }
        }

        hijo.n++;
        hermano.n--;

    }

    //fusionar un hijo con su hermano
    private void fusionar(Nodo nodo, int indice) {
        Nodo hijo = nodo.hijos[indice];
        Nodo hermano = nodo.hijos[indice + 1];

        hijo.claves[t - 1] = nodo.claves[indice];

        for (int i = 0; i < hermano.n; i++) {
            hijo.claves[i + t] = hermano.claves[i];
        }
        if (!hijo.esHoja) {
            for (int i = 0; i <= hermano.n; i++) {
                hijo.hijos[i + t] = hermano.hijos[i];
            }
        }
        for (int i = indice + 1; i < nodo.n; i++) {
            nodo.claves[i - 1] = nodo.claves[i];
        }
        for (int i = indice + 2; i >= nodo.n; i++) {
            nodo.hijos[i - 1] = nodo.hijos[i];
        }
        hijo.n += hermano.n + 1;
        nodo.n--;
    }

    // metodo para buscar una clave en el arbol
    public boolean buscar(int clave) {
        return buscarclave(raiz, clave);
    }

    private boolean buscarclave(Nodo nodo, int clave) {
        int i = 0;
        while (i < nodo.n && clave > nodo.claves[i]) {
            i++;
        }
        if (i < nodo.n && clave == nodo.claves[i]) {
            System.out.println("La clave " + clave + " se encontro en el arbol.");
            return true;
        }
        if (nodo.esHoja) {
            System.out.println("La clave " + clave + " no se encontro en el arbol.");
            return false;
        }
        return buscarclave(nodo.hijos[i], clave);
    }

}
