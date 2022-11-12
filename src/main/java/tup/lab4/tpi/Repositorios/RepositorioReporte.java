package tup.lab4.tpi.Repositorios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.ApplicationScope;
import tup.lab4.tpi.Models.FechaDTO;
import tup.lab4.tpi.Models.Reporte;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

@Repository
@ApplicationScope
public class RepositorioReporte {

    @Autowired
    private DataSource dataSource;

    public RepositorioReporte() {
    }

    public ArrayList<Reporte> getReporte(FechaDTO fecha) {
        try {

            if(fecha.getAnio() == 0 || fecha.getMes() == 0){
                return null;
            }

            ArrayList<Reporte> reportes = new ArrayList<>();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select \n" +
                    "\t(SUM(r.sueldoBruto)+SUM(r.montoAntiguedad)-SUM(r.jubilacion)-SUM(r.obraSocial)-SUM(r.fondoAltaComplejidad)  ) as MONTO,\n" +
                    "\ta.nombre as AREA\n" +
                    "\tfrom recibosueldo r \n" +
                    "inner join empleado e on r.legajoEmpleado = e.legajo\n" +
                    "inner join area a on e.idArea = a.id \n" +
                    "where año = ? and mes = ?\n" +
                    "group by e.idArea \n" +
                    "order by 1 ASC");
            stmt.setInt(1, fecha.getAnio());
            stmt.setInt(2, fecha.getMes());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                float monto = rs.getFloat(1);
                String area = rs.getString(2);
                Reporte r = new Reporte(monto, area);
                reportes.add(r);
            }

            rs.close();
            stmt.close();
            conn.close();
            return reportes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
