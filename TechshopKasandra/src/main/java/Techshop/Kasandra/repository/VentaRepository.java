/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Techshop.Kasandra.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import Techshop.Kasandra.domain.Venta;
/**
 *
 * @author HP
 */
public interface VentaRepository  extends JpaRepository<Venta, Integer>{
    
}
