/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Techshop.Kasandra.repository;
import Techshop.Kasandra.domain.Ruta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author HP
 */
public interface RutaRepository extends JpaRepository<Ruta, Integer> {
    List <Ruta> findAllByOrderByRequiereRolAsc();
}
