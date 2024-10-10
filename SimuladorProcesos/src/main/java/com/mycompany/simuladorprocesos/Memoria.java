/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.simuladorprocesos;

/**
 *
 * @author Lalva
 */
public class Memoria {
    int memoriaDisponible;

    public Memoria(int tamaño) {
        this.memoriaDisponible = tamaño;
    }
    public int getDisponible() {
        return memoriaDisponible;
    }

    public boolean cargarProceso(Proceso proceso) {
        if (memoriaDisponible >= proceso.getTamaño()) {
            memoriaDisponible -= proceso.getTamaño();
            return true;
        } else {
            return false;
        }
    }

    public void liberarMemoria(Proceso proceso) {
        memoriaDisponible += proceso.getTamaño();
    }
}
