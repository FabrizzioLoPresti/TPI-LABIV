package tup.lab4.tpi.Repositorios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.annotation.ApplicationScope;
import tup.lab4.tpi.Models.Area;
import tup.lab4.tpi.Models.Empleado;
import tup.lab4.tpi.Models.EmpleadoDTO;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

@Repository
@ApplicationScope
public class RepositorioEmpleado {

    @Autowired
    private DataSource dataSource;

    public RepositorioEmpleado() {
    }

    public ArrayList<EmpleadoDTO> getEmpleados() {
        try {
            ArrayList<EmpleadoDTO> emp = new ArrayList<>();
            Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM empleado");
            while (rs.next()) {
                int legajo = rs.getInt(1);
                String nombre = rs.getString(2);
                String apellido = rs.getString(3);
                Date fechaNacimiento = rs.getDate(4);
                Date fechaIngreso = rs.getDate(5);
                int idArea = rs.getInt(6);
                float sueldoBruto = rs.getFloat(7);

                Date fechaActual = new Date();
                int antiguedad = fechaActual.getYear() - fechaIngreso.getYear();

                // Consulta para obtener el nombre del area
                Statement stmt2 = conn.createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT nombre FROM area WHERE id = " + idArea);
                rs2.next();
                String nombreArea = rs2.getString(1);

                Area a = new Area(idArea, nombreArea);
                EmpleadoDTO eDTO = new EmpleadoDTO(legajo, nombre, apellido, fechaNacimiento, antiguedad, a.getNombre(), sueldoBruto);
                emp.add(eDTO);
            }
            rs.close();
            stmt.close();
            conn.close();
            return emp;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Empleado getEmpleado(int legajo) {
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM empleado WHERE legajo = ?");
            stmt.setInt(1, legajo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString(2);
                String apellido = rs.getString(3);
                Date fechaNacimiento = rs.getDate(4);
                Date fechaIngreso = rs.getDate(5);
                int idArea = rs.getInt(6);
                float sueldoBruto = rs.getFloat(7);
                Empleado empleado = new Empleado(legajo, nombre, apellido, fechaNacimiento, fechaIngreso, idArea, sueldoBruto);
                rs.close();
                stmt.close();
                conn.close();
                return empleado;
            }
            rs.close();
            stmt.close();
            conn.close();
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public EmpleadoDTO getEmpleadoDTO(int legajo) {
        try {
            EmpleadoDTO emp = null;
            Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM empleado WHERE legajo = " + legajo);
            if (rs.next()) {
                String nombre = rs.getString(2);
                String apellido = rs.getString(3);
                Date fechaNacimiento = rs.getDate(4);
                Date fechaIngreso = rs.getDate(5);
                int idArea = rs.getInt(6);
                float sueldoBruto = rs.getFloat(7);

                Date fechaActual = new Date();
                int antiguedad = fechaActual.getYear() - fechaIngreso.getYear();

                // Consulta para obtener el nombre del area
                Statement stmt2 = conn.createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT nombre FROM area WHERE id = " + idArea);
                rs2.next();
                String nombreArea = rs2.getString(1);

                Area a = new Area(idArea, nombreArea);
                emp = new EmpleadoDTO(legajo, nombre, apellido, fechaNacimiento, antiguedad, a.getNombre(), sueldoBruto);
            }
            rs.close();
            stmt.close();
            conn.close();
            return emp;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    public void setEmp(ArrayList<Empleado> emp) {
//        this.emp = emp;
//    }

    public String addEmpleado(Empleado empleado) {
        try {
            if (getEmpleado(empleado.getLegajo()) != null) {
                throw new RuntimeException("El empleado ya existe");
            }

            if (empleado.getLegajo() == 0 || empleado.getNombre().isEmpty() || empleado.getApellido().isEmpty() || empleado.getFechaNacimiento() == null || empleado.getFechaIngreso() == null || empleado.getIdArea() == 0 || empleado.getSueldoBruto() == 0) {
                throw new RuntimeException("Error: No se puede agregar un empleado con datos incompletos");
            }

            Connection con = dataSource.getConnection();
            PreparedStatement stmt = con.prepareStatement("INSERT INTO empleado VALUES (?,?,?,?,?,?,?)");
            stmt.setInt(1, empleado.getLegajo());
            stmt.setString(2, empleado.getNombre());
            stmt.setString(3, empleado.getApellido());
            stmt.setDate(4, new java.sql.Date(empleado.getFechaNacimiento().getTime()));
            stmt.setDate(5, new java.sql.Date(empleado.getFechaIngreso().getTime()));
            stmt.setInt(6, empleado.getIdArea());
            stmt.setFloat(7, empleado.getSueldoBruto());
            stmt.executeUpdate();
            stmt.close();
            con.close();
            return "Empleado agregado con exito";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Empleado updateEmpleado(@PathVariable int legajo, @RequestBody Empleado empleado) {
        try {
            if (getEmpleado(empleado.getLegajo()) == null) {
                return null;
            }

            if (empleado.getLegajo() == 0 || empleado.getNombre().isEmpty() || empleado.getApellido().isEmpty() || empleado.getFechaNacimiento() == null || empleado.getFechaIngreso() == null || empleado.getIdArea() == 0 || empleado.getSueldoBruto() == 0) {
                return null;
            }

            Connection con = dataSource.getConnection();
            PreparedStatement stmt = con.prepareStatement("UPDATE empleado SET nombre = ?, apellido = ?, fechaNacimiento = ?, fechaIngreso = ?, area = ?, idsueldoBruto = ? WHERE legajo = ?");
            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getApellido());
            stmt.setDate(3, new java.sql.Date(empleado.getFechaNacimiento().getTime()));
            stmt.setDate(4, new java.sql.Date(empleado.getFechaIngreso().getTime()));
            stmt.setInt(5, empleado.getIdArea());
            stmt.setFloat(6, empleado.getSueldoBruto());
            stmt.setInt(7, legajo);
            stmt.executeUpdate();
            stmt.close();
            con.close();
            return empleado;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteEmpleado(int legajo) {
        try {
            Connection con = dataSource.getConnection();
            PreparedStatement stmt = con.prepareStatement("DELETE FROM empleado WHERE legajo = ?");
            stmt.setInt(1, legajo);
            stmt.executeUpdate();
            stmt.close();
            con.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
