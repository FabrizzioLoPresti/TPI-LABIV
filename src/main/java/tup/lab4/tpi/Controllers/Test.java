package tup.lab4.tpi.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tup.lab4.tpi.Models.Persona;
import tup.lab4.tpi.Repositorios.RepositorioPersonas;

import java.util.ArrayList;
import java.util.Random;

@RestController
public class Test {

    @Autowired
    private RepositorioPersonas repositorioPersonas;
    private ArrayList<Integer> list = new ArrayList<>();

    public Test() {
        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            list.add(r.nextInt(100));
        }
    }

    @GetMapping("/test")
    public String test(){
        return "Funcionando endpoint";
    }

    @GetMapping("/test/todos")
    public ArrayList<Integer> getList() {
        return list;
    }

    @GetMapping("/test/{id}")
    public Integer getNumber(@PathVariable Integer id) {
        return list.contains(id) ? id : null;
    }

    @PostMapping("/test/{id}")
    public void addNumber(@PathVariable Integer id) {
        list.add(id);
    }

    @DeleteMapping("/test/{id}")
    public void deleteNumber(@PathVariable Integer id) {
        list.remove(id);
    }

    @GetMapping("/test/personas")
    public ResponseEntity<ArrayList<Persona>> getPersonas() {
        return ResponseEntity.ok(repositorioPersonas.getPersonas());
    }

    @GetMapping("/test/personas/{documento}")
    public ResponseEntity<Persona> getPersona(@PathVariable int documento) {
        Persona persona = repositorioPersonas.getPersona(documento);
        return persona != null ? ResponseEntity.ok(persona) : ResponseEntity.notFound().build();
    }

    @PostMapping("/test/personas")
    public ResponseEntity<Persona> addPersona(@RequestBody Persona persona) {
        repositorioPersonas.addPersona(persona);
        return ResponseEntity.status(HttpStatus.CREATED).body(persona);
    }

    @DeleteMapping("/test/personas/{documento}")
    public ResponseEntity<Persona> deletePersona(@PathVariable int documento) {
        Persona persona = repositorioPersonas.getPersona(documento);
        if (persona != null) {
            repositorioPersonas.deletePersona(documento);
            return ResponseEntity.ok(persona);
        }
        return ResponseEntity.notFound().build();
    }
}