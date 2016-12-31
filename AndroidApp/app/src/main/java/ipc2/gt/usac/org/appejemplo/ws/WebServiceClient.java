package ipc2.gt.usac.org.appejemplo.ws;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalDate;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.ObjectInput;
import java.util.ArrayList;
import java.util.Vector;

import ipc2.gt.usac.org.appejemplo.ws.utils.EstadoDeCuenta;

//KSOAP2
public class WebServiceClient {
    //Namespace of the Webservice - can be found in WSDL
    private static String NAMESPACE = "http://ws/";
    //DIRECCION DE SU WEB SERVICE
    /*Tienen que usar una direccion IP, porque si ponen localhost o 127.0.0.1 se esta refiriendo a
    * su dispositivo como localhost, y ahí no hay ningun servicio publicado.
    * De preferencia si hacen una red hotspot con su Android y su computadora.
    * */
    private static String URL = "http://192.168.0.4:8080/bancoIPC2/API?wsdl";
    //ESTO LO ENCUENTRAN EN EL WSDL
    private static String SOAP_ACTION = "http://ws/";

    //---------------------------
    //METODO PARA ABRIR CUENTA---
    //---------------------------
    public static String AbrirCuentaMonetaria(Integer carnet, String nombre, String apellido, Double monto) {
        String resTxt = null;
        String nombreMetodoWS = "AbrirCuentaMonetaria";

        // SE CREA UNA SOLICITUD, A LA CUAL SE LE ASIGNARÁ LOS PARAMETROS.
        // SE ENVIA EL METODO.
        SoapObject request = new SoapObject(NAMESPACE, nombreMetodoWS);
        // PARAMETROS

        //FORMA (1) DE ENVIAR PARAMETROS
        //ENVIAR CARNET
        PropertyInfo piCarnet = new PropertyInfo();
        piCarnet.setName("carnet");
        piCarnet.setValue(carnet);
        piCarnet.setType(Integer.class);
        request.addProperty(piCarnet); //SE AGREGA PARAMETRO A LA SOLICITUD
//        request.addProperty("carnet",carnet);

        //ENVIAR CARNET
        PropertyInfo piNombre = new PropertyInfo();
        piNombre.setName("nombre");
        piNombre.setValue(nombre);
        piNombre.setType(String.class);
        request.addProperty(piNombre); //SE AGREGA PARAMETRO A LA SOLICITUD
//        request.addProperty("nombre",nombre);

        //ENVIAR CARNET
        PropertyInfo piApellido = new PropertyInfo();
        piApellido.setName("apellido");
        piApellido.setValue(apellido);
        piApellido.setType(String.class);
        request.addProperty(piApellido); //SE AGREGA PARAMETRO A LA SOLICITUD
//        request.addProperty("apellido",apellido);

        //ENVIAR CARNET
        PropertyInfo piMonto = new PropertyInfo();
        piMonto.setName("montoinicial");
        piMonto.setValue(monto);
        piMonto.setType(Double.class);
        request.addProperty(piMonto); //SE AGREGA PARAMETRO A LA SOLICITUD
//        request.addProperty("montoinicial",monto);

        // SE CREA EL ENVELOPE, POR MEDIO DE ESTE OBJETO
        //SE ENVIA LA SOLICITUD (request) CON TODOS LOS PARAMETROS
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        //PARA DOT NET
//        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        //CUANDO TENEMOS UN VALOR DE TIPO DOUBLE SE UTILIZA ESTE OBJETO
        //Y SE AÑADE AL OBJETO A ENVELOPE.
        MarshalDouble md = new MarshalDouble();
        md.register(envelope);

        // CREAR una llamada HTTP a su Web Service.
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Se invoca el servicio.
            androidHttpTransport.call("\"" + SOAP_ACTION + nombreMetodoWS + "\"", envelope);
            // SE OBTIENE EL RESULTADO. SI SON VALORES PRIMITIVOS (String, int, float)
            // SE USA SOAP PRIMITIVE, SI NO, SE USA SOAPOBJECT.
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // SE ENVIA EL VALOR DE LA RESPUESTA
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured: " + e.getMessage();
        }
        return resTxt;
    }

    //METODO PARA LLAMAR EL ESTADO DE CUENTA (LISTA DE OBJETOS)
    public static ArrayList<EstadoDeCuenta> ObtenerEstadoCuenta(Integer cuenta) {
        ArrayList<EstadoDeCuenta> resEstadoCuenta = new ArrayList<EstadoDeCuenta>();
        EstadoDeCuenta edcuenta;
        String webMethName = "todoEstadoDeCuenta";
        // SE CREA UNA SOLICITUD, A LA CUAL SE LE ASIGNARÁ LOS PARAMETROS.
        // SE ENVIA EL METODO.
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // PARAMETROS

        //FORMA (2) DE ENVIAR PARAMETROS. MAS SENCILLO.

        request.addProperty("cuenta", cuenta);

        // SE CREA EL ENVELOPE, POR MEDIO DE ESTE OBJETO
        //SE ENVIA LA SOLICITUD (request) CON TODOS LOS PARAMETROS
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        //PARA DOT NET
        //envelope.dotNet = true;

        // SE ALMACENA LA SOLICITUD EN UN ENVELOPE.
        envelope.setOutputSoapObject(request);

        // CREAR una llamada HTTP a su Web Service.
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // SE INVOCA EL SERVICIO
            envelope.addMapping(NAMESPACE, "EstadoDeCuenta", EstadoDeCuenta.class);
            androidHttpTransport.call(SOAP_ACTION, envelope);

            //SE DEFINE UN OBJETO TIPO VECTOR DE SOAPOBJECT
            //UN SOAPOBJECT RECONOCE CUALQUIER OBJETO QUE SE RECIBA.
            Vector<SoapObject> result = (Vector<SoapObject>) envelope.getResponse();
            //LAS PROPIEDADES DEL OBJETO QUE NECESITAMOS TRAER
            int cuentaD;
            int cuentaO;
            float hmonto;
            int notrans;
            String tipo;
            //ALMACENAR LOS OBJETOS EN UN ARRAY
            for (int i = 0; i < result.size(); i++) {
                SoapObject so = result.get(i);
                //el getProperty, el nombre del valor tiene que ser EXACTAMENTE igual que lo que retorna
                //el servicio.
                cuentaD = Integer.parseInt(so.getProperty("hcuentaD").toString());
                cuentaO = Integer.parseInt(so.getProperty("hcuentaO").toString());
                hmonto = Float.parseFloat(so.getProperty("hmonto").toString());
                notrans = Integer.parseInt(so.getProperty("noTransferencia").toString());
                tipo = so.getProperty("tipo").toString();
                edcuenta = new EstadoDeCuenta(cuentaD, cuentaO, hmonto, notrans, tipo);
                resEstadoCuenta.add(edcuenta);
                //---------------------------------------------------------------------
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resEstadoCuenta;
    }
}