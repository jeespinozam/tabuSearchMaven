/*
 * To do: tiempos como pesos
 */
package com.mycompany.deliveryprojectmaven;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author jorgeespinoza
 */
public class DeliveryProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // leyendo archivo
        int numberOfFiles = 67; //numero de mapas
        int NUM_TEST = 1; //numero de intentos por cada mapa
        String [] paths = new String[numberOfFiles];
        for (int i = 0; i < numberOfFiles; i++) {
            paths[i]= i + ".vrp";
        }
        
        for (int i = 0; i < paths.length; i++) {
            for (int j = 0; j < NUM_TEST ; j++) {
                ParserInput parser = new ParserInput();
                ArrayList<Cliente> clientes = parser.readFile(paths[i]);
                double[][] distances = parser.getDistanceMatrix(clientes);
//                double[][] demands = parser.getDemand(clientes);
                
                Matrix matrix_d = new Matrix(distances);
                System.out.println("Matrix de distancias:");
                matrix_d.printMatrix();

                TabuSearch tabuSearch = new TabuSearch(matrix_d);
                Long start = System.currentTimeMillis();
                tabuSearch.invoke(paths[i], true);
                Long stop = System.currentTimeMillis() - start;
                System.out.println(String.format("Tamaño: %d\t Tiempo: %d ",matrix_d.getEdgeCount(),stop));
            }
            
            Scanner in = new Scanner(System.in);
            System.out.print("Enter para continuar: ");
            String continuar = in.nextLine();      
            
        }
    }
    
}
