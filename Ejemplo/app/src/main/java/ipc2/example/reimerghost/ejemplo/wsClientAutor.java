package ipc2.example.reimerghost.ejemplo;


import android.os.AsyncTask;

import org.ksoap2.*;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


/**
 * Created by reimer on 01/01/2016.
 */
public class wsClientAutor extends AsyncTask<String, String, String> {


    //PROPIEDADES DEL WEB SERVICE QUE USTEDES CREARON
    public static final String wsURL = "http://192.168.0.8:8084/ipc2test/wsAutor?wsdl";
    public static final String NAMESPACE = "http://ws.ipc2.usac.com/";
    private static final String AGREGAR_AUTOR = "ipc2AgregarAutor";
    private static final String OBTENER_AUTORES = "ipc2AgregarAutor";

    private static String laIP;
    private String resultado;

    public wsClientAutor() {
    }

    public String getResult() {
        return resultado;
    }

    @Override
    protected String doInBackground(String... params) {
        publishProgress("Loading contents..."); // Calls onProgressUpdate()
        SoapObject request = new SoapObject(NAMESPACE, AGREGAR_AUTOR);

        request.addProperty("nombre", params[0]);
        request.addProperty("apellido", params[1]);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = false;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(wsURL);

        try {

            androidHttpTransport.call(NAMESPACE, envelope);
            SoapPrimitive result = (SoapPrimitive) envelope.getResponse();

            //to get the data
            resultado = result.toString();
            // 0 is the first object of data


            return resultado;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }


}
