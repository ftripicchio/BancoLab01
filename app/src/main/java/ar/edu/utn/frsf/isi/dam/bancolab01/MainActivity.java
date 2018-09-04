package ar.edu.utn.frsf.isi.dam.bancolab01;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import ar.edu.utn.frsf.isi.dam.bancolab01.modelo.Cliente;
import ar.edu.utn.frsf.isi.dam.bancolab01.modelo.Moneda;
import ar.edu.utn.frsf.isi.dam.bancolab01.modelo.PlazoFijo;

public class MainActivity extends AppCompatActivity {

    private PlazoFijo pf;
    private Cliente cliente;

    // widgets de la vista
    private Button btnHacerPlazoFijo;
    private EditText edtMail;
    private EditText edtCuit;
    private EditText edtMonto;
    private SeekBar seekDias;
    private TextView tvDias;
    private TextView tvIntereses;
    private RadioGroup groupMoneda;
    private RadioButton optPeso;
    private RadioButton optDolar;
    private Switch swAviso;
    private ToggleButton btnRenovar;
    private CheckBox chkAceptaTerminos;
    private TextView mensajes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pf = new PlazoFijo(getResources().getStringArray(R.array.tasas));
        cliente = new Cliente();

        // widgets de la vista
        btnHacerPlazoFijo = findViewById(R.id.btnHacerPF);
        btnHacerPlazoFijo.setEnabled(false);

        edtMail = findViewById(R.id.edCorreo);

        edtCuit = findViewById(R.id.edtCuit);

        optDolar = findViewById(R.id.optDolar);

        optPeso = findViewById(R.id.optPeso);

        groupMoneda = findViewById(R.id.optMoneda);
        groupMoneda.check(R.id.optPeso);

        edtMonto= findViewById(R.id.edtMonto);

        tvDias = findViewById(R.id.tvDiasSeleccionados);
        tvDias.setText("10");
        pf.setDias(10);

        tvIntereses = findViewById(R.id.tvIntereses);
        tvIntereses.setText(pf.intereses().toString());

        seekDias = findViewById(R.id.seekDias);
        seekDias.setMax(180);
        seekDias.setProgress(10);

        swAviso = findViewById(R.id.swAvisarVencimiento);

        btnRenovar = findViewById(R.id.togAccion);

        chkAceptaTerminos = findViewById(R.id.chkTerminos);

        mensajes = findViewById(R.id.tvMessages);


        groupMoneda.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                String selectedRadioButtonText = selectedRadioButton.getText().toString();
                if(selectedRadioButtonText == optPeso.getText().toString()){
                    pf.setMoneda(Moneda.PESO);
                }else if (selectedRadioButtonText == optDolar.getText().toString()){
                    pf.setMoneda(Moneda.DOLAR);
                }
            }
        });
        edtMonto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals("")){
                    pf.setMonto(0.0);
                    tvIntereses.setText(pf.intereses().toString());
                }else{
                    double monto = Double.parseDouble(s.toString());
                    pf.setMonto(monto);
                    tvIntereses.setText(pf.intereses().toString());
                }

            }
        });
        seekDias.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvDias.setText(new Integer(progress).toString() + " días de plazo");
                pf.setDias(progress);
                tvIntereses.setText(pf.intereses().toString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        chkAceptaTerminos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked){
                   btnHacerPlazoFijo.setEnabled(true);
               }else{
                   btnHacerPlazoFijo.setEnabled(false);
                   Context context = getApplicationContext();
                   CharSequence text = "Es obligatorio aceptar las condiciones";
                   int duration = Toast.LENGTH_SHORT;
                   Toast.makeText(context, text, duration).show();
               }
            }
        });
        btnHacerPlazoFijo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean error = false;
                String errores = "";

                String mail = edtMail.getText().toString();
                if(mail.equals("")) {
                    error = true;
                    errores = errores + "Debe ingresar un mail. ";
                }
                else cliente.setMail(mail);

                String cuit = edtCuit.getText().toString();
                if(cuit.equals("")) {
                    error = true;
                    errores = errores + "Debe ingresar un cuit. ";
                }
                else cliente.setCuit(cuit);

                pf.setCliente(cliente);

                String monto = edtMonto.getText().toString();
                if(monto.equals("") || Double.parseDouble(monto)<=0) {
                    error = true;
                    errores = errores + "El monto debe ser superior a 0. ";
                }
                else pf.setMonto(Double.parseDouble(monto));

                int dias = seekDias.getProgress();
                if(dias<10) {
                    error = true;
                    errores = errores + "El plazo debe ser superior a 10 días. ";
                }
                else pf.setDias(dias);

                pf.setAvisarVencimiento(swAviso.isChecked());
                pf.setRenovarAutomaticamente(btnRenovar.isChecked());

                if(error){
                    Context context = getApplicationContext();
                    CharSequence text = "Error";
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(context, text, duration).show();
                    mensajes.setText(errores);
                    mensajes.setTextColor(Color.RED);
                }else{
                    mensajes.setText(pf.toString());
                    mensajes.setTextColor(Color.BLUE);
                }
            }
        });

    }
}
