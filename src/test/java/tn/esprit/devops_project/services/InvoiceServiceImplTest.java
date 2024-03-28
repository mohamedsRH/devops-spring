package tn.esprit.devops_project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.repositories.InvoiceDetailRepository;
import tn.esprit.devops_project.repositories.InvoiceRepository;
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.repositories.SupplierRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
class InvoiceServiceImplTest {
    @Mock
    InvoiceRepository invoiceRepository;
    @Mock
    OperatorRepository operatorRepository;
    @Mock
    InvoiceDetailRepository invoiceDetailRepository;
    @Mock
    SupplierRepository supplierRepository;

    @InjectMocks
    InvoiceServiceImpl invoiceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void retrieveAllInvoices() {
        List<Invoice> invoiceList = new ArrayList<> ();
        Mockito.when ( invoiceRepository.findAll ()).thenReturn(invoiceList);

        List<Invoice> result = invoiceService.retrieveAllInvoices();

        assertEquals(invoiceList, result);
    }

    @Test
    void cancelInvoice() {
        Long invoiceId = 1L;
        Invoice invoice = new Invoice();
        invoice.setArchived(false);
        Mockito.when(invoiceRepository.findById(invoiceId)).thenReturn( Optional.of(invoice));

        invoiceService.cancelInvoice(invoiceId);

        assertTrue(invoice.getArchived ());
        Mockito.verify(invoiceRepository, Mockito.times(1)).save(invoice);
    }

    @Test
    void retrieveInvoice() {
        Long invoiceId = 1L;
        Invoice invoice = new Invoice();
        Mockito.when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));

        Invoice result = invoiceService.retrieveInvoice(invoiceId);

        assertEquals(invoice, result);
    }

    @Test
    void getInvoicesBySupplier_SupplierNotFound_ThrowsException() {
        Long idSupplier = 1L;
        Mockito.when(supplierRepository.findById(idSupplier)).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () -> invoiceService.getInvoicesBySupplier(idSupplier));
    }

    @Test
    void assignOperatorToInvoice() {
        Long idOperator = 1L;
        Long idInvoice = 1L;
        Operator operator = new Operator();
        Invoice invoice = new Invoice();
        Mockito.when(operatorRepository.findById(idOperator)).thenReturn(Optional.of(operator));
        Mockito.when(invoiceRepository.findById(idInvoice)).thenReturn(Optional.of(invoice));

        invoiceService.assignOperatorToInvoice(idOperator, idInvoice);

        assertTrue(operator.getInvoices().contains(invoice));
        Mockito.verify(operatorRepository,  Mockito.times(1)).save(operator);
    }

    @Test
    void getTotalAmountInvoiceBetweenDates() {
        Date startDate = new Date();
        Date endDate = new Date();
        Mockito.when(invoiceRepository.getTotalAmountInvoiceBetweenDates(startDate, endDate)).thenReturn(100.0f);

        float result = invoiceService.getTotalAmountInvoiceBetweenDates(startDate, endDate);

        assertEquals(100.0f, result);
    }
}