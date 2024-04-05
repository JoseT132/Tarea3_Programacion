package tarea3_programacion;

import java.util.Scanner;

public class Tarea3_Programacion {

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        boolean valido = false;
        int op, grado = 0, clave;
        Arbol_B arbolB = null;

        while (grado <= 2) {
            System.out.println("Ingrese el grado del arbol (Mayor a 2): ");
            grado = entrada.nextInt();
            if (grado <= 2) {
                System.out.println("Ingrese un grado mayor a 2.");
            }
        }
        System.out.println();
        arbolB = new Arbol_B(grado);
        while (!valido) {
            System.out.println("Menu:");
            System.out.println("1) INSERTAR CLAVE");
            System.out.println("2) ELIMINACION DE CLAVE");
            System.out.println("3) BUSQUEDA ");
            System.out.println("4) MOSTRAR");
            System.out.println("5) SALIR");

            op = entrada.nextInt();

            switch (op) {
                case 1:
                    System.out.println();
                    System.out.println("Ingrese la clave: ");
                    clave = entrada.nextInt();
                    arbolB.insertar(clave);
                    System.out.println("Sea ingresado correctamente");
                    System.out.println();
                    break;
                case 2:
                    System.out.println();
                    System.out.println("Ingrese la clave que desea eliminar: ");
                    clave = entrada.nextInt();
                    arbolB.eliminarclave(clave);
                    System.out.println();
                    break;
                case 3:
                    System.out.println();
                    System.out.println("Ingrese la clave que desea buscar: ");
                    clave = entrada.nextInt();
                    arbolB.buscar(clave);
                    System.out.println();
                    break;
                case 4:
                    System.out.println("Datos del arbol b:");
                    arbolB.mostrarArbol();
                    System.out.println();
                    break;
                case 5:
                    System.out.println("Saliendo del programa");
                    valido = true;
                    break;
                default:
                    break;
            }
        }
    }
}
