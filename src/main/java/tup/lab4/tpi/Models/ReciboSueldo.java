package tup.lab4.tpi.Models;

public class ReciboSueldo {

    private int id;

    private int legajo;
    private int anio;
    private int mes;
    private float sueldoBruto;
    private float montoAntiguedad;
    private float jubilacion;
    private float obraSocial;
    private float fondoAltaComplejidad;

    public ReciboSueldo() {

    }

    public ReciboSueldo(int id, int legajo, int anio, int mes, float sueldoBruto, float montoAntiguedad, float jubilacion, float obraSocial, float fondoAltaComplejidad) {
        this.id = id;
        this.legajo = legajo;
        this.anio = anio;
        this.mes = mes;
        this.sueldoBruto = sueldoBruto;
        this.montoAntiguedad = montoAntiguedad;
        this.jubilacion = jubilacion;
        this.obraSocial = obraSocial;
        this.fondoAltaComplejidad = fondoAltaComplejidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public float getSueldoBruto() {
        return sueldoBruto;
    }

    public void setSueldoBruto(float sueldoBruto) {
        this.sueldoBruto = sueldoBruto;
    }

    public float getMontoAntiguedad() {
        return montoAntiguedad;
    }

    public void setMontoAntiguedad(float montoAntiguedad) {
        this.montoAntiguedad = montoAntiguedad;
    }

    public float getJubilacion() {
        return jubilacion;
    }

    public void setJubilacion(float jubilacion) {
        this.jubilacion = jubilacion;
    }

    public float getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(float obraSocial) {
        this.obraSocial = obraSocial;
    }

    public float getFondoAltaComplejidad() {
        return fondoAltaComplejidad;
    }

    public void setFondoAltaComplejidad(float fondoAltaComplejidad) {
        this.fondoAltaComplejidad = fondoAltaComplejidad;
    }


}
