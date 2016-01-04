package ipc2.example.reimerghost.ejemplo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import android.app.Activity;

import java.util.concurrent.ExecutionException;

/**
 * @author Reimer Chamale
 */
public class Principal extends Activity {


    private TextView salida;

    private EditText etNombre, etApellido;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        salida = (TextView) findViewById(R.id.salida);
        salida.setText("Esperando...");

        etNombre = (EditText) findViewById(R.id.textNombre);
        etApellido = (EditText) findViewById(R.id.textApellido);

        btnGuardar = (Button) findViewById(R.id.btnEnviar);

        addListenerOnButton();

    }

    public void addListenerOnButton() {

        btnGuardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String stNombre = etNombre.getText().toString();
                String stApellido = etApellido.getText().toString();
                String respuesta;
                try {
                    if (stNombre.isEmpty() || stApellido.isEmpty()) {
                        respuesta = "ERROR: No puede dejar los Campos Vacios";
                    } else {
                        respuesta = new wsClientAutor().execute(stNombre, stApellido).get();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    respuesta = e.getMessage();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    respuesta = e.getMessage();
                }
                salida.setText(respuesta);


            }

        });

    }


}