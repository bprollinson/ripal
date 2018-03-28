import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class LR0ProductionSetDFAStateTest
{
    @Test
    public void testStructureEqualsReturnsTrueForSameProductionSet()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testStructureEqualsReturnsTrueForSameSubsequentProductionSet()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentProductionSet()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentSubsequentProductionSet()
    {
        assertEquals(0, 1);
    }
}
