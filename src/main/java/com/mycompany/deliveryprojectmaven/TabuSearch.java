/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.deliveryprojectmaven;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import org.math.plot.*;

/**
 *
 * @author jorgeespinoza
 */
public class TabuSearch {
    private TabuList tabuList;
    private final Matrix matrix;

    int[] currSolution;
    int numberOfIterations;
    int problemSize;

    private int[] bestSolution;
    private double bestCost;

    double[] x;
    double[] y;
    
    public TabuSearch(Matrix matrix) {
        this.matrix = matrix;
        problemSize = matrix.getEdgeCount();
        numberOfIterations = problemSize * 1000;

        tabuList = new TabuList(problemSize);
        setupCurrentSolution();
        setupBestSolution();

    }

    private void setupBestSolution() {
        bestSolution = new int[problemSize + 1];
        System.arraycopy(currSolution, 0, bestSolution, 0, bestSolution.length);
        bestCost = matrix.calculateDistance(bestSolution);
    }

    private void setupCurrentSolution() {
        currSolution = new int[problemSize + 1];
        for (int i = 0; i < problemSize; i++)
            currSolution[i] = i;
        currSolution[problemSize] = 0;
    }


    private void printSolution(int[] solution) {
        for (int i = 0; i < solution.length; i++) {
            System.out.print(solution[i] + " ");
        }
        System.out.println();
    }
    
    private boolean isTabu(int j, int k){
        if (tabuList.tabuList[j][k] > 0){
            return true;
        }
        return false;
    }
    
    private ArrayList<Solution> BestNeighBourSolutionLocator(){
        ArrayList<Solution> soluciones = new ArrayList();
        
        for (int j = 1; j < currSolution.length - 1; j++) {
            for (int k = j; k < currSolution.length - 1; k++) {
                if (j != k) {
                    int [] tempSolution = new int[currSolution.length];
                    System.arraycopy(currSolution, 0, tempSolution, 0, tempSolution.length);
                    swap(tempSolution, j, k);
                    double currCost = matrix.calculateDistance(tempSolution);
                    currCost += tabuList.getValue(k, j);
                    soluciones.add(new Solution(j, k, currCost));
                }
            }
        }
        
        return soluciones;
    }
    
    private boolean aspirationCriteriaIsApplied(Solution s){
        if(s.value < bestCost) return true;
        
        return false;
    }
    
    private boolean isAceptable(){
        
        return true;
    }
    
    public void invoke(String path, boolean plot) {
        
        int numberOfIterationsWithSameBestCost = 0;
        boolean isLocalMaximum = false;
        boolean isStopper = false;
        
        x = new double[numberOfIterations];
        y = new double[numberOfIterations];
        int i;
        for ( i = 0; i < numberOfIterations; i++) {
            System.out.println("En la iteración " + i + ": Costo:" + bestCost);
            x[i] = (double) i;
            y[i] = (double) bestCost;
            boolean isAspirationCriteria = false;
            int city1 = 0;
            int city2 = 0;
            
            
            //get the neighbour 
            ArrayList<Solution> soluciones = BestNeighBourSolutionLocator();
            
            //choose the best (choose the less negative too) and (not tabu or verify aspiration criteria)
//            soluciones.sort(new SolutionComparator());
            Random r = new Random();
            int index = r.nextInt(soluciones.size());
            soluciones.get(index);
            city1 = soluciones.get(index).i;
            city2 = soluciones.get(index).j;
            
            while(index<soluciones.size() && isTabu(city1, city2)){
                if(aspirationCriteriaIsApplied(soluciones.get(index))){
                    break;
                };
                
                index = r.nextInt(soluciones.size());
                
                city1 = soluciones.get(index).i;
                city2 = soluciones.get(index).j;
            }
            
            if(index==soluciones.size()){
                isStopper = true;
                break;
            }
            
            //if not tabu -> swap and select it as best solution and increment the tabu
            swap(currSolution, city1, city2);
            double currCost = soluciones.get(index).value;
            if (isAceptable() && (currCost < bestCost)) {
                System.arraycopy(currSolution, 0, bestSolution, 0, bestSolution.length);
                bestCost = currCost;
                
                tabuList.decrementTabu();
                tabuList.tabuMove(city1, city2);
                
            }else{
                numberOfIterationsWithSameBestCost +=1;
                
                tabuList.decrementTabu();
            }
            
//          Local solution stop condition
            if(numberOfIterationsWithSameBestCost>=100){
                isLocalMaximum = true;
                break;
            }
            
        }

        if(isLocalMaximum){
            System.out.println("Búsqueda terminada en un posible máximo local: " + numberOfIterationsWithSameBestCost + " iteraciones \nCosto final = " + bestCost + "\nMejor Solucion :");
            printSolution(bestSolution);
        }else if(isStopper){
            System.out.println("Búsqueda terminada! La solución actual no permite continuar" + numberOfIterationsWithSameBestCost + " iteraciones \nCosto final = " + bestCost + "\nMejor Solucion :");
            printSolution(bestSolution);
        }else{
            System.out.println("Búsqueda terminada! \nCosto final = " + bestCost + "\nMejor Solucion :");
            printSolution(bestSolution);
        }
        
        ParserInput parser = new ParserInput();
        parser.printFile(path, bestSolution, bestCost);
        if (plot) plotIteration();
    }
    
    private void plotIteration(){
        Plot2DPanel plot = new Plot2DPanel();
        
        int cant = 0;
        for (int j = 0; j < numberOfIterations; j++) {
            if(y[j]!= 0.0){
                cant +=1;
            }
        }
        double[] tempy = new double[cant];
        double[] tempx = new double[cant];
        for (int i = 0; i < cant; i++) {
            tempx[i] = x[i];
            tempy[i] = y[i];
        }
        
        plot.addLinePlot("Iteración vs Costo", tempx, tempy);
        JFrame frame = new JFrame("Panel");
        frame.setContentPane(plot);
        frame.setSize(800, 800);
        frame.setVisible(true);
    }

    private void swap(int[] solution, int i, int k) {
        int temp = solution[i];
        solution[i] = solution[k];
        solution[k] = temp;
    }
}
