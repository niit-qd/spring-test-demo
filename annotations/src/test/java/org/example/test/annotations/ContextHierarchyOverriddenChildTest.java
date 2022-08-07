package org.example.test.annotations;

import org.example.jdbc.Employee;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextHierarchy({
        @ContextConfiguration(name = "parent", locations = "/test-config-parent.xml"),
        @ContextConfiguration(name = "child", locations = "/test-config-child.xml")
})
class ContextHierarchyOverriddenParent2Test {
}

@ContextHierarchy({
        @ContextConfiguration(name = "child", locations = "/test-config-child2.xml", inheritLocations = false)
})
public class ContextHierarchyOverriddenChildTest extends ContextHierarchyOverriddenParent2Test {

    Logger logger = LoggerFactory.getLogger(ContextHierarchyMergedChildTest.class);

    @Autowired
    @Qualifier("employee0")
    private Employee employee0;

    // 报异常，无法注入。
    @Autowired
    @Qualifier("employee1")
    private Employee employee1;

    @Autowired
    @Qualifier("employee2")
    private Employee employee2;

    @Before
    public void setUp() {
        logger.info("setUp: employ0 = {}", employee0);
        logger.info("setUp: employ1 = {}", employee1);
        logger.info("setUp: employ2 = {}", employee2);
        System.out.println(employee0);
        System.out.println(employee1);
        System.out.println(employee2);
    }

    @Test
    public void test1() {
        Assert.assertNotNull(employee0);
        Assert.assertNotNull(employee1);
        Assert.assertNotNull(employee2);
    }

}