/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Techshop.Kasandra.service;
import Techshop.Kasandra.domain.Factura;
import Techshop.Kasandra.repository.FacturaRepository;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author HP
 */
@Service
public class FacturaService {
     private final FacturaRepository facturaRepository;
    
    public FacturaService(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    
    @Transactional(readOnly = true) // Solo lectura, mejor rendimiento
    public Factura getFacturaConVentas(Integer idFactura) {
        
        // 1. Llamar al método del repositorio que usa FETCH JOIN
        // El .orElseThrow lanza una excepción si el Optional está vacío
        return facturaRepository.findByIdFacturaConDetalle(idFactura)
                .orElseThrow(() -> new NoSuchElementException("Factura con ID " + idFactura + " no encontrada."));
        
        // El resultado es una Factura con todas las relaciones (usuario, ventas, producto) 
        // ya inicializadas, lista para ser usada en el Controller y la vista Thymeleaf.
    }
}
