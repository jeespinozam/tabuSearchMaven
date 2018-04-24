/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.deliveryprojectmaven;

/**
 *
 * @author jorgeespinoza
 */
public class TabuList {
    int [][] tabuList ;

    public TabuList(int numCities){
        tabuList = new int[numCities][numCities]; //city 0 is not used here, but left for simplicity
    }

    public void tabuMove(int city1, int city2){ //tabus the swap operation
        if(city1>city2){ //guarantee that the uper matrix is modified
            int temp = city1;
            city1 = city2;
            city2 = temp;
        }
        //short term memomy & long term memory
        if(tabuList[city1][city2] <= 0 ){
            tabuList[city1][city2] = 3;
        }else{
            tabuList[city2][city1]+= 1;
        }
        
    }

    public void decrementTabu(){
        for(int i = 0; i<tabuList.length; i++){
            for(int j = 0; j<tabuList.length; j++){
                if(i!=j && i<j){ //guarantee that the uper matrix is modified
                    tabuList[i][j]-=tabuList[i][j]<=0?0:1;
                }
            }
        }
    }
    
    public int getValue(int i, int j){
        return this.tabuList[i][j];
    }
}
