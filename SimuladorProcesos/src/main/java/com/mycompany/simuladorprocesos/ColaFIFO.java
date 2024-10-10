/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.simuladorprocesos;

/**
 *
 * @author Lalva
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Nodo<T> {
    T valor;
    Nodo<T> siguiente;

    public Nodo(T valor) {
        this.valor = valor;
        this.siguiente = null;
    }
}


public class ColaFIFO<T> {
    private Nodo<T> frente; 
    private Nodo<T> finalCola; 
    private int tamaño; 

    public ColaFIFO() {
        frente = null;
        finalCola = null;
        tamaño = 0;
    }

    
    public void encolar(T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento);
        if (estaVacia()) {
            frente = nuevoNodo; 
        } else {
            finalCola.siguiente = nuevoNodo; 
        }
        finalCola = nuevoNodo; 
        tamaño++;
    }

    
    public T desencolar() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola está vacía");
        }
        T valor = frente.valor; 
        frente = frente.siguiente; 
        if (frente == null) {
            finalCola = null; 
        }
        tamaño--;
        return valor;
    }

    
    public T frente() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola está vacía");
        }
        return frente.valor;
    }

    
    public boolean estaVacia() {
        return frente == null;
    }

    
    public int tamaño() {
        return tamaño;
    }

    
    public void imprimirCola() {
        Nodo<T> actual = frente;
        while (actual != null) {
            System.out.print(actual.valor + " ");
            actual = actual.siguiente;
        }
        System.out.println();
    }

    public void ordenarPorTiempoLlegada() {
        if (estaVacia()) {
            return;  
        }

        
        List<Proceso> listaProcesos = new ArrayList<>();
        while (!estaVacia()) {
            listaProcesos.add((Proceso) desencolar());  
        }

        
        Collections.sort(listaProcesos, new Comparator<Proceso>() {
            @Override
            public int compare(Proceso p1, Proceso p2) {
                return Integer.compare(p1.getTiempoLlegada(), p2.getTiempoLlegada());
            }
        });

        
        for (Proceso proceso : listaProcesos) {
            encolar((T) proceso);  
        }
    }

    
    public T obtenerElemento(int indice) {
        if (indice < 0 || indice > tamaño) {
            throw new IndexOutOfBoundsException("Índice fuera de los límites de la cola. Índice: " + indice);
        }
    
        
        if (tamaño == 1) {
            return frente.valor; 
        }
    
        
        Nodo<T> actual = frente; 
        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente; 
        }
        return actual.valor; 
    }
    


}