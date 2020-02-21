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
public class Negacion implements Operacion {

    private int[] res;
    
    @Override
    public int[] operar(int[] a, int[] b) {
        
        res = a;
        for(int i = 0; i <4;i++){
            if(a[i] == 1){
                res[i] = 0;
                continue;
            } 
            if(a[i] == 0)
                res[i] = 1;
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
