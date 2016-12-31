package ipc2.gt.usac.org.appejemplo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ipc2.gt.usac.org.appejemplo.ws.WebServiceClient;

public class principal extends AppCompatActivity {


    Button btn;
    Button btnEstadoCta;
    EditText editCarnet;
    EditText editNombre;
    EditText editApellido;
    EditText editMonto;
    TextView txtResult;

    //--
    Integer ca;
    String no, ap;
    Double mo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //Se manda a llamar la instancia despues de creado.
        btn = (Button) findViewById(R.id.button);
        btnEstadoCta = (Button) findViewById(R.id.btnEstadoCta);
        editCarnet = (EditText) findViewById(R.id.editCarnet);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editApellido = (EditText) findViewById(R.id.editApellido);
        editMonto = (EditText) findViewById(R.id.editMonto);
        txtResult = (TextView) findViewById(R.id.lblResultado);

        //LISTENER DEL BOTON.
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //---
                //--- Obtener los valors de los campos.
                ca = Integer.parseInt(editCarnet.getText().toString());
                no = editNombre.getText().toString();
                ap = editApellido.getText().toString();
                mo = Double.parseDouble(editMonto.getText().toString());

                /*Se crea una tarea asíncrona, para evitar tener errores con la aplicación android
                * al momento de mandar a llamar el web service y no se quede esperando una respuesta,
                * y se trabe.
                * */
                /* AsyncTask<PARAMETROS, Void, RESPUESTA>*/
                new AsyncTask<String, Void, Object>() {
                    /*Esto se ejecuta DESPUES de realizar la tarea asincrona.
                    *
                    * ----*/
                    protected void onPostExecute(Object result) {
                        super.onPostExecute(result);
                        if(result=="true") {
                            txtResult.setText("Cuenta creada exitosamente");
                        }else{
                            txtResult.setText("Ocurrió un error");
                        }
                    }
                    /*Tareas que se harán de fondo.
                    * */
                    @Override
                    protected Object doInBackground(String... params) {
                        String res;
                        res = WebServiceClient.AbrirCuentaMonetaria(ca, no, ap, mo);
                        return res;
                    }
                }.execute();
            }
        });

        //LISTENER PARA CAMBIAR DE PANTALLA.
        btnEstadoCta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ENVIO DE PARAMETROS ENTRE PANTALLAS
                Intent i = new Intent(getApplicationContext(), EstadoCuenta.class);
                i.putExtra("parametro", "PRUEBA");
                i.putExtra("abc", 12345);
                startActivity(i); //SE LLAMA A LA OTRA PANTALLA.
            }
        });
    }
}
