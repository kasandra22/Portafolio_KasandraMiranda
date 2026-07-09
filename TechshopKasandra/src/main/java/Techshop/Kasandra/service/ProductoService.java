/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Techshop.Kasandra.service;
import Techshop.Kasandra.domain.Producto;
import Techshop.Kasandra.repository.ProductoRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author HP
 */
@Service
public class ProductoService {
   private final ProductoRepository productoRepository;
    private final CloudinaryStorageService cloudinaryStorageService;
    public ProductoService(ProductoRepository productoRepository,
                            CloudinaryStorageService cloudinaryStorageService) {
        this.productoRepository = productoRepository;
        this.cloudinaryStorageService = cloudinaryStorageService;
    }
    @Transactional(readOnly = true)
    public List<Producto> getProductos(boolean activo) {
        if (activo) {
            return productoRepository.findByActivoTrue();
        }
        return productoRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Optional<Producto> getProducto(Integer idProducto) {
        return productoRepository.findById(idProducto);
    }
    @Transactional
    public void save(Producto producto, MultipartFile imagenFile) {
        producto = productoRepository.save(producto);
        if (imagenFile != null && !imagenFile.isEmpty()) {
            try {
                String rutaImagen = cloudinaryStorageService.uploadImage(
                        imagenFile, "producto", producto.getIdProducto());
                producto.setRutaImagen(rutaImagen);
                productoRepository.save(producto);
            } catch (IOException e) {
                throw new RuntimeException("Error al subir la imagen a Cloudinary.", e);
            }
        }
    }
    @Transactional
    public void delete(Integer idProducto) {
        if (!productoRepository.existsById(idProducto)) {
            throw new IllegalArgumentException("El producto con ID " + idProducto + " no existe.");
        }
        try {
            cloudinaryStorageService.deleteImage("producto", idProducto);
            productoRepository.deleteById(idProducto);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar el producto. Tiene datos asociados.", e);
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar la imagen en Cloudinary.", e);
        }
    }
 
    //Ejemplo de método utilizando consultas derivadas
    @Transactional(readOnly = true)
    public List<Producto> consultaDerivada(double precioInf, double precioSup) {
        return productoRepository.findByPrecioBetweenOrderByPrecioAsc(precioInf, precioSup);
    }
 
    //Ejemplo de método utilizando consultas JPQL
    @Transactional(readOnly = true)
    public List<Producto> consultaJPQL(double precioInf, double precioSup) {
        return productoRepository.consultaJPQL(precioInf, precioSup);
    }
 
    //Ejemplo de método utilizando consultas SQL nativas
    @Transactional(readOnly = true)
    public List<Producto> consultaSQL(double precioInf, double precioSup) {
        return productoRepository.consultaSQL(precioInf, precioSup);
    }
}
