
package com.reparaciones.sistema.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long idPago;

    @NotNull(message = "La fecha del pago es obligatoria")
    @PastOrPresent(message = "La fecha del pago no puede ser futura")
    @Column(name = "fecha")
    private LocalDate fecha;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor que cero")
    @Digits(integer = 10, fraction = 2,
            message = "El monto debe tener máximo 10 enteros y 2 decimales")
    @Column(name = "monto", precision = 12, scale = 2)
    private BigDecimal monto;

    @NotBlank(message = "El método de pago es obligatorio")
    @Size(max = 50, message = "El método de pago no puede superar los 50 caracteres")
    @Column(name = "metodo_pago")
    private String metodoPago;

    @NotBlank(message = "El estado del pago es obligatorio")
    @Size(max = 30, message = "El estado no puede superar los 30 caracteres")
    @Column(name = "estado_pago")
    private String estadoPago;

    @NotNull(message = "La reparación asociada es obligatoria")
    @JsonBackReference("reparacion-pagos")
    @ManyToOne
    @JoinColumn(name = "id_reparacion")
    private Reparacion reparacion;

    public Pago() {
    }

    public Long getIdPago() {
        return idPago;
    }

    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public Reparacion getReparacion() {
        return reparacion;
    }

    public void setReparacion(Reparacion reparacion) {
        this.reparacion = reparacion;
    }
}
