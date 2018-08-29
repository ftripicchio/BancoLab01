package ar.edu.utn.frsf.isi.dam.bancolab01.modelo;

import android.util.Log;

import java.util.Arrays;

public class PlazoFijo {

    private Integer dias;
    private Double monto;
    private Boolean avisarVencimiento;
    private Boolean renovarAutomaticamente;
    private Moneda moneda;
    private String[] tasas;
    private Cliente cliente;

    public PlazoFijo(String[] tasas) {
        Log.d("APP_01", tasas + "");
        Log.d("APP_01", Arrays.toString(tasas));
        this.tasas = tasas;
        this.monto = 0.0;
        this.dias = 0;
        this.avisarVencimiento = false;
        this.renovarAutomaticamente = false;
        this.moneda = Moneda.PESO;
    }

    public Integer getDias() {
        return dias;
    }

    public Double getMonto() {
        return monto;
    }

    public Boolean getAvisarVencimiento() {
        return avisarVencimiento;
    }

    public Boolean getRenovarAutomaticamente() {
        return renovarAutomaticamente;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public String[] getTasas() {
        return tasas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public void setAvisarVencimiento(Boolean avisarVencimiento) {
        this.avisarVencimiento = avisarVencimiento;
    }

    public void setRenovarAutomaticamente(Boolean renovarAutomaticamente) {
        this.renovarAutomaticamente = renovarAutomaticamente;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public void setTasas(String[] tasas) {
        this.tasas = tasas;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "PlazoFijo{" +
                "dias=" + dias +
                ", monto=" + monto +
                ", avisarVencimiento=" + avisarVencimiento +
                ", renovarAutomaticamente=" + renovarAutomaticamente +
                ", moneda=" + moneda +
                ", tasas=" + Arrays.toString(tasas) +
                ", cliente=" + cliente +
                '}';
    }

    private Double tasa(){
        if(dias< 30 && monto <=5000) return Double.valueOf(tasas[0]);
        else if(dias>= 30 && monto <=5000) return Double.valueOf(tasas[1]);
        else if(dias< 30 && monto <=99999) return Double.valueOf(tasas[2]);
        else if(dias>= 30 && monto <=99999) return Double.valueOf(tasas[3]);
        else if(dias< 30 && monto >99999) return Double.valueOf(tasas[4]);
        else if(dias>= 30 && monto >99999) return Double.valueOf(tasas[5]);
        else return 0.0;
    }

    public Double intereses(){
        Double tasaInteres = tasa();
        Double interes = monto * ( Math.pow(1 + tasaInteres/100.0, dias/360.0) -1);
        return interes;
    }


}
