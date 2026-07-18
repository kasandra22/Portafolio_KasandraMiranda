/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Techshop.Kasandra.repository;
import Techshop.Kasandra.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
/**
 *
 * @author HP
 */
public interface UsuarioRepository extends  JpaRepository<Usuario, Integer> {
    Optional <Usuario> findByUsernameAndActivoTrue (String Username);
}
