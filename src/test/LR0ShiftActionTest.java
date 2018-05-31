import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.parser.contextfreelanguage.LR0ReduceAction;
import larp.parser.contextfreelanguage.LR0ShiftAction;

public class LR0ShiftActionTest
{
    @Test
    public void testEqualsReturnsTrueForShiftActionWithSameStateIndex()
    {
        LR0ShiftAction action = new LR0ShiftAction(0);
        LR0ShiftAction otherAction = new LR0ShiftAction(0);

        assertTrue(action.equals(otherAction));
    }

    @Test
    public void testEqualsReturnsFalseForShiftActionWithDifferentStateIndex()
    {
        LR0ShiftAction action = new LR0ShiftAction(0);
        LR0ShiftAction otherAction = new LR0ShiftAction(1);

        assertFalse(action.equals(otherAction));
    }

    @Test
    public void testEqualsReturnsFalseForActionOfOtherType()
    {
        LR0ShiftAction action = new LR0ShiftAction(0);
        LR0ReduceAction otherAction = new LR0ReduceAction(0);

        assertFalse(action.equals(otherAction));
    }
}
