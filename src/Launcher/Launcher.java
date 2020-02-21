/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Launcher;

import Vista.Calculadora;
import java.util.Scanner;

/**
 *
 * @author javie
 */
public class Launcher {

    private static void imprimir(int[] a) {
        for(int i = 0;i <a.length ; i++){
            System.out.println(a[i]);
        }
        System.out.println("");
    }

    
     
    public static void main(String[] args) {
        Calculadora l = new Calculadora();
        System.out.println("Inserte la exprecion deseada en terminos de P y Q: (conjucion: ^, disyuncion: v, condicional: >, bicondicional: =, negacion: -)");
        Scanner  sc = new Scanner(System.in);
        String operacion = sc.nextLine();
        char[] op = operacion.toCharArray();
        System.out.println(l.convertir(op));
        imprimir(l.calcular(l.convertir(op)));
        
    }
    
}
