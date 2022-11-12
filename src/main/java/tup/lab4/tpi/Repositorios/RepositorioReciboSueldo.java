package tup.lab4.tpi.Repositorios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.annotation.ApplicationScope;
import tup.lab4.tpi.Models.ReciboSueldo;
import tup.lab4.tpi.Models.ReciboSueldoDTO;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

@Repository
@ApplicationScope
public class RepositorioReciboSueldo {

    @Autowired
    private DataSource dataSource;

    public RepositorioReciboSueldo() {
    }

    public ArrayList<ReciboSueldoDTO> getRecibosSueldo(int legajo) {
        try {
            ArrayList<ReciboSueldoDTO> recibosSueldo = new ArrayList<>();
            Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM recibosueldo WHERE legajoEmpleado = " + legajo);

            while (rs.next()) {
                int id = rs.getInt(1);
                int anio = rs.getInt(3);
                int mes = rs.getInt(4);
                float sueldoBruto = rs.getFloat(5);
                float jubilacion = rs.getFloat(6);
                float obraSocial = rs.getFloat(7);
                float fondoAltaComplejidad = rs.getFloat(8);
                float montoAntiguedad = rs.getFloat(9);

                float sueldoNeto = sueldoBruto + montoAntiguedad - jubilacion - obraSocial - fondoAltaComplejidad;

                ReciboSueldoDTO rDTO = new ReciboSueldoDTO(legajo, anio, mes, sueldoBruto, montoAntiguedad, jubilacion, obraSocial, fondoAltaComplejidad, sueldoNeto);
                recibosSueldo.add(rDTO);
            }
            rs.close();
            stmt.close();
            conn.close();
            return recibosSueldo;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String addReciboSueldo(ReciboSueldoDTO reciboSueldo) {
        try {
            Connection conn = dataSource.getConnection();

            // Validar que el empleado exista
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM empleado WHERE legajo = " + reciboSueldo.getLegajo());
            float sueldoBruto = 0;

            if (!rs.next()) {
                throw new RuntimeException("El empleado no existe");
            } else {
                sueldoBruto = rs.getFloat(7);
            }

            // Validar que el recibo no exista
            PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM recibosueldo WHERE legajoEmpleado = ? AND año = ? AND mes = ?");
            stmt2.setInt(1, reciboSueldo.getLegajo());
            stmt2.setInt(2, reciboSueldo.getAnio());
            stmt2.setInt(3, reciboSueldo.getMes());

            ResultSet rs2 = stmt2.executeQuery();
            if (rs2.next()) {
                throw new RuntimeException("El recibo ya existe");
            }

            // Insertar el recibo
            PreparedStatement stmt3 = conn.prepareStatement("INSERT INTO recibosueldo (legajoEmpleado, año, mes, sueldoBruto, jubilacion, obraSocial, fondoAltaComplejidad, montoAntiguedad) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            stmt3.setInt(1, reciboSueldo.getLegajo());
            stmt3.setInt(2, reciboSueldo.getAnio());
            stmt3.setInt(3, reciboSueldo.getMes());
            stmt3.setFloat(4, reciboSueldo.getSueldoBruto());
            stmt3.setFloat(5, reciboSueldo.getJubilacion());
            stmt3.setFloat(6, reciboSueldo.getObraSocial());
            stmt3.setFloat(7, reciboSueldo.getFondoAltaComplejidad());
            stmt3.setFloat(8, reciboSueldo.getMontoAntiguedad());

            int rta = stmt3.executeUpdate();
            if (rta == 0) {
                return null;
            }
            stmt3.close();
            conn.close();
            return "Recibo agregado";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
