/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import bdd.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author r_blu
 */
public class CuentaAhorro {

    public int noCuenta, carnet;
    public String nombre, apellido;
    public float monto;
    
    public CuentaAhorro(int nc,int c, String n, String a, float f){
        noCuenta = nc;
        carnet = c;
        nombre = n;
        apellido = a;
        monto = f;
    }
    
    public CuentaAhorro(){
        
    }

    public boolean crearCuentaAhorros(String nombre, String apellido, float monto, int carnet) {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String insertTableSQL = "INSERT INTO cuenta"
                + "(nombre, apellido, monto, carnet) VALUES"
                + "(?,?,?,?)";

        try {
            dbConnection = new conexion().getDBConnection();
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);

            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellido);
            preparedStatement.setFloat(3, monto);
            preparedStatement.setInt(4, carnet);

            // execute insert SQL stetement
            preparedStatement.executeUpdate();

            dbConnection.close();
            System.out.println("NUEVA CUENTA: NOMBRE: "+nombre+"-APELLIDO-: "+apellido+"-MONTO-: "+monto+"-CARNET-: "+carnet);

            return true;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean abonarCuentaAhorros(int noCuenta, float monto) {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String SQLabonar = "INSERT INTO transaccion"
                + "(monto, cuenta_destino, cuenta_origen) VALUES"
                + "(?,?,NULL)";

        if (monto > 0) {
            try {
                dbConnection = new conexion().getDBConnection();
                preparedStatement = dbConnection.prepareStatement(SQLabonar);

                preparedStatement.setFloat(1, monto);
                preparedStatement.setInt(2, noCuenta);

                // execute insert SQL stetement
                preparedStatement.executeUpdate();

                SQLabonar = "UPDATE cuenta SET monto=monto+? where no_cuenta=?";
                preparedStatement = dbConnection.prepareStatement(SQLabonar);
                preparedStatement.setFloat(1, monto);
                preparedStatement.setInt(2, noCuenta);
                preparedStatement.executeUpdate();

                dbConnection.close();
                System.out.println("ABONO A: "+noCuenta+"MONTO DE: "+monto);
                return true;

            } catch (SQLException e) {

                System.out.println(e.getMessage());
                return false;
            }
        }
        return false;
    }

    public boolean retiroCuentaAhorros(int noCuenta, float monto) {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String SQLabonar = "INSERT INTO transaccion"
                + "(monto, cuenta_destino,cuenta_origen) VALUES"
                + "(?,?,NULL)";

        if (monto > 0) {
            try {
                dbConnection = new conexion().getDBConnection();
                preparedStatement = dbConnection.prepareStatement(SQLabonar);

                preparedStatement.setFloat(1, -(monto));
                preparedStatement.setInt(2, noCuenta);

                // execute insert SQL stetement
                preparedStatement.executeUpdate();

                SQLabonar = "UPDATE cuenta SET monto=monto-? where no_cuenta=?";
                preparedStatement = dbConnection.prepareStatement(SQLabonar);
                preparedStatement.setFloat(1, monto);
                preparedStatement.setInt(2, noCuenta);
                preparedStatement.executeUpdate();
                dbConnection.close();

                System.out.println("RETIRO DE: "+noCuenta+"MONTO DE: "+monto);
                return true;

            } catch (SQLException e) {

                System.out.println(e.getMessage());
                return false;
            }
        }
        return false;
    }

    public CuentaAhorro[] getCuenta(int carnet) {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        Vector a = new Vector<>();
        CuentaAhorro[] vCuenta = new CuentaAhorro[5];

        String selectSQL = "select * from cuenta where carnet = ? limit 5";
        try {
            dbConnection = new conexion().getDBConnection();

            preparedStatement = dbConnection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, carnet);

            ResultSet rs;
            rs = preparedStatement.executeQuery();
            int noCta;
            String nombre, apellido;
            float monto;
            while (rs.next()) {
                noCta = rs.getInt("no_cuenta");
                nombre = rs.getString("nombre");
                apellido = rs.getString("apellido");
                monto = rs.getFloat("monto");
                System.out.println("#noCuenta" + noCta + "-monto: " + monto + "-nombre: " + nombre + "-apellido: " + apellido+"-carnet"+carnet);
                a.add(new CuentaAhorro(noCta,carnet,nombre,apellido,monto));
            }
            rs.close();
            a.copyInto(vCuenta);
            return vCuenta;
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

    public Historial[] getEstadoCuenta(int id) {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        Vector a = new Vector<>();
        Historial[] vHistorial = new Historial[5];

        String selectSQL = "select * from transaccion where cuenta_destino = ? limit 5";
        try {
            dbConnection = new conexion().getDBConnection();

            preparedStatement = dbConnection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, id);

            ResultSet rs;
            rs = preparedStatement.executeQuery();
            int no_trans, ctaO, ctaD;
            float monto;
            while (rs.next()) {
                no_trans = rs.getInt("no_transaccion");
                monto = rs.getFloat("monto");
                ctaO = rs.getInt("cuenta_origen");
                ctaD = rs.getInt("cuenta_destino");
                System.out.println("#TRANSF" + no_trans + "-monto: " + monto + "-ctaOrigen: " + ctaO + "-ctaDest: " + ctaD);                
                a.add(new Historial(no_trans, monto, ctaO, ctaD));
            }
            rs.close();
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

    public static void main(String[] args) {
        (new CuentaAhorro()).getCuenta(2008);
    }

}
