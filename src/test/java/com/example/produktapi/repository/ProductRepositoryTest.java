package com.example.produktapi.repository;

import com.example.produktapi.exception.BadRequestException;
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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@DataJpaTest
class ProductRepositoryTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService underTest;

    @Captor
    ArgumentCaptor<Product> productCaptor;

    @Test
    void whenGetAllProducts_thenExactlyOneInteractionWithRepositoryMethodFindAll() {
        // when
        underTest.getAllProducts();
        //then
        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void whenGetAllCategories_thenExactlyOneInteractionWithRepositoryMethodGetByCategory() {
        // when
        underTest.getAllCategories();
        //then
        verify(repository, times(1)).findAllCategories();
        verifyNoMoreInteractions(repository);
    }

    @Test
    @Disabled
    void givenAnExistingCategory_whenGetProductsByCategory_thenReceivesANonEmptyList() {
        // Skippar för nu
    }

    @Test
    void whenAddingAProduct_thenSaveMethodShouldBeCalled() {
        // given
        Product product = new Product("Rätt objekt som sparas", 4000.0, "", "", "");
        // when
        underTest.addProduct(product);
        //then
        verify(repository).save(productCaptor.capture());
        assertEquals(product, productCaptor.getValue());
    }

    @Test
    void whenAddingProductWithDuplicateTitle_thenThrowError() {
        // given
        String title = "vår test-titel";
        Product product = new Product(title, 34.0, "", "", "");
        given(repository.findByTitle(title)).willReturn(Optional.of(product));

        //then
        BadRequestException exception = assertThrows(BadRequestException.class,
                //when
                () -> underTest.addProduct(product));
        verify(repository, times(1)).findByTitle(title);
        verify(repository, never()).save(any());
        assertEquals("En produkt med titeln: vår test-titel finns redan", exception.getMessage());
    }





    @Test
    void getAllProducts() {
        underTest.getAllProducts();
        verify(repository).findAll();
    }

    @Test
    void getAllCategories() {
        underTest.getAllCategories();
        verify(repository).findAllCategories();
    }

    @Test
    void getProductsByCategory() {
        underTest.getProductsByCategory("electronic");
        verify(repository,times(1)).findByCategory("electronic");
    }




    @Test
    void getProductById() {
        Integer id = 1;
        Product product = new Product(id,"Rätt objekt som sparas", 4000.0, "", "", "");

        given(repository.findById(id)).willReturn(Optional.of(product));
        underTest.getProductById(id);
        verify(repository).findById(id);
        //Assertions.assertEquals(product,productCaptor.getValue());

    }
    @Test
    void updateProductButNotReally() {
        Integer id = 1;
        Product product = new Product(id,"Rätt objekt som sparas", 4000.0, "", "", "");
        given(repository.findById(id)).willReturn(Optional.of(product));
        Product product1 = new Product("Ny text", 4050.0, "", "", "");
        underTest.updateProduct(product1,id);

        verify(repository).save(productCaptor.capture());
        assertEquals(product,productCaptor.getValue());
    }

    @Test
    void deleteProduct() {
        Integer id = 1;
        Product product = new Product(id,"Rätt objekt som sparas", 4000.0, "", "", "");
        given(repository.findById(id)).willReturn(Optional.of(product));
        underTest.deleteProduct(id);

        verify(repository).deleteById(id);
    }

}