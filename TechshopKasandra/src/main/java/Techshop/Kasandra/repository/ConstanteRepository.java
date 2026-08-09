package Techshop.Kasandra.repository;

import Techshop.Kasandra.domain.Constante;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConstanteRepository extends JpaRepository<Constante, Integer> {

    public Optional<Constante> findByAtributo(String atributo);

}