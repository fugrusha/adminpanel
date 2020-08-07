package com.golovko.adminpanel.service;

import com.golovko.adminpanel.domain.AbstractEntity;
import com.golovko.adminpanel.domain.Customer;
import com.golovko.adminpanel.dto.customer.CustomerPatchDTO;
import com.golovko.adminpanel.repository.RepositoryHelper;
import lombok.extern.slf4j.Slf4j;
import org.bitbucket.brunneng.ot.Configuration;
import org.bitbucket.brunneng.ot.ObjectTranslator;
import org.bitbucket.brunneng.ot.exceptions.TranslationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    private void configureForAbstractEntity(Configuration c) {
        c.beanOfClass(AbstractEntity.class).setIdentifierProperty("id");
        c.beanOfClass(AbstractEntity.class).setBeanFinder(
                (beanClass, id) -> repoHelper.getReferenceIfExist(beanClass, (UUID) id));
    }

    private void configureForCustomer(Configuration c) {
        c.beanOfClass(CustomerPatchDTO.class).translationTo(Customer.class).mapOnlyNotNullProperties();
    }

}
