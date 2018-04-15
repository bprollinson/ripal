import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class DFAStateTest
{
    @Test
    public void testGetNextStateReturnsNullWhenInputDoesNotMatchAnyTransitionInput()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testGetNextStateReturnsNextStateFromSingleMatchingTransition()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testGetNextSTateReturnsNextStateFromSubsequentMatchingTransition()
    {
        assertEquals(0, 1);
    }
}
