package ipc2.example.reimerghost.ejemplo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Principal extends AppCompatActivity {

    Button botonPrueba;
    TextView ejTextView;
    EditText texto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        addListenerOnButton();
    }

    public void addListenerOnButton() {

        botonPrueba = (Button) findViewById(R.id.ipc2Boton);
        ejTextView = (TextView) findViewById(R.id.Salida);
        texto = (EditText) findViewById(R.id.mensaje);

        botonPrueba.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ejTextView.setText("Hola "+texto.getText()+"!!!");



            }

        });

    }}
