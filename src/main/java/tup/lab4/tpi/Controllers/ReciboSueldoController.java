package tup.lab4.tpi.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tup.lab4.tpi.Models.ReciboSueldo;
import tup.lab4.tpi.Models.ReciboSueldoDTO;
import tup.lab4.tpi.Repositorios.RepositorioReciboSueldo;

import java.util.ArrayList;

@RestController
public class ReciboSueldoController {

    @Autowired
    private RepositorioReciboSueldo repositorioReciboSueldo;

    @GetMapping("/recibos/{legajo}")
    public ResponseEntity<ArrayList<ReciboSueldoDTO>> getRecibos(@PathVariable int legajo) {
        try {
            return ResponseEntity.ok(repositorioReciboSueldo.getRecibosSueldo(legajo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/recibos")
    public ResponseEntity<String> addReciboSueldo(@RequestBody ReciboSueldoDTO reciboSueldo) {
        try {
            String mensaje = repositorioReciboSueldo.addReciboSueldo(reciboSueldo);
            return reciboSueldo != null ? ResponseEntity.ok(mensaje) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
