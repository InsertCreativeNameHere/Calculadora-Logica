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
    private ArrayList contadores = new ArrayList();

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
    
    public int[] calcular(String polaca){
        
        
        Stack<int[]> letras = new Stack();
        int[] P = {1,1,0,0};
        int[] Q = {1,0,1,0};
        int[] primero,segundo;
        char[] array = polaca.toCharArray();
        for(int i = 0;i<array.length;i++){
            if(array[i] == ' '){
                continue;
            }
            switch (array[i]){
                case 'P':
                    letras.push(P);
                    break;
                case 'Q':
                    letras.push(Q);
                    imprimir(letras.peek());
                    break;
                case 'v':
                    signo = new Disyuncion();
                    segundo = letras.pop();
                    primero = letras.pop();
                    letras.push(signo.operar(primero,segundo));
                    break;
                case '^':
                    signo = new Conjuncion();
                    letras.push(signo.operar(letras.pop(),letras.pop()));
                    break;
                case '>':
                    signo = new Condicional();
                    imprimir(letras.peek());
                    segundo = letras.pop();
                    primero = letras.pop();
                    letras.push(signo.operar(primero,segundo));;
                    break;
                case '=':
                    signo = new Bicondicional();
                    segundo = letras.pop();
                    primero = letras.pop();
                    letras.push(signo.operar(primero,segundo));
                    break;
                case '-':
                    signo = new Negacion();
                    letras.push(signo.operar(letras.pop(),Q));
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
                        contadores.add(numeroP);
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
                            contadores.set(j, Integer.valueOf(contadores.get(j).toString()) - 1);
                        }
                        if (contadores.get(j).equals(0)) {
                            bandera = 1;
                            break;
                        }
                    }
                    if (bandera == 1) {
                        res.add(menosGeneral.pop());
                    }
                }
                if (palabra[i] == 'v' || palabra[i] == '^'|| palabra[i] == '>'|| palabra[i] == '=') {
                    pila.push(palabra[i]);
                }
                if (palabra[i] == '(') {
                    System.out.print(" ");
                }
            }
        }
        String resultado = "";
        for(int i = 0;i<res.size();i++){
            resultado = resultado + res.get(i).toString();
        }
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

}
