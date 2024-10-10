/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.simuladorprocesos;

/**
 *
 * @author Lalva
 */
import java.util.Random;
import java.util.Scanner;

public class SimuladorProcesos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ColaFIFO<Proceso> colaProcesos = new ColaFIFO<>();
        Memoria memoria = new Memoria(1024); // Tamaño de memoria fijo para la simulación (1024k)
        ColaFIFO<Proceso> colaListosEjecucion = new ColaFIFO<>(); // Cola para Round Robin
        Random random = new Random();
        int tiempo = 0;

        System.out.println("Bienvenido al Simulador de Planificación de Procesos.");
        int opcion;

        
        
        Proceso proceso1 = new Proceso("P1", "Proceso 1", 100, 6, 1, 1);
        Proceso proceso2 = new Proceso("P2", "Proceso 2", 100, 18, 1, 4);
        Proceso proceso3 = new Proceso("P3", "Proceso 3", 100, 12, 1, 6);
        Proceso proceso4 = new Proceso("P4", "Proceso 4", 100, 17, 1, 7);
        colaProcesos.encolar(proceso1);
        colaProcesos.encolar(proceso2);
        colaProcesos.encolar(proceso3);
        colaProcesos.encolar(proceso4);

        do {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Agregar proceso manualmente");
            System.out.println("2. Generar procesos automáticamente");
            System.out.println("3. Listar procesos en la cola");
            System.out.println("4. Iniciar simulación Round Robin");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    
                    Proceso procesoManual = new Proceso();
                    System.out.println("\n--- Agregar Proceso Manualmente ---");
                    System.out.print("ID: ");
                    procesoManual.setId(scanner.next());
                    System.out.print("Nombre: ");
                    procesoManual.setNombre(scanner.next());
                    System.out.print("Tamaño: ");
                    procesoManual.setTamaño(scanner.nextInt());
                    System.out.print("Tiempo de ejecución: ");
                    procesoManual.setTiempoEjecucion(scanner.nextInt());
                    System.out.print("Prioridad: ");
                    procesoManual.setPrioridad(scanner.nextInt());
                    System.out.print("Tiempo de llegada: ");
                    procesoManual.setTiempoLlegada(scanner.nextInt());

                    
                    colaProcesos.encolar(procesoManual);
                    System.out.println("Proceso agregado exitosamente.");
                    break;

                case 2:
                    
                    System.out.println("\n--- Generar Procesos Automáticamente ---");
                    System.out.print("¿Cuántos procesos desea generar? ");
                    int cantidadProcesos = scanner.nextInt();

                    for (int i = 0; i < cantidadProcesos; i++) {
                        
                        String procesoId = "P" + (i + 1);
                        String procesoNombre = "Proceso" + (i + 1);
                        int procesoTamaño = random.nextInt(10) + 50; 
                        int procesoTiempoEjecucion = random.nextInt(500) + 100; 
                        int procesoPrioridad = random.nextInt(10) + 1; 
                        int procesoTiempoLlegada = random.nextInt(1000); 

                        
                        Proceso procesoAutomatico = new Proceso(procesoId, procesoNombre, procesoTamaño,
                                procesoTiempoEjecucion, procesoPrioridad, procesoTiempoLlegada);
                        colaProcesos.encolar(procesoAutomatico);
                    }
                    System.out.println("Se generaron " + cantidadProcesos + " procesos automáticamente.");
                    break;

                case 3:
                    
                    System.out.println("\n--- Listar Procesos en la Cola ---");
                    colaProcesos.imprimirCola();
                    break;

                case 4:
                    if (colaProcesos.estaVacia()) {
                        System.out.println("No hay procesos en la cola para simular.");
                    } else {
                    System.out.print("Ingrese el quantum de tiempo (en milisegundos): ");
                    int quantum = scanner.nextInt();
                    colaProcesos.ordenarPorTiempoLlegada();


                    boolean simulacionTerminada = false;


                    ColaFIFO<Proceso> procesosCompletados = new ColaFIFO<Proceso>(); 


                    int numero_procesos = colaProcesos.tamaño();
                    do {
                        simulacionTerminada = true;  
                        int size = colaProcesos.tamaño();
                        for (int i = 0; i < size; i++) {
                            Proceso procesoActual = colaProcesos.desencolar();


                            if (memoria.cargarProceso(procesoActual)) {
                                if(procesoActual.getTiempoLlegada() <= tiempo){
                                    colaListosEjecucion.encolar(procesoActual);
                                    mensaje_encolar(tiempo, colaProcesos, colaListosEjecucion, memoria, procesoActual);

                                    if(!colaProcesos.estaVacia()){
                                        int tiempoAux = tiempo;
                                        for(int k = 0; k<=colaProcesos.tamaño(); k++){
                                            Proceso proceso_siguiente = colaProcesos.obtenerElemento(k);
                                            if(proceso_siguiente.getTiempoLlegada() < tiempo+quantum){
                                                proceso_siguiente = colaProcesos.desencolar();
                                                if (memoria.cargarProceso(proceso_siguiente)) {
                                                    tiempo = proceso_siguiente.getTiempoLlegada();
                                                    colaListosEjecucion.encolar(proceso_siguiente);
                                                    mensaje_encolar(tiempo, colaProcesos, colaListosEjecucion, memoria, proceso_siguiente);
                                                    tiempo = tiempoAux;
                                                }
                                            }
                                            else break;
                                        }
                                    }


                                    while (!colaListosEjecucion.estaVacia()) {
                                        Proceso procesoEnCPU = colaListosEjecucion.desencolar();
                                        memoria.liberarMemoria(procesoEnCPU);

                                        if (procesoEnCPU.getTiempoSubidaCPU() == -1) {
                                            procesoEnCPU.setTiemposubidaCPU(tiempo);
                                        }

                                        int tiempoRestante = Math.min(quantum, procesoEnCPU.getTiempoEjecucion());

                                        procesoEnCPU.setTiempo_ejecucion_previa(procesoEnCPU.getTiempo_ejecucion_previa() + tiempoRestante);

                                        for (int j = tiempoRestante; j > 0; j--) {
                                            System.out.println("\nTiempo actual: " + tiempo + " ms. Ejecutando proceso " + procesoEnCPU.getNombre() + " por " + j + " ms.");
                                            tiempo++;
                                            procesoEnCPU.setTiempoEjecucion(procesoEnCPU.getTiempoEjecucion() - 1);

                                            if (procesoEnCPU.getTiempoEjecucion() == 0) {
                                                procesoEnCPU.setTiempo_ejecucion_previa(procesoEnCPU.getTiempo_ejecucion_previa() - tiempoRestante);
                                                procesoEnCPU.setSumatiempos(tiempo);
                                                procesoEnCPU.setUltimoCPU(tiempo-tiempoRestante);
                                                System.out.println("Proceso " + procesoEnCPU.getNombre() + " ha completado su ejecución.");
                                                procesosCompletados.encolar(procesoEnCPU); 
                                                break;  
                                            }

                                        }


                                        if (procesoEnCPU.getTiempoEjecucion() > 0) {
                                            System.out.println("Quantum agotado. El proceso " + procesoEnCPU.getNombre() + " tiene " + procesoEnCPU.getTiempoEjecucion() + " ms restantes.");
                                            colaProcesos.encolar(procesoEnCPU);  
                                        }

                                        imprimirColas(colaProcesos, colaListosEjecucion);
                                    }
                                }
                                else{
                                    System.out.println("\nTiempo: " + tiempo);
                                    memoria.liberarMemoria(procesoActual);
                                    colaProcesos.encolar(procesoActual);
                                    colaProcesos.ordenarPorTiempoLlegada();
                                    tiempo++;
                                }

                            } else {
                                System.out.println("Memoria insuficiente para cargar el proceso " + procesoActual.getId() + ". Memoria disponible: " + memoria.getDisponible());
                                colaProcesos.encolar(procesoActual);
                                break;  
                            }
                        }




                        if (!colaProcesos.estaVacia()) {
                            simulacionTerminada = false;  
                        }
                    } while (!simulacionTerminada);

                    System.out.println("Todos los procesos han terminado.");

                    procesosCompletados.imprimirCola();

                    System.out.println("\n--- Resumen de la Simulación ---");
                    int sumatoria_Espera=0;
                    int sumatoria_Ejecucion=0;
                    int sumatoria_respuesta = 0;
                    while (!procesosCompletados.estaVacia()) {
                        Proceso procesoFinalizado = procesosCompletados.desencolar();
                        sumatoria_Espera += procesoFinalizado.getUltimoCPU() - procesoFinalizado.getTiempoLlegada() - procesoFinalizado.getTiempo_ejecucion_previa();
                        sumatoria_Ejecucion += procesoFinalizado.getSumatiempos() - procesoFinalizado.getTiempoLlegada();
                        sumatoria_respuesta += procesoFinalizado.getTiempoSubidaCPU() - procesoFinalizado.getTiempoLlegada();
                    }



                    float tiempo_espera_promedio = sumatoria_Espera/numero_procesos;
                    System.out.println("Tiempo de espera promedio: " + tiempo_espera_promedio);
                    float tiempo_ejecucion_promedio = sumatoria_Ejecucion/numero_procesos;
                    System.out.println("Tiempo de ejecución promedio: " + tiempo_ejecucion_promedio);
                    float tiempo_respuesta_promedio = sumatoria_respuesta/numero_procesos;
                    System.out.println("Tiempo de respuesta promedio: " + tiempo_respuesta_promedio);
                }
                break;


                case 5:
                    
                    System.out.println("Saliendo del simulador...");
                    break;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }

        } while (opcion != 5);
    }

    public static void imprimirColas(ColaFIFO<Proceso> c1, ColaFIFO<Proceso> c2){
        System.out.println("\nCola de procesos:");
        c1.imprimirCola();
        System.out.println("\nCola de listos para ejecución:");
        c2.imprimirCola();
    }

    public static void mensaje_encolar(int tiempo, ColaFIFO<Proceso> colaProcesos, ColaFIFO<Proceso> colaListosEjecucion, Memoria memoria, Proceso procesoActual){
        System.out.println("\n-----------------------------------------------");
        System.out.println("\nTiempo:" + tiempo);
        System.out.println("\nSubió el proceso " + procesoActual.getNombre() + " a la cola de listos para ejecución. Memoria disponible: " + memoria.getDisponible());
        imprimirColas(colaProcesos, colaListosEjecucion);
        System.out.println("\n-----------------------------------------------");    

    }
}


