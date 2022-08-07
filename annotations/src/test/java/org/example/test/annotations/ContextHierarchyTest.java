package org.example.test.annotations;

import org.example.jdbc.Employee;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextHierarchy({
        @ContextConfiguration(locations = "/test-config-parent.xml"),
        @ContextConfiguration(locations = "/test-config-child.xml")
})
public class ContextHierarchyTest {

    Logger logger = LoggerFactory.getLogger(ContextHierarchyTest.class);

    @Autowired
//    @Qualifier("employee1")
    private Employee employee;

    @Before
    public void setup() {
        logger.info("employee = {}", employee);
        System.out.println("employee = " + employee);
    }

    @Test
    public void test() {
        Assert.assertTrue(true);
    }
}
