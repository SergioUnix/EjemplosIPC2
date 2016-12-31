/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author r_blu
 */
public class Conexion {
    //-- DRIVER -- (Definición del Driver de Postgresql)
    private static final String DB_DRIVER = "org.postgresql.Driver";
    //-- DATOS DE STRING CONN --
    private static final String cHOST = "localhost";//127.0.0.1
    private static final String cPORT = "5432"; //Postgresql PORT
    private static final String cDATABASE = "BancoIPC2";
    
    // -- DATOS DE USUARIO DE BD -- //
    private static final String cUSER = "postgres";
    private static final String cPASSWORD = "IPC2";
    
    private static final String DB_CONNECTION = "jdbc:postgresql://" + cHOST + ":" + cPORT + "/" + cDATABASE;
    
    /**
     * Metodo para conectar a la base de datos.
     *
     * @return Conexion
     */
    public Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, cUSER, cPASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }
    
    static void TestConexion(){
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String selectSQL = "select tablename from pg_tables where schemaname='public'";
        try {
            dbConnection = new Conexion().getDBConnection();

            preparedStatement = dbConnection.prepareStatement(selectSQL);
            
            ResultSet rs;
            rs = preparedStatement.executeQuery();
            String tablename;
            float monto;
            while (rs.next()) {
                tablename = rs.getString("tablename");
                System.out.println(tablename);
            }
            rs.close();
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
    }
    public static void main(String[] args) {
        Conexion.TestConexion();
    }
    
}