/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

/**
 *
 * @author javie
 */
public class Conjuncion implements Operacion {

    private int[] res;
    
    
    @Override
    public int[] operar(int[] a, int[] b) {
        res = a;
        for (int i = 0; i <4 ;i++){
            if(a[i] == 1 && b[i] == 1){
                res[i] = 1;
            }else{
                res[i] = 0;
            }
        }
        return res;
    }

    public int[] getRes() {
        return res;
    }

    public void setRes(int[] res) {
        this.res = res;
    }
    
}
