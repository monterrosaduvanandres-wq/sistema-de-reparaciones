
package com.reparaciones.sistema.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "reparacion")
public class Reparacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reparacion")
    private Long idReparacion;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @Column(name = "fecha_estimada")
    private LocalDate fechaEstimada;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;

    @Size(max = 500, message = "El diagnostico no puede superar 500 caracteres")
    @Column(name = "diagnostico")
    private String diagnostico;

    @NotBlank(message = "El problema reportado es obligatorio")
    @Size(min = 5, max = 500, message = "El problema reportado debe tener entre 5 y 500 caracteres")
    @Column(name = "problema_reportado")
    private String problemaReportado;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 50, message = "El estado no puede superar 50 caracteres")
    @Column(name = "estado")
    private String estado;

    @NotNull(message = "Debe seleccionar un tecnico")
    @ManyToOne
    @JoinColumn(name = "id_tecnico")
    private Tecnico tecnico;

    @NotNull(message = "Debe seleccionar un celular")
    @JsonBackReference("celular-reparaciones")
    @ManyToOne
    @JoinColumn(name = "id_celular")
    private Celular celular;

    @JsonManagedReference("reparacion-seguimientos")
    @OneToMany(mappedBy = "reparacion")
    private List<SeguimientoReparacion> seguimientos;

    @JsonManagedReference("reparacion-detalles")
    @OneToMany(mappedBy = "reparacion")
    private List<DetalleReparacion> detalles;

    @JsonManagedReference("reparacion-pagos")
    @OneToMany(mappedBy = "reparacion")
    private List<Pago> pagos;

    public Reparacion() {
    }

    public Long getIdReparacion() {
        return idReparacion;
    }

    public void setIdReparacion(Long idReparacion) {
        this.idReparacion = idReparacion;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaEstimada() {
        return fechaEstimada;
    }

    public void setFechaEstimada(LocalDate fechaEstimada) {
        this.fechaEstimada = fechaEstimada;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getProblemaReportado() {
        return problemaReportado;
    }

    public void setProblemaReportado(String problemaReportado) {
        this.problemaReportado = problemaReportado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public Celular getCelular() {
        return celular;
    }

    public void setCelular(Celular celular) {
        this.celular = celular;
    }

    public List<SeguimientoReparacion> getSeguimientos() {
        return seguimientos;
    }

    public void setSeguimientos(List<SeguimientoReparacion> seguimientos) {
        this.seguimientos = seguimientos;
    }

    public List<DetalleReparacion> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleReparacion> detalles) {
        this.detalles = detalles;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }
}
