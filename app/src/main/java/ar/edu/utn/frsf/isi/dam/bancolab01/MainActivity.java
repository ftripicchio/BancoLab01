package ar.edu.utn.frsf.isi.dam.bancolab01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ar.edu.utn.frsf.isi.dam.bancolab01.modelo.Cliente;
import ar.edu.utn.frsf.isi.dam.bancolab01.modelo.PlazoFijo;

public class MainActivity extends AppCompatActivity {

    private PlazoFijo pf;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //pf = new PlazoFijo(getResources());
        cliente = new Cliente();
    }
}
