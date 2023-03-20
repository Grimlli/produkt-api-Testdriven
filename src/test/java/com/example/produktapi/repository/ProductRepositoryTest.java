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
import java.util.Random;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository underTest;


    @Test
    void whenSearchingForAnExistingTitle_thenReturnThatProduct() {
        //given
        String title = "En dator";

        Product product2 = new Product(2,
                title,
                25000.0,
                "Elekronik",
                "Det är en produkt",
                "BILD");
        underTest.save(product2);
        Optional<Product> optionalProduct = underTest.findByTitle(title);

        Assertions.assertAll(
                () -> assertTrue(optionalProduct.isPresent()),
                () -> assertFalse(optionalProduct.isEmpty()),
                () -> assertEquals(title, optionalProduct.get().getTitle())
        );
    }

    @Test
    void whenGivenNewCategoryFindProductWithCategory(){
        String category = "Elekronik";

        Product product2 = new Product(2,
                "Random",
                25000.0,
                category,
                "Det är en produkt",
                "BILD");
        underTest.save(product2);
        List<Product> optionalProduct = underTest.findByCategory(category);

        Assertions.assertAll(
                () -> assertFalse(optionalProduct.isEmpty()),
                () -> assertEquals(category, optionalProduct.get(0).getCategory())
        );
    }

    @Test
    void findAllCategory(){
        String[] category = {
                "electronics",
                "jewelery",
                "men's clothing",
                "women's clothing"
        };

        List<String> optionalProduct = underTest.findAllCategories();

        Assertions.assertAll(
                () -> assertFalse(optionalProduct.isEmpty()),
                ()-> assertEquals(4,optionalProduct.size()),
                () -> assertEquals(category[0], optionalProduct.get(0)),
                () -> assertEquals(category[1], optionalProduct.get(1)),
                () -> assertEquals(category[2], optionalProduct.get(2)),
                () -> assertEquals(category[3], optionalProduct.get(3))

        );
    }

/*
- findAllCategories
- findByCategory
- findByTitle

*/


    @Test
    void whenSearchingForNonExistingTitle_thenReturnEmptyOptional(){
        String title = "En titel som absolut inte finns";

        Optional<Product> optionalProduct = underTest.findByTitle(title);

        Assertions.assertAll(
                ()-> assertFalse(optionalProduct.isPresent()),
                () -> assertTrue(optionalProduct.isEmpty()),
                () -> assertThrows(NoSuchElementException.class, ()-> optionalProduct.get())
        );
    }
}