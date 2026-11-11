
package com.reparaciones.sistema.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "tecnico")
public class Tecnico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tecnico")
    private Long idTecnico;

    @NotBlank(message = "El nombre del técnico es obligatorio")
    @Size(min = 2, max = 60, message = "El nombre debe tener entre 2 y 60 caracteres")
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "El apellido del técnico es obligatorio")
    @Size(min = 2, max = 60, message = "El apellido debe tener entre 2 y 60 caracteres")
    @Column(name = "apellido")
    private String apellido;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(
        regexp = "^[0-9+()\\- ]{7,20}$",
        message = "El teléfono debe tener entre 7 y 20 caracteres y un formato válido"
    )
    @Column(name = "telefono")
    private String telefono;

    @NotBlank(message = "La especialidad del técnico es obligatoria")
    @Size(min = 3, max = 100, message = "La especialidad debe tener entre 3 y 100 caracteres")
    @Column(name = "especialidad")
    private String especialidad;

    @JsonIgnore
    @OneToMany(mappedBy = "tecnico")
    private List<Reparacion> reparaciones;

    public Tecnico() {
    }

    public Long getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(Long idTecnico) {
        this.idTecnico = idTecnico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public List<Reparacion> getReparaciones() {
        return reparaciones;
    }

    public void setReparaciones(List<Reparacion> reparaciones) {
        this.reparaciones = reparaciones;
    }
}
