package com.golovko.adminpanel;

import com.golovko.adminpanel.util.TestObjectFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql(statements = {
//        "delete from order",
        "delete from product",
        "delete from app_user",
        "delete from category",
        "delete from customer"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public abstract class BaseTest {

    @Autowired
    protected TestObjectFactory testObjectFactory;
}
