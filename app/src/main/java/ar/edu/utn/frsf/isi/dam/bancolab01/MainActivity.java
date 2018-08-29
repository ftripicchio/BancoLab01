package ar.edu.utn.frsf.isi.dam.bancolab01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import ar.edu.utn.frsf.isi.dam.bancolab01.modelo.Cliente;
import ar.edu.utn.frsf.isi.dam.bancolab01.modelo.PlazoFijo;

public class MainActivity extends AppCompatActivity {

    private PlazoFijo pf;
    private Cliente cliente;

    // widgets de la vista
    private Button btnHacerPlazoFijo;
    private EditText edtMonto;
    private SeekBar seekDias;
    private TextView tvDias;
    private TextView tvIntereses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pf = new PlazoFijo(getResources().getStringArray(R.array.tasas));
        cliente = new Cliente();

        // widgets de la vista
        btnHacerPlazoFijo = findViewById(R.id.btnHacerPF);
        btnHacerPlazoFijo.setEnabled(false);

        edtMonto= findViewById(R.id.edtMonto);

        tvDias = findViewById(R.id.tvDiasSeleccionados);
        //tvDias.setText("10");

        tvIntereses = findViewById(R.id.tvIntereses);
        //tvIntereses.setText(pf.intereses().toString());

        seekDias = findViewById(R.id.seekDias);
        seekDias.setMax(180);
        seekDias.setProgress(10);
        seekDias.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvDias.setText(progress);
                pf.setDias(progress);
                tvIntereses.setText(pf.intereses().toString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


    }
}
