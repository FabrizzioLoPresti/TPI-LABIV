package tup.lab4.tpi.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tup.lab4.tpi.Models.Empleado;
import tup.lab4.tpi.Models.EmpleadoDTO;
import tup.lab4.tpi.Repositorios.RepositorioEmpleado;

import java.util.ArrayList;


@RestController
public class EmpleadoController {

    @Autowired
    private RepositorioEmpleado repositorioEmpleado;

    @GetMapping("/empleados")
    public ResponseEntity<ArrayList<EmpleadoDTO>> getEmpleados() {
        try {
            return ResponseEntity.ok(repositorioEmpleado.getEmpleados());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/empleados/{legajo}")
    public ResponseEntity<EmpleadoDTO> getEmpleado(@PathVariable int legajo) {
        try {
            EmpleadoDTO empleado = repositorioEmpleado.getEmpleadoDTO(legajo);
            return empleado != null ? ResponseEntity.ok(empleado) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/empleados")
    public ResponseEntity<String> addEmpleado(@RequestBody Empleado empleado) {
        try {
            String mensaje = repositorioEmpleado.addEmpleado(empleado);
            return empleado != null ? ResponseEntity.ok(mensaje) : ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/empleados/{legajo}")
    public ResponseEntity<Empleado> updateEmpleado(@PathVariable int legajo, @RequestBody Empleado empleado) {
        try {
            repositorioEmpleado.updateEmpleado(legajo, empleado);
            return empleado != null ? ResponseEntity.ok(empleado) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/empleados/{legajo}")
    public ResponseEntity<Empleado> deleteEmpleado(@PathVariable int legajo) {
        try {
            boolean res = repositorioEmpleado.deleteEmpleado(legajo);
            return res ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
