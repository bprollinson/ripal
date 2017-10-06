import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ConcatenationNodeTest
{
    @Test
    public void testEqualsReturnsTrueForConcatenationNodeWithNoChildren()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testEqualsReturnsTrueForConcatenationNodeWithSameSubtree()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testEqualsReturnsFalseForConcatenationNodeWithDifferentSubtree()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testEqualsReturnsFalseForNodeOfOtherType()
    {
        assertEquals(0, 1);
    }
}
