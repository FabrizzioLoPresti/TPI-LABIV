package tup.lab4.tpi.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tup.lab4.tpi.Models.Empleado;
import tup.lab4.tpi.Models.FechaDTO;
import tup.lab4.tpi.Models.Reporte;
import tup.lab4.tpi.Repositorios.RepositorioEmpleado;
import tup.lab4.tpi.Repositorios.RepositorioReporte;

import java.util.ArrayList;

@RestController
public class ReporteController {

    @Autowired
    private RepositorioReporte repositorioReporte;

    @GetMapping("/reporte")
    public ResponseEntity<ArrayList<Reporte>> getReportes(@RequestBody FechaDTO fecha) {
        try {
            ArrayList<Reporte> reporte = repositorioReporte.getReporte(fecha);
            return reporte != null ? ResponseEntity.ok(reporte) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
