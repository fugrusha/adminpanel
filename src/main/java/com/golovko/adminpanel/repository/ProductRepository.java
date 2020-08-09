package com.golovko.adminpanel.repository;

import com.golovko.adminpanel.domain.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends CrudRepository<Product, UUID> {

    @Query("select p from Product p where p.category.id = :categoryId and p.orderNumber = :orderNumber")
    Product findByCategoryIdAndOrderNumber(UUID categoryId, Integer orderNumber);

    Product findByIdAndCategoryId(UUID id, UUID categoryId);

    List<Product> findByCategoryId(UUID categoryId);
}
