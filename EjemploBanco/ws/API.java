/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import banco.Monetaria;
import banco.EstadoDeCuenta;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author r_blu
 */
@WebService(serviceName = "API")
public class API {

    /**
     *
     */
    @WebMethod(operationName = "AbrirCuentaMonetaria")
    public boolean AbrirCuentaMonetaria(@WebParam(name = "carnet") Integer carnet, @WebParam(name = "nombre") String nombre, @WebParam(name = "apellido") String apellido, @WebParam(name = "montoinicial") Double inicial) {
        Monetaria ca = new Monetaria();
        if (carnet>0 && !nombre.isEmpty() && !apellido.isEmpty() && inicial>0) {
            return ca.crearCuentaAhorros(nombre, apellido, inicial, carnet);
        } else {
            return false;
        }
    }

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "generarNuevaTarjeta")
    public int generarNuevaTarjeta(@WebParam(name = "cuenta") int cuenta, @WebParam(name = "carnet") int carnet) {
        return (new Monetaria()).getTarjeta(carnet, cuenta);
    }

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "getTarjetasAsociadas")
    public Integer[] getTarjetasAsociadas(@WebParam(name = "cuenta") int cuenta, @WebParam(name = "carnet") int carnet) {
        return (new Monetaria()).showTarjetas(carnet, cuenta);
    }

    /**
     * This is a sample web service operation
     *
     * @param tarjeta
     * @param monto
     * @return boolean
     */
    @WebMethod(operationName = "hacerPagoConTarjeta")
    public boolean hacerPagoConTarjeta(@WebParam(name = "noTarjeta") int tarjeta, @WebParam(name = "monto") float monto) {
        Monetaria c = new Monetaria();
        int cta = c.obtenerCuenta(tarjeta);
        return c.retiroCuentaAhorros(cta, monto);
    }

    /**
     * This is a sample web service operation
     *
     * @param cuenta
     * @param monto
     * @return Boolean
     */
    @WebMethod(operationName = "agregarDeposito")
    public boolean agregarDeposito(@WebParam(name = "noCuenta") int cuenta, @WebParam(name = "monto") float monto) {
        return (new Monetaria()).abonarCuentaAhorros(cuenta, monto);
    }

    /**
     * This is a sample web service operation
     *
     * @param carnet
     * @return Monetaria[]
     */
    @WebMethod(operationName = "listaCuentasAsociadas")
    public Monetaria[] listaCuentasAsociadas(@WebParam(name = "carnet") int carnet) {
        return (new Monetaria()).getCuentas(carnet);
    }

    /**
     * Servicio que muestra un array de Estados de cuenta.
     *
     * @param cuenta
     * @return EstadoDeCuenta[]
     */
    @WebMethod(operationName = "todoEstadoDeCuenta")
    public EstadoDeCuenta[] todoEstadoDeCuenta(@WebParam(name = "cuenta") int cuenta) {
        return (new EstadoDeCuenta()).getEstadoCuenta(cuenta);
    }

}
