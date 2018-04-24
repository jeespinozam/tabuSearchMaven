/*
 * To do: tiempos como pesos
 */
package com.mycompany.deliveryprojectmaven;

import java.util.ArrayList;
import javax.swing.JFrame;

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
        ParserInput parser = new ParserInput();
        ArrayList<Cliente> clientes = parser.readFile("A-n32-k5.vrp");
        double[][] d = parser.getDistanceMatrix(clientes);
        
        /*
        int[][] d = new int[][]{{0 , 1 , 3 , 99, 5 },
                                {1 , 0 , 99, 4 , 8 },
                                {3 , 1 , 0 , 5 , 1 },
                                {4 , 4 , 5 , 0 , 99},
                                {5 , 8 , 99, 99, 0 }};
        */
        int[][] x = new int[][]{{0 , 1 , 1 , 0 , 1 },
                                {1 , 0 , 0 , 1 , 1 },
                                {1 , 0 , 0 , 1 , 0 },
                                {0 , 1 , 1 , 0 , 0 },
                                {1 , 1 , 0 , 0 , 0 }};
        
        // Matrix matrix_d = new Matrix(32);
        Matrix matrix_d = new Matrix(d,x);
        
        System.out.println("Matrix de distancias:");
        matrix_d.printMatrix();
        
        int[] solution = new int[matrix_d.getEdgeCount()+1]; //+1 for the depart as the last route
        
        //Simple Initial Solution  
        solution[0] = 0; solution[matrix_d.getEdgeCount()]= 0; //begin and end in the depart
        for (int i = 1; i < solution.length - 1; i++) {
            solution[i]= i;
        }
        
        
        System.out.println("Solución inicial:");
        for (int i = 0; i < solution.length; i++) {
            System.out.print(solution[i] + " ");
        }
        System.out.println("");
        double costo_ini = matrix_d.calculateDistance(solution);
        System.out.println("Costo inicial = " + costo_ini + "\n"); 
        
        TabuSearch tabuSearch = new TabuSearch(matrix_d);
        Long start = System.currentTimeMillis();
        tabuSearch.invoke();
        Long stop = System.currentTimeMillis() - start;
        System.out.println(String.format("Tamaño: %d\t Tiempo: %d ",matrix_d.getEdgeCount(),stop));
        
        
//        int size = 10;
//        while (size < 100) {
//            Matrix matrix_d = new Matrix(size);
//            matrix_d.printMatrix();
//            TabuSearch tabuSearch = new TabuSearch(matrix_d);
//            Long start = System.currentTimeMillis();
//            tabuSearch.invoke();
//            Long stop = System.currentTimeMillis() - start;
//            System.out.println(String.format("Rozmiar: %d\t czas: %d ",size,stop));
//            size +=10;
//        }
    }
    
}
