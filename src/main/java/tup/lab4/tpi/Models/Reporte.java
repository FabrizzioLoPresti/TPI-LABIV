package tup.lab4.tpi.Models;

public class Reporte {
    float sueldoNeto;
    String  nombreArea;

    public Reporte() {

    }

    public Reporte(float sueldoNeto, String nombreArea) {
        this.sueldoNeto = sueldoNeto;
        this.nombreArea = nombreArea;
    }

    public float getSueldoNeto() {
        return sueldoNeto;
    }

    public void setSueldoNeto(float sueldoNeto) {
        this.sueldoNeto = sueldoNeto;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }

}
