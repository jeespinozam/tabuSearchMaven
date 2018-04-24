/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.deliveryprojectmaven;
import java.util.Random;

/**
 *
 * @author jorgeespinoza
 */

public class Matrix {
    private int MAX_COST = 9999;
    private double[][] matrix;
    private int[][] boolean_matrix;
    private int edgeCount;

    public Matrix(int size) {
        edgeCount = size;
        matrix = new double[size][size];
        generateMatrix(size);
        generateBooleanMatrix(size);
    }

    public Matrix(double[][] matrix) {
        edgeCount = matrix.length;
        this.matrix = new double[edgeCount][edgeCount];
        for (int i = 0; i < edgeCount; i++) {
            for (int j = 0; j < edgeCount; j++) {
                this.matrix[i][j] = matrix[i][j];
            }
        }
    }
    
    public Matrix(double[][] matrix, int[][]boolean_matrix) {
        edgeCount = matrix.length;
        this.matrix = new double[edgeCount][edgeCount];
        for (int i = 0; i < edgeCount; i++) {
            for (int j = 0; j < edgeCount; j++) {
                this.matrix[i][j] = matrix[i][j];
            }
        }
        generateBooleanMatrix(edgeCount);
//        this.boolean_matrix = new int[edgeCount][edgeCount];
//        for (int i = 0; i < edgeCount; i++) {
//            for (int j = 0; j < edgeCount; j++) {
//                this.boolean_matrix[i][j] = boolean_matrix[i][j]==0?MAX_COST:1;
//            }
//        }
        
    }

    /****************GETTER***********************/
    
    public int getEdgeCount() {
        return edgeCount;
    }

    public double getWeight(int from, int to) {
        return matrix[from][to];
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public int getSize() {
        return edgeCount;
    }

    public void generateBooleanMatrix(int size) {
//        Random random = new Random();
//        this.boolean_matrix = new int[size][size];
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                this.boolean_matrix[i][j] = random.nextInt(1);
//                
//                if(this.boolean_matrix[i][j]==0){
//                    this.boolean_matrix[i][j]= MAX_COST;
//                }
//            }
//        }
        this.boolean_matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.boolean_matrix[i][j] = 1;
                
            }
        }
    }
    
    public void printMatrix() {
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                System.out.print(matrix[i][j]);
                if(matrix[i][j]<10){
                    System.out.print("   ");
                }else if(matrix[i][j]<100){
                    System.out.print("  ");
                }else{
                    System.out.print(" ");
                }
            }
            System.out.println("\n ");
        }
    }

    //función objetivo
    public int calculateDistance(int solution[]) {
        int cost = 0;
        for (int i = 0; i < solution.length - 1; i++) {
//            System.out.println("La solution[" + i + "]: " + solution[i]);
//            System.out.println("La solution[" + (i + 1) + "]: " + solution[i+1]);
//            
            cost += matrix[solution[i]][solution[i + 1]]*boolean_matrix[solution[i]][solution[i + 1]];
            
//            System.out.println("El costo es:" + cost);
        }
        return cost;
    }

    private void generateMatrix(int size) {
        Random random = new Random();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (row != col) {
                    int value = random.nextInt(100) + 1; //random between 1 and 100
                    matrix[row][col] = value;
                    matrix[col][row] = value;
                }
            }
        }
    }
}
