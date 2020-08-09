package com.golovko.adminpanel.service;

import com.golovko.adminpanel.domain.*;
import com.golovko.adminpanel.dto.category.CategoryPatchDTO;
import com.golovko.adminpanel.dto.customer.CustomerPatchDTO;
import com.golovko.adminpanel.dto.order.OrderPatchDTO;
import com.golovko.adminpanel.dto.product.ProductPatchDTO;
import com.golovko.adminpanel.dto.product.ProductReadDTO;
import com.golovko.adminpanel.dto.user.AppUserPatchDTO;
import com.golovko.adminpanel.repository.RepositoryHelper;
import lombok.extern.slf4j.Slf4j;
import org.bitbucket.brunneng.ot.Configuration;
import org.bitbucket.brunneng.ot.ObjectTranslator;
import org.bitbucket.brunneng.ot.exceptions.TranslationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TranslationService {

    @Autowired
    private RepositoryHelper repoHelper;

    private final ObjectTranslator objectTranslator;

    public TranslationService() {
        objectTranslator = new ObjectTranslator(createConfig());
    }

    private Configuration createConfig() {
        Configuration c = new Configuration();

        configureForAbstractEntity(c);
        configureForCustomer(c);
        configureForCategory(c);
        configureForProduct(c);
        configureForOrder(c);
        configureForAppUser(c);

        return c;
    }

    public <T> T translate(Object srcObject, Class<T> targetClass) {
        try {
            return objectTranslator.translate(srcObject, targetClass);
        } catch (TranslationException e) {
            log.warn(e.getMessage());
            throw (RuntimeException) e.getCause();
        }
    }

    public <T> void map(Object srcObject, Object destObject) {
        try {
            objectTranslator.mapBean(srcObject, destObject);
        } catch (TranslationException e) {
            log.warn(e.getMessage());
            throw (RuntimeException) e.getCause();
        }
    }

    public <T> List<T> translateList(List<?> objects, Class<T> targetClass) {
        return objects.stream().map(o -> translate(o, targetClass)).collect(Collectors.toList());
    }

    private void configureForAbstractEntity(Configuration c) {
        c.beanOfClass(AbstractEntity.class).setIdentifierProperty("id");
        c.beanOfClass(AbstractEntity.class).setBeanFinder(
                (beanClass, id) -> repoHelper.getReferenceIfExist(beanClass, (UUID) id));
    }

    private void configureForCustomer(Configuration c) {
        c.beanOfClass(CustomerPatchDTO.class).translationTo(Customer.class).mapOnlyNotNullProperties();
    }

    private void configureForOrder(Configuration c) {
        c.beanOfClass(OrderPatchDTO.class).translationTo(OrderCart.class).mapOnlyNotNullProperties();
    }

    private void configureForCategory(Configuration c) {
        c.beanOfClass(CategoryPatchDTO.class).translationTo(Category.class).mapOnlyNotNullProperties();
    }

    private void configureForProduct(Configuration c) {
        Configuration.Translation t = c.beanOfClass(Product.class).translationTo(ProductReadDTO.class);
        t.srcProperty("category.id").translatesTo("categoryId");

        c.beanOfClass(ProductPatchDTO.class).translationTo(Product.class).mapOnlyNotNullProperties();
    }

    private void configureForAppUser(Configuration c) {
        c.beanOfClass(AppUserPatchDTO.class).translationTo(AppUser.class).mapOnlyNotNullProperties();
    }
}
