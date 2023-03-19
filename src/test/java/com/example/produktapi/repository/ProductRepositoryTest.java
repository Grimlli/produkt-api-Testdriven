package com.example.produktapi.repository;

import com.example.produktapi.exception.BadRequestException;
import com.example.produktapi.exception.EntityNotFoundException;
import com.example.produktapi.model.Product;
import com.example.produktapi.service.ProductService;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@DataJpaTest
@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService mockUnderTest;

    @Captor
    ArgumentCaptor<Product> productCaptor;

    @Autowired
    private ProductRepository underTest;


    @Test
    void simpleTest() {
        List<Product> products = underTest.findAll();
        Assertions.assertFalse(products.isEmpty());
    }

    // I think this is not what I am suppose to test
    @Test
    void whenSearchingForAnExistingTitle_thenReturnThatProduct() {
        //given
        String title = "En dator";

        Product product2 = new Product(2,
                title,
                25000.0,
                "Elekronik",
                "bra o ha",
                "urlTillBild");
        underTest.save(product2);
        Optional<Product> optionalProduct = underTest.findById(2);

        //then
        // Ett sätt att skriva 3 tester
        assertTrue(optionalProduct.isPresent());
        assertFalse(optionalProduct.isEmpty());
        assertEquals(title, optionalProduct.get().getTitle());


        // Ett annat sätt att skriva samma 3 tester
        Assertions.assertAll(
                () -> assertTrue(optionalProduct.isPresent()),
                () -> assertFalse(optionalProduct.isEmpty()),
                () -> assertEquals(title, optionalProduct.get().getTitle())
        );
    }


    @Test
    void whenSearchingForNonExistingTitle_thenReturnEmptyOptional(){
        // given
        String title = "En titel som absolut inte finns";
        // when
        Optional<Product> optionalProduct = underTest.findByTitle(title);

        // then
        Assertions.assertAll(
                ()-> assertFalse(optionalProduct.isPresent()),
                () -> assertTrue(optionalProduct.isEmpty()),
                () -> assertThrows(NoSuchElementException.class, ()-> optionalProduct.get())
        );
    }
/*
    @Test
    void updateProduct(){
        // given


        String title = "En dator";

        Product product = new Product(2,
                title,
                25000.0,
                "Elekronik",
                "bra o ha",
                "urlTillBild");
        Product productUpdate = new Product(2,
                title,
                23335.0,
                "Elekronik",
                "Dåligt att ha",
                "urlTillBild");
        mockUnderTest.addProduct(product);
        mockUnderTest.updateProduct(productUpdate,2);
        verify(repository).save(productCaptor.capture());
        Assertions.assertAll(
                ()-> assertTrue(productCaptor.isPresent()),
                () -> assertFalse(optionalProduct.isEmpty()),
                () -> assertNotEquals(productUpdate,optionalProduct),
                () -> assertThrows(NoSuchElementException.class, ()-> optionalProduct.get())
        );
    }
*/

    @Test
    void getProductByIDWhenNoProductExistWhithID(){
        Integer id = 1;
        //Product product = new Product(id,"Rätt objekt som sparas", 4000.0, "", "", "");
        //BDDMockito.given(repository.findById(id)).willReturn(Optional.of(product));
        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> mockUnderTest.getProductById(id));

        /*
        mockUnderTest.getProductById(id);
        verify(repository).findById(id);
         */
        Assertions.assertAll(
                ()-> Assertions.assertEquals("Produkt med id 1 hittades inte",thrown.getMessage())
               // ()-> verify(repository, never()).save(any())
        );
    }

    @Test
    void deleteProductByIDWhenNoProductExistWhithID(){
        Integer id = 1;
        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> mockUnderTest.deleteProduct(id));


        Assertions.assertAll(
                ()-> Assertions.assertEquals("Produkt med id 1 hittades inte",thrown.getMessage())
        );
    }

    @Test
    void uppdateProductByIDWhenNoProductExistWhithID(){
        Integer id = 1;
        Product productUpdate = new Product(2,
                "Fake Product",
                23335.0,
                "Elekronik",
                "Dåligt att ha",
                "urlTillBild");
        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> mockUnderTest.updateProduct(productUpdate,id));

        Assertions.assertAll(
                ()-> Assertions.assertEquals("Produkt med id 1 hittades inte",thrown.getMessage())
        );
    }

}