/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import bdd.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author r_blu
 */
public class EstadoDeCuenta {

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    private float Hmonto;
    private int noTransferencia;
    private int HcuentaD;
    private int HcuentaO;
    private int carnet;
    private String tipo;

    public EstadoDeCuenta(int T, float mo, int ctaD, int ctaO,String ctipo) {
        noTransferencia = T;
        Hmonto = mo;
        HcuentaD = ctaD;
        HcuentaO = ctaO;
        tipo = ctipo;
    }
    
    public EstadoDeCuenta(){
        
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
    
    public EstadoDeCuenta[] getEstadoCuenta(int cuenta) {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        Vector a = new Vector<>();
        

        String selectSQL = "select * from transaccion where ctaDestino = ? OR ctaOrigen= ? order by idtransaccion DESC";
        try {
            dbConnection = new Conexion().getDBConnection();

            preparedStatement = dbConnection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, cuenta);
            preparedStatement.setInt(2, cuenta);

            ResultSet rs;
            rs = preparedStatement.executeQuery();
            int no_trans, ctaO, ctaD;
            float monto;
            String cTipo="";
            while (rs.next()) {
                no_trans = rs.getInt("idtransaccion");
                monto = rs.getFloat("monto");
                ctaO = rs.getInt("ctaOrigen");
                ctaD = rs.getInt("ctaDestino");
                if(ctaO==cuenta){
                    cTipo = "Retiro";
                }
                if(ctaD==cuenta){
                    cTipo = "Deposito";
                }
                a.add(new EstadoDeCuenta(no_trans, monto, ctaO, ctaD,cTipo));
                System.out.println("#TRANSF No.: " + no_trans + " monto: " + monto + " ctaOrigen: " + ctaO + " ctaDest: " + ctaD+" Tipo: "+cTipo);                
            }
            rs.close();
            EstadoDeCuenta[] vHistorial = new EstadoDeCuenta[a.size()];
            a.copyInto(vHistorial);
            return vHistorial;
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    dbConnection.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (dbConnection != null) {
                    dbConnection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        return null;
    }
    
}
