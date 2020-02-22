/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Logica.Bicondicional;
import Logica.Condicional;
import Logica.Conjuncion;
import Logica.Disyuncion;
import Logica.Negacion;
import Logica.Operacion;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author javie
 */
public class Calculadora {

    private String valor;
    private Operacion signo;
    private ArrayList<Integer> contadores = new ArrayList<Integer>();
    private ArrayList<Integer> contadoresA = new ArrayList<Integer>();
    private ArrayList<Integer> contadoresC = new ArrayList<Integer>();

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    private static void imprimir(int[] a) {
        for(int i = 0;i <a.length ; i++){
            System.out.println(a[i]);
        }
        System.out.println("");
    }
    
    private static void imprimir(char[] a) {
        for(int i = 0;i <a.length ; i++){
            System.out.println(a[i]);
        }
        System.out.println("");
    }
    
    public int[] calcular(String polaca){
        
        
        Stack<int[]> letras = new Stack();
        int[] P = {1,1,0,0};
        int[] Q = {1,0,1,0};
        int[] primero,segundo;
        char[] array = polaca.toCharArray();
        for(int i = 0;i<polaca.length();i++){
            System.out.println("estoy en :"+ array[i]);
            if(polaca.charAt(i) == ' '){
                continue;
            }
            switch (polaca.charAt(i)){
                case 'P':
                    int[] p1 = {1,1,0,0};
                    letras.push(p1);
                    break;
                case 'Q':
                    int[] q1 = {1,0,1,0};
                    letras.push(q1);
                    break;
                case 'v':
                    signo = new Disyuncion();
                    segundo = letras.pop();
                    primero = letras.pop();
                    letras.push(signo.operar(primero,segundo));
                    System.out.println("v");
                    imprimir(letras.peek());
                    break;
                case '^':
                    signo = new Conjuncion();
                    letras.push(signo.operar(letras.pop(),letras.pop()));
                    System.out.println("^");
                    imprimir(letras.peek());
                    break;
                case '>':
                    signo = new Condicional();
                    segundo = letras.pop();
                    primero = letras.pop();
                    letras.push(signo.operar(primero,segundo));;
                    System.out.println(">");
                    imprimir(letras.peek());
                    break;
                case '=':
                    signo = new Bicondicional();
                    segundo = letras.pop();
                    primero = letras.pop();
                    letras.push(signo.operar(primero,segundo));
                    System.out.println("=");
                    imprimir(letras.peek());
                    break;
                case '-':
                    signo = new Negacion();
                    letras.push(signo.operar(letras.pop(),Q));
                    System.out.println("-");
                    imprimir(letras.peek());
                    break;
            }
        }
        return letras.pop();
    }
    
    
    public String convertir(char[] palabra) {

        Stack pila = new Stack();
        Stack menos = new Stack();
        Stack menosGeneral = new Stack();
        ArrayList res = new ArrayList();
        
        for (int i = 0; i < palabra.length; i++) {
            if (palabra[i] == 'P' || palabra[i] == 'Q') {
                res.add(palabra[i]);
               if (!menos.empty() && (palabra[i+1] == 'v' || palabra[i+1] == '^'|| palabra[i+1] == '>'|| palabra[i+1] == '=' )) {
                       res.add(menos.pop());
                    }
            } else {
                if (palabra[i] == '-') {
                    if (palabra[i + 1] == '(') {
                        menosGeneral.push(palabra[i]);
                        int numeroP = this.contarParentesis(i, palabra);
                        int numeroPA = this.contarParentesisA(i, palabra);
                        contadores.add(numeroP);
                        contadoresC.add(0);
                        contadoresA.add(numeroPA);
                        
                    } else {
                        menos.push(palabra[i]);
                    }
                }

                if (palabra[i] == ')') {
                    int bandera = 0;
                    if (!menos.empty()) {
                        res.add(menos.pop());
                    }
                    if(!pila.isEmpty())
                        res.add(pila.pop());
                    for (int j = 0; j < contadores.size(); j++) {
                        if (contadores.get(j).equals(0)) {
                            continue;
                        } else {
                            contadoresC.set(j, contadoresC.get(j) +1);
                            contadoresA.set(j, contadoresA.get(j) - contadoresC.get(j));
                            contadores.set(j,contadores.get(j) - 1);
                            if (contadoresA.get(j) == 0|| contadores.get(j).equals(0)) {
                                contadores.set(j, 0);
                                bandera = 1;
                                break;
                            }
                            if(i < palabra.length -1 && (palabra[i] == 'v' || palabra[i] == '^'|| palabra[i] == '>'|| palabra[i] == '=')){
                                contadoresA.set(j, contadoresA.get(j)+ contarParentesisA(i, palabra));
                            }
                        }
                        
                    }
                    if (bandera == 1) {
                        res.add(menosGeneral.pop());
                    }
                }
                if (palabra[i] == 'v' || palabra[i] == '^'|| palabra[i] == '>'|| palabra[i] == '=') {
                    pila.push(palabra[i]);
                }
            }
        }
        String resultado = "";
        for(int i = 0;i<res.size();i++){
            resultado = resultado + res.get(i).toString();
        }
        contadores.clear();
        contadoresC.clear();
        contadoresA.clear();
        return resultado;
    }

    public int contarParentesis(int pos, char[] a) {
        int c = 0;
        
        for (int i = pos + 1; i < a.length; i++) {
            if (a[i] == '(') {
                c++;
            }
        }
        return c;
    }
    
    public int contarParentesisA(int pos, char[] a){
        int c = 0;
        for(int i = pos+1;i < a.length;i++){
            if(a[i] == ')'){
                    break;
            }else if (a[i] == '('){
                c++;  
            }
        }
        return c;
    }
    
    public int contarParentesisC(int pos, char[] a){
        int c = 0;
        for(int i = pos +1;i < a.length;i++){
            if (a[i] == ')'){
                c++;
                if(i < a.length -1 &&(a[i+1] == 'v' || a[i+1] == '^'|| a[i+1] == '>'|| a[i+1] == '=')){
                    break;
                }
            }
        }
        return c;
    }

    public int proxSig(int pos, char[] a){
        int c = 0;
        for(int i = pos +1;i < a.length;i++){
                if(a[i] == 'v' || a[i] == '^'|| a[i] == '>'|| a[i] == '='){
                    return i;
                }
        }
        return c;
    }
    
}
