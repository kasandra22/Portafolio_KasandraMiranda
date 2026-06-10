/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Techshop.Kasandra.repository;

import Techshop.Kasandra.domain.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 
 *
 * @author HP
 */
@Repository
public interface CategoriaRepository extends JpaRepository  <Categoria, Integer>{
    public List  <Categoria> findByActivoTrue ();
}
