/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

import banco.*;

/**
 *
 * @author r_blu
 */
@WebService(serviceName = "API", targetNamespace = "http://my.org/ns/")
public class API {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "operation")
    public String operation() {
        //TODO write your implementation code here:
        return null;
    }
    
    
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "cuentaNueva")
    public boolean cuentaNueva(
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "apellido") String apellido,
            @WebParam(name = "monto") float monto,
            @WebParam(name = "carnet") int carnet) {
        return (new CuentaAhorro()).crearCuentaAhorros(nombre, apellido, monto, carnet);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "acreditarCuenta")
    public boolean acreditarCuenta(
            @WebParam(name = "noCuenta") int noCuenta,
            @WebParam(name = "monto") float monto
    ) {
        return (new CuentaAhorro()).abonarCuentaAhorros(noCuenta, monto);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "debitarCuenta")
    public boolean debitarCuenta(
            @WebParam(name = "noCuenta") int noCuenta,
            @WebParam(name = "monto") float monto
    ) {
        return (new CuentaAhorro()).retiroCuentaAhorros(noCuenta, monto);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "debitarTarjeta")
    public String debitarTarjeta() {
        //TODO write your implementation code here:
        return null;
    }
    
    
    @WebMethod(operationName = "getCuentasAsociadas")
    public CuentaAhorro[] getCientasAsociadas(@WebParam(name = "carnet") int carnet){
        return (new CuentaAhorro()).getCuenta(carnet);
    }
    
    @WebMethod(operationName = "getEstadoCta")
    public Historial[] getEstadoCta(@WebParam(name = "cuen") int cuen){
        
        return (new CuentaAhorro()).getEstadoCuenta(cuen);
    }
    
}
