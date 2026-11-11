
package com.reparaciones.sistema.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "seguimiento_reparacion")
public class SeguimientoReparacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seguimiento")
    private Long idSeguimiento;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 50, message = "El estado no puede superar 50 caracteres")
    @Column(name = "estado")
    private String estado;

    @NotNull(message = "La fecha y hora son obligatorias")
    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @Size(max = 500, message = "La observacion no puede superar 500 caracteres")
    @Column(name = "observacion")
    private String observacion;

    @NotNull(message = "Debe seleccionar una reparacion")
    @JsonBackReference("reparacion-seguimientos")
    @ManyToOne
    @JoinColumn(name = "id_reparacion")
    private Reparacion reparacion;

    public SeguimientoReparacion() {
    }

    public Long getIdSeguimiento() {
        return idSeguimiento;
    }

    public void setIdSeguimiento(Long idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Reparacion getReparacion() {
        return reparacion;
    }

    public void setReparacion(Reparacion reparacion) {
        this.reparacion = reparacion;
    }
}
