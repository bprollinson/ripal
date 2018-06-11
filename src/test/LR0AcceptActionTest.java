import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.parser.contextfreelanguage.LR0AcceptAction;
import larp.parser.contextfreelanguage.LR0ShiftAction;

public class LR0AcceptActionTest
{
    @Test
    public void testEqualsReturnsTrueForActionOfSameType()
    {
        LR0AcceptAction action = new LR0AcceptAction();
        LR0AcceptAction otherAction = new LR0AcceptAction();

        assertTrue(action.equals(otherAction));
    }

    @Test
    public void testEqualsReturnsFalseForActionOfOtherType()
    {
        LR0AcceptAction action = new LR0AcceptAction();
        LR0ShiftAction otherAction = new LR0ShiftAction(0);

        assertFalse(action.equals(otherAction));
    }
}
