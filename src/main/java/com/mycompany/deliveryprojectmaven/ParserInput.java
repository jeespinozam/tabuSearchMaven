/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.deliveryprojectmaven;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author jorgeespinoza
 */
public class ParserInput {
    
    public double [][] getDistanceMatrix(ArrayList<Cliente> clientes) {
        double [][] distances = new double[clientes.size()][clientes.size()];
        double distance = 0;
        for (int i=0;i<clientes.size();i++) {
            for (int j=i+1;j<clientes.size();j++) {
                distance = Math.hypot(clientes.get(i).getX() - clientes.get(j).getX(), clientes.get(i).getY() - clientes.get(j).getY());
                distances[i][j] = distance;
                distances[j][i] = distance;
            }
        }
        return distances;
    }

    public ArrayList<Cliente> readFile(String path) {
        try {
            ArrayList<Cliente> clientes = new ArrayList<>();
            
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            boolean ignorar;
            // HashMap<Integer, Locacion> locacionesMap;
            int nNodos = 0;
            int iNodo = 0;
            int x,y;
            int i,j;
            int capacidad;
            while (true) {
                line = br.readLine();
                if (line == null)
                    break;
                if (line.startsWith("DIMENSION : ")) {
                    line = line.replace("DIMENSION : ", "");
                    nNodos = Integer.parseInt(line);
                }
                else if (line.startsWith("CAPACITY : ") && iNodo == 0) {
                    line = line.replace("CAPACITY : ", "");
                    capacidad = Integer.parseInt(line);
                    // ignoramos el tipo de vehiculo por ahora
                }
                else if (line.startsWith("NODE_COORD_SECTION ")) {
                    for (iNodo = 0; iNodo < nNodos; iNodo++) {
                        String [] tokens;
                        String nombre;
                        // se omite servicio
                        line = br.readLine();
                        tokens = line.split(" ");
                        nombre = "Locacion " + iNodo;
                        x = Integer.parseInt(tokens[1]);
                        y = Integer.parseInt(tokens[2]);
                        // se crea al nuevo cliente y se le agrega al nuevo arreglo de clientes
                        Cliente nuevoCliente = new Cliente(x,y);
                        clientes.add(nuevoCliente);
                    }
                }
                else if (line.startsWith("DEMAND_SECTION ")) {
                    for (iNodo = 0; iNodo < nNodos; iNodo++) {
                        String[] tokens;
                        String nombre;
                        int id, demanda;
                        line = br.readLine();
                        tokens = line.split(" ");
                        id = Integer.parseInt(tokens[0]);
                        demanda = Integer.parseInt(tokens[1]);
                        // se setea la demanda al respectivo cliente
                        clientes.get(iNodo).setDemanda(demanda);
                    }
                }
                else if (line.startsWith("DEPOT_SECTION")) {
                    line = br.readLine();
                }
            }
            return clientes;
        }
        catch(FileNotFoundException ex) {
            System.out.println("Archivo no encontrado ...");
            System.out.println(ex.getMessage());
            return null;
        }
        catch(IOException ex) {
            System.out.println("Error de entrada de datos");
            System.out.print(ex.getMessage());
            return null;
        }
    }
}
