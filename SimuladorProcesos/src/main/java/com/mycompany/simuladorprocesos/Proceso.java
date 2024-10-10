/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.simuladorprocesos;

/**
 *
 * @author Lalva
 */
public class Proceso {
    
    private String id;
    private String nombre;
    private int tamaño;
    private int tiempoEjecucion;
    private int prioridad;
    private int tiempoLlegada;
    private int tiempoSubidaCPU = -1;
    private int ultimoCPU;
    private int sumatiempos;
    private int tiempo_ejecucion_previa = 0;
    

    
    public Proceso(String id, String nombre, int tamaño, int tiempoEjecucion, int prioridad, int tiempoLlegada) {
        this.id = id;
        this.nombre = nombre;
        this.tamaño = tamaño;
        this.tiempoEjecucion = tiempoEjecucion;
        this.prioridad = prioridad;
        this.tiempoLlegada = tiempoLlegada;
    }

    public Proceso() {
    }

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }

    public int getTiempoEjecucion() {
        return tiempoEjecucion;
    }

    public void setTiempoEjecucion(int tiempoEjecucion) {
        this.tiempoEjecucion = tiempoEjecucion;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public int getTiempoLlegada() {
        return tiempoLlegada;
    }

    public void setTiempoLlegada(int tiempoLlegada) {
        this.tiempoLlegada = tiempoLlegada;
    }

    public int  getTiempoSubidaCPU() {
        return tiempoSubidaCPU;
    }

    public void setTiemposubidaCPU(int tiempoSubidaCPU) {
        this.tiempoSubidaCPU = tiempoSubidaCPU;
    }

    public int getUltimoCPU() {
        return ultimoCPU;
    }

    public void setUltimoCPU(int ultimoCPU) {
        this.ultimoCPU = ultimoCPU;
    }
    public int getSumatiempos() {
        return sumatiempos;
    }
    public void setSumatiempos(int sumatiempos) {
        this.sumatiempos = sumatiempos;
    }

    public int getTiempo_ejecucion_previa() {
        return tiempo_ejecucion_previa;
    }

    public void setTiempo_ejecucion_previa(int tiempo_ejecucion_previa) {
        this.tiempo_ejecucion_previa = tiempo_ejecucion_previa;
    }

    
    @Override
    public String toString() {
        return "\nProceso{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", tamaño=" + tamaño +
                ", tiempoEjecucion=" + tiempoEjecucion +
                ", prioridad=" + prioridad +
                ", tiempoLlegada=" + tiempoLlegada +
                ". tiempoSubidaCPU=" + tiempoSubidaCPU +
                ". ultimoCPU=" + ultimoCPU +
                '}';
    }
}