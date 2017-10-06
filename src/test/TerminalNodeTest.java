import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TerminalNodeTest
{
    @Test
    public void testEqualsReturnsTrueForTerminalNodeWithSameValue()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testEqualsReturnsFalseForTerminalNodeWithDifferentValue()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testEqualsReturnsFalseForNodeOfOtherType()
    {
        assertEquals(0, 1);
    }
}
