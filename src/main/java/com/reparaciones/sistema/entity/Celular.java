package com.reparaciones.sistema.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "celular")
public class Celular {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_celular")
    private Long idCelular;

    @NotBlank(message = "La marca del celular es obligatoria")
    @Size(max = 50, message = "La marca no puede superar los 50 caracteres")
    @Column(name = "marca")
    private String marca;

    @NotBlank(message = "El modelo del celular es obligatorio")
    @Size(max = 60, message = "El modelo no puede superar los 60 caracteres")
    @Column(name = "modelo")
    private String modelo;

    @NotBlank(message = "El color del celular es obligatorio")
    @Size(max = 30, message = "El color no puede superar los 30 caracteres")
    @Column(name = "color")
    private String color;

    @Email(message = "El correo electrónico no tiene un formato válido")
    @Size(max = 120, message = "El correo no puede superar los 120 caracteres")
    @Column(name = "gmail")
    private String gmail;

    @NotNull(message = "El celular debe estar asociado a un cliente")
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @JsonManagedReference("celular-reparaciones")
    @OneToMany(mappedBy = "celular")
    private List<Reparacion> reparaciones;

    public Celular() {
    }

    public Long getIdCelular() {
        return idCelular;
    }

    public void setIdCelular(Long idCelular) {
        this.idCelular = idCelular;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Reparacion> getReparaciones() {
        return reparaciones;
    }

    public void setReparaciones(List<Reparacion> reparaciones) {
        this.reparaciones = reparaciones;
    }
}