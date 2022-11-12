package tup.lab4.tpi.Models;

public class FechaDTO {
    private int anio;
    private int mes;

    public FechaDTO() {

    }

    public FechaDTO(int anio, int mes) {
        this.anio = anio;
        this.mes = mes;
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
}
