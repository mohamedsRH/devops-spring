package tn.esprit.devops_project.services;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.repositories.SupplierRepository;
import tn.esprit.devops_project.services.SupplierServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class SupplierServiceImplTest {

    @Autowired
    SupplierRepository supplierRepository;

    SupplierServiceImpl supplierService;

    @BeforeEach
    void setUp() {
        supplierService = new SupplierServiceImpl(supplierRepository);
    }

    @AfterEach
    void tearDown() {
        supplierRepository.deleteAll();
    }

    @Test
    void addSupplier() {
        Supplier supplier = new Supplier();
        supplier.setLabel("Test Supplier");

        Supplier savedSupplier = supplierService.addSupplier(supplier);
        Supplier retrievedSupplier = supplierRepository.findById(savedSupplier.getIdSupplier()).orElse(null);

        assertEquals(supplier, retrievedSupplier);
    }

    @Test
    void retrieveSupplier() {
        Supplier supplier = new Supplier();
        supplier.setLabel("Test Supplier");
        Supplier savedSupplier = supplierRepository.save(supplier);

        Supplier retrievedSupplier = supplierService.retrieveSupplier(savedSupplier.getIdSupplier());

        assertEquals(supplier, retrievedSupplier);
    }

    @Test
    void retrieveAllSuppliers() {
        Supplier supplier1 = new Supplier();
        supplier1.setLabel("Supplier 1");
        Supplier supplier2 = new Supplier();
        supplier2.setLabel("Supplier 2");
        supplierRepository.save(supplier1);
        supplierRepository.save(supplier2);

        List<Supplier> suppliers = supplierService.retrieveAllSuppliers();

        assertEquals(2, suppliers.size());
    }

    @Test
    void updateSupplier() {
        Supplier supplier = new Supplier();
        supplier.setLabel("Supplier");
        supplier = supplierRepository.save(supplier);

        supplier.setLabel("Updated Supplier");
        supplierService.updateSupplier(supplier);

        Supplier updatedSupplier = supplierRepository.findById(supplier.getIdSupplier()).orElse(null);
        assertEquals("Updated Supplier", updatedSupplier.getLabel());
    }

    @Test
    void deleteSupplier() {
        Supplier supplier = new Supplier();
        supplier.setLabel("Supplier");
        supplier = supplierRepository.save(supplier);
        Long supplierId = supplier.getIdSupplier();

        supplierService.deleteSupplier(supplierId);

        assertEquals(0, supplierRepository.count());
    }
}
