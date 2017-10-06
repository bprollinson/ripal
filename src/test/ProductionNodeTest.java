import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ProductionNodeTest
{
    @Test
    public void testEqualsReturnsTrueForProductionNodeWithNoChildren()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testEqualsReturnsTrueForProductionNodeWithSameSubtree()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testEqualsReturnsFalseForProductionNodeWithDifferentSubtree()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testEqualsReturnsFalseForNodeOfOtherType()
    {
        assertEquals(0, 1);
    }
}
