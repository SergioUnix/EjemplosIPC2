package ipc2.gt.usac.org.appejemplo.ws.utils;

/**
 * Created by r_blu on 29/12/2016.
 */

public class EstadoDeCuenta {
//ESTA CLASE SI ES NECESARIA CREARLA NOSOTROS MISMOS CON SUS SETTERS Y GETTERS
    private float Hmonto;
    private int noTransferencia;
    private int HcuentaD;
    private int HcuentaO;
    private int carnet;
    private String tipo;

    public EstadoDeCuenta(int cuentaD,int cuentaO,float hmonto, int notrans, String t){
        HcuentaD = cuentaD;
        HcuentaO = cuentaO;
        Hmonto = hmonto;
        noTransferencia = notrans;
        tipo = t;
    }


    public float getHmonto() {
        return Hmonto;
    }

    public void setHmonto(float hmonto) {
        Hmonto = hmonto;
    }

    public int getNoTransferencia() {
        return noTransferencia;
    }

    public void setNoTransferencia(int noTransferencia) {
        this.noTransferencia = noTransferencia;
    }

    public int getHcuentaD() {
        return HcuentaD;
    }

    public void setHcuentaD(int hcuentaD) {
        HcuentaD = hcuentaD;
    }

    public int getHcuentaO() {
        return HcuentaO;
    }

    public void setHcuentaO(int hcuentaO) {
        HcuentaO = hcuentaO;
    }

    public int getCarnet() {
        return carnet;
    }

    public void setCarnet(int carnet) {
        this.carnet = carnet;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
