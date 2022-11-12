package tup.lab4.tpi.Repositorios;

import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.ApplicationScope;
import tup.lab4.tpi.Models.Persona;

import java.util.ArrayList;

@Repository
@ApplicationScope
public class RepositorioPersonas {

    private ArrayList<Persona> personas = new ArrayList<>();

    public RepositorioPersonas() {
        personas.add(new Persona(12345678, "Juan Perez", 25));
        personas.add(new Persona(87654321, "Maria Lopez", 30));
    }

    public ArrayList<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(ArrayList<Persona> personas) {
        this.personas = personas;
    }

    public void addPersona(Persona persona) {
        personas.add(persona);
    }

    public void deletePersona(int documento) {
        personas.removeIf(p -> p.getDocumento() == documento);
    }

    public Persona getPersona(int documento) {
        return personas.stream().filter(p -> p.getDocumento() == documento).findFirst().orElse(null);
    }

    public void updatePersona(Persona persona) {
        deletePersona(persona.getDocumento());
        addPersona(persona);
    }

}
