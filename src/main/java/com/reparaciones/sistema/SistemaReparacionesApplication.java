
package com.reparaciones.sistema;

import com.reparaciones.sistema.entity.Cliente;
import com.reparaciones.sistema.entity.Celular;
import com.reparaciones.sistema.entity.Tecnico;
import com.reparaciones.sistema.entity.Reparacion;
import com.reparaciones.sistema.entity.SeguimientoReparacion;
import com.reparaciones.sistema.entity.DetalleReparacion;
import com.reparaciones.sistema.entity.Repuesto;
import com.reparaciones.sistema.entity.Proveedor;
import com.reparaciones.sistema.entity.Pago;

import com.reparaciones.sistema.repository.ClienteRepository;
import com.reparaciones.sistema.repository.CelularRepository;
import com.reparaciones.sistema.repository.TecnicoRepository;
import com.reparaciones.sistema.repository.ReparacionRepository;
import com.reparaciones.sistema.repository.SeguimientoReparacionRepository;
import com.reparaciones.sistema.repository.DetalleReparacionRepository;
import com.reparaciones.sistema.repository.RepuestoRepository;
import com.reparaciones.sistema.repository.ProveedorRepository;
import com.reparaciones.sistema.repository.PagoRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class SistemaReparacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaReparacionesApplication.class, args);
	}

	@Bean
	CommandLineRunner demo(
			ClienteRepository clienteRepository,
			CelularRepository celularRepository,
			TecnicoRepository tecnicoRepository,
			ReparacionRepository reparacionRepository,
			SeguimientoReparacionRepository seguimientoRepository,
			DetalleReparacionRepository detalleRepository,
			RepuestoRepository repuestoRepository,
			ProveedorRepository proveedorRepository,
			PagoRepository pagoRepository) {

		return args -> {

			
			//  CLIENTES
			

			Cliente cliente1 = new Cliente();
			cliente1.setNombre("Duvan");
			cliente1.setApellido("Perez");
			cliente1.setTelefono("3001234567");
			cliente1.setDireccion("Cartagena");
			cliente1.setCorreo("duvan@gmail.com");
			cliente1.setFechaRegistro(LocalDate.now());

			clienteRepository.save(cliente1);

			Cliente cliente2 = new Cliente();
			cliente2.setNombre("Andres");
			cliente2.setApellido("Rodriguez");
			cliente2.setTelefono("3019876543");
			cliente2.setDireccion("Cartagena");
			cliente2.setCorreo("andres@gmail.com");
			cliente2.setFechaRegistro(LocalDate.now());

			clienteRepository.save(cliente2);


			
			//  TECNICOS
			

			Tecnico tecnico1 = new Tecnico();
			tecnico1.setNombre("Carlos");
			tecnico1.setApellido("Gomez");
			tecnico1.setTelefono("3105551234");
			tecnico1.setEspecialidad("Pantallas y componentes");

			tecnicoRepository.save(tecnico1);

			Tecnico tecnico2 = new Tecnico();
			tecnico2.setNombre("Miguel");
			tecnico2.setApellido("Torres");
			tecnico2.setTelefono("3155556789");
			tecnico2.setEspecialidad("Software y sistemas");

			tecnicoRepository.save(tecnico2);


			
			// PROVEEDORES
			

			Proveedor proveedor1 = new Proveedor();
			proveedor1.setNombre("TecnoRepuestos Cartagena");
			proveedor1.setTelefono("6055551111");
			proveedor1.setDireccion("Centro, Cartagena");
			proveedor1.setCorreo("ventas@tecnorepuestos.com");

			proveedorRepository.save(proveedor1);

			Proveedor proveedor2 = new Proveedor();
			proveedor2.setNombre("Mobile Parts Colombia");
			proveedor2.setTelefono("6015552222");
			proveedor2.setDireccion("Bogota");
			proveedor2.setCorreo("contacto@mobileparts.com");

			proveedorRepository.save(proveedor2);


			
			// REPUESTOS
			

			Repuesto repuesto1 = new Repuesto();
			repuesto1.setNombre("Pantalla Samsung S23");
			repuesto1.setDescripcion("Pantalla AMOLED de reemplazo");
			repuesto1.setPrecio(new BigDecimal("850000"));
			repuesto1.setStock(8);
			repuesto1.setProveedor(proveedor1);

			repuestoRepository.save(repuesto1);

			Repuesto repuesto2 = new Repuesto();
			repuesto2.setNombre("Bateria Samsung S23");
			repuesto2.setDescripcion("Bateria de reemplazo para Samsung S23");
			repuesto2.setPrecio(new BigDecimal("280000"));
			repuesto2.setStock(15);
			repuesto2.setProveedor(proveedor2);

			repuestoRepository.save(repuesto2);


			
			//  CELULARES
			

			Celular celular1 = new Celular();
			celular1.setMarca("Samsung");
			celular1.setModelo("Galaxy S23");
			celular1.setColor("Negro");
			celular1.setGmail("duvan@gmail.com");
			celular1.setCliente(cliente1);

			celularRepository.save(celular1);

			Celular celular2 = new Celular();
			celular2.setMarca("iPhone");
			celular2.setModelo("13");
			celular2.setColor("Azul");
			celular2.setGmail("andres@gmail.com");
			celular2.setCliente(cliente2);

			celularRepository.save(celular2);


			
			// REPARACIONES
			

			Reparacion reparacion1 = new Reparacion();
			reparacion1.setFechaIngreso(LocalDate.now());
			reparacion1.setFechaEstimada(LocalDate.now().plusDays(3));
			reparacion1.setFechaEntrega(null);
			reparacion1.setDiagnostico("Pantalla dañada");
			reparacion1.setProblemaReportado("La pantalla presenta grietas y no responde correctamente");
			reparacion1.setEstado("En reparacion");
			reparacion1.setTecnico(tecnico1);
			reparacion1.setCelular(celular1);

			reparacionRepository.save(reparacion1);

			Reparacion reparacion2 = new Reparacion();
			reparacion2.setFechaIngreso(LocalDate.now());
			reparacion2.setFechaEstimada(LocalDate.now().plusDays(2));
			reparacion2.setFechaEntrega(null);
			reparacion2.setDiagnostico("Problema de sistema");
			reparacion2.setProblemaReportado("El celular se reinicia constantemente");
			reparacion2.setEstado("En diagnostico");
			reparacion2.setTecnico(tecnico2);
			reparacion2.setCelular(celular2);

			reparacionRepository.save(reparacion2);


			
			//  SEGUIMIENTO DE REPARACIONES
			

			SeguimientoReparacion seguimiento1 = new SeguimientoReparacion();
			seguimiento1.setEstado("Recibido");
			seguimiento1.setFechaHora(LocalDateTime.now());
			seguimiento1.setObservacion("Celular recibido y registrado en el sistema");
			seguimiento1.setReparacion(reparacion1);

			seguimientoRepository.save(seguimiento1);

			SeguimientoReparacion seguimiento2 = new SeguimientoReparacion();
			seguimiento2.setEstado("Diagnostico realizado");
			seguimiento2.setFechaHora(LocalDateTime.now());
			seguimiento2.setObservacion("Se confirma daño en la pantalla");
			seguimiento2.setReparacion(reparacion1);

			seguimientoRepository.save(seguimiento2);

			SeguimientoReparacion seguimiento3 = new SeguimientoReparacion();
			seguimiento3.setEstado("En diagnostico");
			seguimiento3.setFechaHora(LocalDateTime.now());
			seguimiento3.setObservacion("Se revisa el sistema operativo del dispositivo");
			seguimiento3.setReparacion(reparacion2);

			seguimientoRepository.save(seguimiento3);


			
			//  DETALLES DE REPARACION
			

			DetalleReparacion detalle1 = new DetalleReparacion();
			detalle1.setCantidad(1);
			detalle1.setPrecioUnitario(new BigDecimal("850000"));
			detalle1.setSubTotal(new BigDecimal("850000"));
			detalle1.setReparacion(reparacion1);
			detalle1.setRepuesto(repuesto1);

			detalleRepository.save(detalle1);

			DetalleReparacion detalle2 = new DetalleReparacion();
			detalle2.setCantidad(1);
			detalle2.setPrecioUnitario(new BigDecimal("280000"));
			detalle2.setSubTotal(new BigDecimal("280000"));
			detalle2.setReparacion(reparacion1);
			detalle2.setRepuesto(repuesto2);

			detalleRepository.save(detalle2);


			
			//  PAGOS
			

			Pago pago1 = new Pago();
			pago1.setFecha(LocalDate.now());
			pago1.setMonto(new BigDecimal("500000"));
			pago1.setMetodoPago("Transferencia");
			pago1.setEstadoPago("Pagado");
			pago1.setReparacion(reparacion1);

			pagoRepository.save(pago1);

			Pago pago2 = new Pago();
			pago2.setFecha(LocalDate.now());
			pago2.setMonto(new BigDecimal("100000"));
			pago2.setMetodoPago("Efectivo");
			pago2.setEstadoPago("Pagado");
			pago2.setReparacion(reparacion2);

			pagoRepository.save(pago2);


			
			// MOSTRAR INFORMACION 
			

			System.out.println();
			System.out.println("==================================================");
			System.out.println("       SISTEMA DE REPARACION DE CELULARES");
			System.out.println("==================================================");


			System.out.println();
			System.out.println("========== CLIENTES ==========");

			clienteRepository.findAll().forEach(cliente -> {
				System.out.println(
					"ID: " + cliente.getIdCliente()
					+ " | Nombre: " + cliente.getNombre()
					+ " " + cliente.getApellido()
					+ " | Telefono: " + cliente.getTelefono()
					+ " | Correo: " + cliente.getCorreo()
					+ " | Fecha: " + cliente.getFechaRegistro()
				);
			});


			System.out.println();
			System.out.println("========== CELULARES ==========");

			celularRepository.findAll().forEach(celular -> {
				System.out.println(
					"ID: " + celular.getIdCelular()
					+ " | Marca: " + celular.getMarca()
					+ " | Modelo: " + celular.getModelo()
					+ " | Color: " + celular.getColor()
					+ " | Cliente: "
					+ celular.getCliente().getNombre()
					+ " " + celular.getCliente().getApellido()
				);
			});


			System.out.println();
			System.out.println("========== TECNICOS ==========");

			tecnicoRepository.findAll().forEach(tecnico -> {
				System.out.println(
					"ID: " + tecnico.getIdTecnico()
					+ " | Nombre: " + tecnico.getNombre()
					+ " " + tecnico.getApellido()
					+ " | Telefono: " + tecnico.getTelefono()
					+ " | Especialidad: " + tecnico.getEspecialidad()
				);
			});


			System.out.println();
			System.out.println("========== PROVEEDORES ==========");

			proveedorRepository.findAll().forEach(proveedor -> {
				System.out.println(
					"ID: " + proveedor.getIdProveedor()
					+ " | Nombre: " + proveedor.getNombre()
					+ " | Telefono: " + proveedor.getTelefono()
					+ " | Correo: " + proveedor.getCorreo()
				);
			});


			System.out.println();
			System.out.println("========== REPUESTOS ==========");

			repuestoRepository.findAll().forEach(repuesto -> {
				System.out.println(
					"ID: " + repuesto.getIdRepuesto()
					+ " | Nombre: " + repuesto.getNombre()
					+ " | Precio: $" + repuesto.getPrecio()
					+ " | Stock: " + repuesto.getStock()
					+ " | Proveedor: " + repuesto.getProveedor().getNombre()
				);
			});


			System.out.println();
			System.out.println("========== REPARACIONES ==========");

			reparacionRepository.findAll().forEach(reparacion -> {
				System.out.println(
					"ID: " + reparacion.getIdReparacion()
					+ " | Problema: " + reparacion.getProblemaReportado()
					+ " | Estado: " + reparacion.getEstado()
					+ " | Celular: "
					+ reparacion.getCelular().getMarca()
					+ " " + reparacion.getCelular().getModelo()
					+ " | Tecnico: "
					+ reparacion.getTecnico().getNombre()
					+ " " + reparacion.getTecnico().getApellido()
				);
			});


			System.out.println();
			System.out.println("========== SEGUIMIENTOS ==========");

			seguimientoRepository.findAll().forEach(seguimiento -> {
				System.out.println(
					"ID: " + seguimiento.getIdSeguimiento()
					+ " | Estado: " + seguimiento.getEstado()
					+ " | Observacion: " + seguimiento.getObservacion()
					+ " | Reparacion ID: "
					+ seguimiento.getReparacion().getIdReparacion()
				);
			});


			System.out.println();
			System.out.println("========== DETALLES DE REPARACION ==========");

			detalleRepository.findAll().forEach(detalle -> {
				System.out.println(
					"ID: " + detalle.getIdDetalle()
					+ " | Cantidad: " + detalle.getCantidad()
					+ " | Precio unitario: $" + detalle.getPrecioUnitario()
					+ " | Subtotal: $" + detalle.getSubTotal()
					+ " | Repuesto: " + detalle.getRepuesto().getNombre()
					+ " | Reparacion ID: "
					+ detalle.getReparacion().getIdReparacion()
				);
			});


			System.out.println();
			System.out.println("========== PAGOS ==========");

			pagoRepository.findAll().forEach(pago -> {
				System.out.println(
					"ID: " + pago.getIdPago()
					+ " | Fecha: " + pago.getFecha()
					+ " | Monto: $" + pago.getMonto()
					+ " | Metodo: " + pago.getMetodoPago()
					+ " | Estado: " + pago.getEstadoPago()
					+ " | Reparacion ID: "
					+ pago.getReparacion().getIdReparacion()
				);
			});


			System.out.println();
			System.out.println("==================================================");
			System.out.println("        DATOS GUARDADOS CORRECTAMENTE");
			System.out.println("==================================================");
		};
	}
}

