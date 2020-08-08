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
        "delete from app_user"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BaseTest {

    @Autowired
    protected TestObjectFactory testObjectFactory;
}
