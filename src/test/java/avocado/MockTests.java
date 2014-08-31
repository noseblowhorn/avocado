package avocado;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MockTests {

    @Test
    public void testCreate() {
        TestClass proxy = Mock.create(TestClass.class);
        assertTrue(proxy instanceof TestClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateFromFinalClass() {
        FinalTestClass proxy = Mock.create(FinalTestClass.class);
    }

    @Test
    public void testGet() {
        TestClass proxy = Mock.create(TestClass.class);
        MockEnhancer enhancer = Mock.get(proxy);
        assertNotNull(enhancer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNotTracked() {
        Mock.get(new Object());
    }

    public final class FinalTestClass {

    }
}
