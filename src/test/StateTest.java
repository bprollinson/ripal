import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.automaton.State;

public class StateTest
{
    @Test
    public void testEqualsReturnsTrue()
    {
        State state = new State("S0", true);

        assertTrue(state.equals(new State("S1", true)));
    }

    @Test
    public void testEqualsReturnsFalse()
    {
        State state = new State("S0", true);

        assertFalse(state.equals(new State("S1", false)));
    }
}
