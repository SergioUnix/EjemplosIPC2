/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import java.util.ArrayList;

/**
 *
 * @author r_blu
 */
public class Historial {

    private float Hmonto;
    private int noTransferencia;
    private int HcuentaD;
    private int HcuentaO;

    public Historial(int T, float mo, int ctaD, int ctaO) {
        noTransferencia = T;
        Hmonto = mo;
        HcuentaD = ctaD;
        HcuentaO = ctaO;
    }
    
    public Historial(){
        
    }

    /**
     * @return the Hmonto
     */
    public float getHmonto() {
        return Hmonto;
    }

    /**
     * @param Hmonto the Hmonto to set
     */
    public void setHmonto(float Hmonto) {
        this.Hmonto = Hmonto;
    }

    /**
     * @return the noTransferencia
     */
    public int getNoTransferencia() {
        return noTransferencia;
    }

    /**
     * @param noTransferencia the noTransferencia to set
     */
    public void setNoTransferencia(int noTransferencia) {
        this.noTransferencia = noTransferencia;
    }

    /**
     * @return the HcuentaD
     */
    public int getHcuentaD() {
        return HcuentaD;
    }

    /**
     * @param HcuentaD the HcuentaD to set
     */
    public void setHcuentaD(int HcuentaD) {
        this.HcuentaD = HcuentaD;
    }

    /**
     * @return the HcuentaO
     */
    public int getHcuentaO() {
        return HcuentaO;
    }

    /**
     * @param HcuentaO the HcuentaO to set
     */
    public void setHcuentaO(int HcuentaO) {
        this.HcuentaO = HcuentaO;
    }

}
