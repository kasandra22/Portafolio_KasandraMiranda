/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Techshop.Kasandra.repository;
import Techshop.Kasandra.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author HP
 */
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    public List<Producto> findByActivoTrue();
}
