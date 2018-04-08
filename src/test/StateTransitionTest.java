import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class StateTransitionTest
{
    @Test
    public void testInputEqualsReturnsTrueWhenInputMatchesExpectedInput()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testInputEqualsReturnsFalseWhenInputDoesNotMatchExpectedInput()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testInputEqualsReturnsTrueWhenEmptyInputMatchesExpectedEmptyInput()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testInputEqualsReturnsFalseWhenNonEmptyInputDoesNotMatchExpectedEmptyInput()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testInputEqualsReturnsFalseWhenEmptyInputDoesNotMatchExpectedNonEmptyInput()
    {
        assertEquals(0, 1);
    }
}
