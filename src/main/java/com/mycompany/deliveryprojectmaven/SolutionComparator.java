/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.deliveryprojectmaven;

import java.util.Comparator;

/**
 *
 * @author jorgeespinoza
 */
public class SolutionComparator implements Comparator<Solution>{

    @Override
    public int compare(Solution o1, Solution o2) {
        
        if(o1.value < 0 && o2.value <0){
            return Double.compare(o2.value, o1.value);
        }
        
        return Double.compare(o1.value, o2.value);
    }
    
}
