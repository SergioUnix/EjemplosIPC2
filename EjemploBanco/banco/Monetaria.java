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
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author r_blu
 */
public class Monetaria {

    public int noCuenta, carnet;
    public String nombre, apellido;
    public float monto;

    public Monetaria(int nc, int c, String n, String a, float f) {
        noCuenta = nc;
        carnet = c;
        nombre = n;
        apellido = a;
        monto = f;
    }

    public Monetaria() {

    }

    public boolean crearCuentaAhorros(String nombre, String apellido, Double monto, Integer carnet) {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String insertTableSQL = "INSERT INTO ahorro"
                + "(nombre, apellido, monto, carnet) VALUES"
                + "(?,?,?,?)";

        try {
            dbConnection = new Conexion().getDBConnection();
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);

            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellido);
            preparedStatement.setDouble(3, monto);
            preparedStatement.setInt(4, carnet);

            // execute insert SQL stetement
            preparedStatement.executeUpdate();

            dbConnection.close();
            System.out.println("NUEVA CUENTA: NOMBRE: " + nombre + " APELLIDO : " + apellido + " MONTO : " + monto + " CARNET : " + carnet);

            return true;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean crearTarjetaDebito(int carnet, int noCuenta) {
        if (validarCarnetCuenta(carnet, noCuenta)) {
            Connection dbConnection = null;
            PreparedStatement preparedStatement = null;

            String insertTableSQL = "INSERT INTO tarjeta"
                    + "(noCuenta) VALUES"
                    + "(?)";

            try {
                dbConnection = new Conexion().getDBConnection();
                preparedStatement = dbConnection.prepareStatement(insertTableSQL);

                preparedStatement.setInt(1, noCuenta);

                // execute insert SQL stetement
                preparedStatement.executeUpdate();

                dbConnection.close();
                System.out.println("Se creó una nueva Tarjeta para: " + carnet + " Asociada a la cuenta: " + noCuenta + ".");

                return true;

            } catch (SQLException e) {

                System.out.println(e.getMessage());
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean abonarCuentaAhorros(int noCuenta, float monto) {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String SQLabonar = "INSERT INTO transaccion"
                + "(monto,ctaDestino,ctaOrigen) VALUES"
                + "(?,?,NULL)";

        if (monto > 0) {
            try {
                dbConnection = new Conexion().getDBConnection();
                preparedStatement = dbConnection.prepareStatement(SQLabonar);

                preparedStatement.setFloat(1, monto);
                preparedStatement.setInt(2, noCuenta);

                // execute insert SQL stetement
                preparedStatement.executeUpdate();

                SQLabonar = "UPDATE ahorro SET monto=monto+? where noCuenta=?";
                preparedStatement = dbConnection.prepareStatement(SQLabonar);
                preparedStatement.setFloat(1, monto);
                preparedStatement.setInt(2, noCuenta);
                preparedStatement.executeUpdate();

                dbConnection.close();
                System.out.println("ABONO A: " + noCuenta + "UN MONTO DE: " + monto);
                return true;

            } catch (SQLException e) {

                System.out.println(e.getMessage());
                return false;
            }
        }
        return false;
    }

    public int obtenerCuenta(int noTarjeta) {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        int cta = 0;

        String selectSQL = "SELECT T.noCuenta FROM TARJETA T,AHORRO A\n"
                + "where T.noCuenta = A.noCuenta\n"
                + "AND T.noTarjeta = ?";
        try {
            dbConnection = new Conexion().getDBConnection();

            preparedStatement = dbConnection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, noTarjeta);

            ResultSet rs;
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                cta = rs.getInt("noCuenta");
                System.out.println("# No. Tarjeta: " + noTarjeta + " CuentaAsociada: " + cta);
            }
            rs.close();
            return cta;
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
        return cta;
    }

    public boolean retiroCuentaAhorros(int noCuenta, float monto) {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String SQLabonar = "INSERT INTO transaccion"
                + "(monto, ctaOrigen,ctaDestino) VALUES"
                + "(?,?,NULL)";
        if (monto > 0) {
            try {
                dbConnection = new Conexion().getDBConnection();
                preparedStatement = dbConnection.prepareStatement(SQLabonar);

                preparedStatement.setFloat(1, -(monto));
                preparedStatement.setInt(2, noCuenta);

                // execute insert SQL stetement
                preparedStatement.executeUpdate();

                SQLabonar = "UPDATE ahorro SET monto=monto-? where noCuenta=?";
                preparedStatement = dbConnection.prepareStatement(SQLabonar);
                preparedStatement.setFloat(1, monto);
                preparedStatement.setInt(2, noCuenta);
                preparedStatement.executeUpdate();
                dbConnection.close();

                System.out.println("RETIRO DE: " + noCuenta + "MONTO DE: " + monto);
                return true;

            } catch (SQLException e) {

                System.out.println(e.getMessage());
                return false;
            }
        }
        return false;
    }

    public Monetaria[] getCuentas(int carnet) {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        Vector a = new Vector<Monetaria>();

        String selectSQL = "select * from ahorro where carnet = ?";
        try {
            dbConnection = new Conexion().getDBConnection();

            preparedStatement = dbConnection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, carnet);

            ResultSet rs;
            rs = preparedStatement.executeQuery();
            int noCta;
            String nombre, apellido;
            float monto;
            while (rs.next()) {

                noCta = rs.getInt("noCuenta");
                nombre = rs.getString("nombre");
                apellido = rs.getString("apellido");
                monto = rs.getFloat("monto");
                System.out.println("#NoCuenta: " + noCta + " monto: Q" + monto + " nombre: " + nombre + " apellido: " + apellido + " carnet: " + carnet);
                a.add(new Monetaria(noCta, carnet, nombre, apellido, monto));
            }
            rs.close();
            Monetaria[] vCuenta = new Monetaria[a.size()];
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

    public int getTarjeta(int carnet, int cuenta) {
        this.crearTarjetaDebito(carnet, cuenta);
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        Vector a = new Vector<Monetaria>();
        int idTarjeta = 0;

        String selectSQL = "select T.noTarjeta\n"
                + "from tarjeta T, ahorro C\n"
                + "where c.noCuenta = t.noCuenta\n"
                + "AND c.carnet = ?\n"
                + "order by t.notarjeta desc limit 1";
        try {
            dbConnection = new Conexion().getDBConnection();

            preparedStatement = dbConnection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, carnet);

            ResultSet rs;
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                idTarjeta = rs.getInt("noTarjeta");
            }
            rs.close();
            return idTarjeta;
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
        return idTarjeta;
    }

    public Integer[] showTarjetas(int carnet, int cuenta) {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        Vector a = new Vector<Integer>();
        

        String selectSQL = "select T.noTarjeta\n"
                + "from tarjeta T, ahorro C\n"
                + "where c.noCuenta = t.noCuenta\n"
                + "AND c.carnet = ?\n"
                + "AND c.noCuenta = ?\n"
                + "order by t.notarjeta desc";
        try {
            dbConnection = new Conexion().getDBConnection();

            preparedStatement = dbConnection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, carnet);
            preparedStatement.setInt(2, cuenta);

            ResultSet rs;
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                a.add(rs.getInt("noTarjeta"));
                System.out.println("La cuenta> "+cuenta+" Tiene la tarjeta: "+rs.getInt("noTarjeta"));
            }
            rs.close();
            Integer[] idTarjeta = new Integer[a.size()];
            a.copyInto(idTarjeta);
            return idTarjeta;
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

    public boolean validarCarnetCuenta(int carnet, int cuenta) {
        boolean valida = false;
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String selectSQL = "select * from ahorro where carnet = ? AND noCuenta=?";
        try {
            dbConnection = new Conexion().getDBConnection();

            preparedStatement = dbConnection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, carnet);
            preparedStatement.setInt(2, cuenta);

            ResultSet rs;
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                valida = true;
                System.out.println("#NoCuenta: " + cuenta + "Para Carnet: " + carnet + " es válida.");
            }
            rs.close();
            return valida;
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
        System.out.println("#NoCuenta: " + cuenta + "Para Carnet: " + carnet + "NO es válida.");
        return valida;
    }

    public static void main(String[] args) {
//        System.out.println((new Monetaria()).obtenerCuenta(1));
//        (new Monetaria()).getCuentas(2008);
//        (new Monetaria()).getCuentas(2008);
//        (new Monetaria()).abonarCuentaAhorros(1, 14);
//         System.out.println((new Historial()).getEstadoCuenta(1));
//         System.out.println((new Monetaria()).getTarjeta(2008));
//        (new Monetaria()).crearCuentaAhorros("Reimer", "Chamale", 100f, 2008);
    }

}
