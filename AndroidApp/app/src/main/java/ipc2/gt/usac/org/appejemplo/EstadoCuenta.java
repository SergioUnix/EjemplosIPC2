package ipc2.gt.usac.org.appejemplo;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Iterator;

import ipc2.gt.usac.org.appejemplo.ws.WebServiceClient;
import ipc2.gt.usac.org.appejemplo.ws.utils.EstadoDeCuenta;

public class EstadoCuenta extends AppCompatActivity {

    Button btnEnviar;
    TextView txtParam1, txtParam2;
    EditText editCuenta;
    Integer cta;
    ArrayList<EstadoDeCuenta> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_cuenta);

        btnEnviar = (Button) findViewById(R.id.btnCuenta);
        txtParam1 = (TextView) findViewById(R.id.txtParams);
        txtParam2 = (TextView) findViewById(R.id.txtParam2);

        //PARAMETROS RECIBIDOS DE LA PANTALLA ANTERIOR.
        //--------------------------------------------
        Intent inte = getIntent();
        txtParam1.setText("PARAM1: " + inte.getStringExtra("parametro"));
        txtParam2.setText("PARAM2; " + inte.getIntExtra("abc", 0));
        editCuenta = (EditText) findViewById(R.id.editCuenta);

        //LISTENER DEL BOTON.
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cta = Integer.parseInt(editCuenta.getText().toString());
                //---
                //--- Obtener los valors de los campos.

                /*Se crea una tarea asíncrona, para evitar tener errores con la aplicación android
                * al momento de mandar a llamar el web service y no se quede esperando una respuesta,
                * y se trabe.
                * */
                /* AsyncTask<PARAMETROS, Void, RESPUESTA>*/
                new AsyncTask<String, Void, ArrayList<EstadoDeCuenta>>() {
                    /*Esto se ejecuta DESPUES de realizar la tarea asincrona.
                    *
                    * ----*/
                    protected void onPostExecute(ArrayList<EstadoDeCuenta> result) {
                        lista = result;
                        //Y Se manda a imprimir la lista
                    }

                    /*Tareas que se harán de fondo.
                    * */
                    @Override
                    protected ArrayList<EstadoDeCuenta> doInBackground(String... params) {
                        ArrayList<EstadoDeCuenta> res;
                        res = WebServiceClient.ObtenerEstadoCuenta(cta);
                        return res;
                    }
                }.execute();
            }
        });
    }

}
