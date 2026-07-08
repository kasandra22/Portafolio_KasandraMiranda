package Techshop.Kasandra.service;

import Techshop.Kasandra.domain.Categoria;
import Techshop.Kasandra.repository.CategoriaRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CloudinaryStorageService cloudinaryStorageService;

    // Inyección por constructor
    public CategoriaService(CategoriaRepository categoriaRepository,
                             CloudinaryStorageService cloudinaryStorageService) {
        this.categoriaRepository = categoriaRepository;
        this.cloudinaryStorageService = cloudinaryStorageService;
    }

    @Transactional(readOnly = true)
    public List<Categoria> getCategorias(boolean activo) {
        if (activo) {
            return categoriaRepository.findByActivoTrue();
        }
        return categoriaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Categoria> getCategoria(Integer idCategoria) {
        return categoriaRepository.findById(idCategoria);
    }

    @Transactional
    public void save(Categoria categoria, MultipartFile imagenFile) {
        categoria = categoriaRepository.save(categoria);
        if (imagenFile != null && !imagenFile.isEmpty()) {
            try {
                // Se llama sobre la instancia inyectada, no sobre la clase
                String rutaImagen = cloudinaryStorageService.uploadImage(
                        imagenFile,
                        "categoria",
                        categoria.getIdCategoria()
                );
                categoria.setRutaImagen(rutaImagen);
                categoriaRepository.save(categoria);
            } catch (IOException e) {
                throw new RuntimeException("Error al subir la imagen.", e);
            }
        }
    }

    @Transactional
    public void delete(Integer idCategoria) {
        // Verifica si la categoría existe
        if (!categoriaRepository.existsById(idCategoria)) {
            throw new IllegalArgumentException(
                    "La categoría con ID " + idCategoria + " no existe."
            );
        }
        try {
            // Primero eliminamos la imagen en Cloudinary
            cloudinaryStorageService.deleteImage("categoria", idCategoria);
            // Luego eliminamos el registro en base de datos
            categoriaRepository.deleteById(idCategoria);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException(
                    "No se puede eliminar la categoría. Tiene datos asociados.",
                    e
            );
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar la imagen en Cloudinary.", e);
        }
    }
}