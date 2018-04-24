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
public class Cliente {
    // coordenadas
    private double x;
    private double y;
    private int demanda; // por ahora sera infinito

    ////////////////////

    public Cliente(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Cliente(double x, double y, int demanda) {
        this.x = x;
        this.y = y;
        this.demanda = demanda;
    }

    /**
     * @param demanda the demanda to set
     */
    public void setDemanda(int demanda) {
        this.demanda = demanda;
    }
    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }
    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = y;
    }
    /**
     * @return the demanda
     */
    public int getDemanda() {
        return demanda;
    }
    /**
     * @return the x
     */
    public double getX() {
        return x;
    }
    /**
     * @return the y
     */
    public double getY() {
        return y;
    }
    
}
