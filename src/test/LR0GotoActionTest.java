import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.parser.contextfreelanguage.LR0GotoAction;
import larp.parser.contextfreelanguage.LR0ShiftAction;

public class LR0GotoActionTest
{
    @Test
    public void testEqualsReturnsTrueForGotoActionWithSameStateIndex()
    {
        LR0GotoAction action = new LR0GotoAction(0);
        LR0GotoAction otherAction = new LR0GotoAction(0);

        assertTrue(action.equals(otherAction));
    }

    @Test
    public void testEqualsReturnsFalseForGotoActionWithDifferentStateIndex()
    {
        LR0GotoAction action = new LR0GotoAction(0);
        LR0GotoAction otherAction = new LR0GotoAction(1);

        assertFalse(action.equals(otherAction));
    }

    @Test
    public void testEqualsReturnsFalseForActionOfOtherType()
    {
        LR0GotoAction action = new LR0GotoAction(0);
        LR0ShiftAction otherAction = new LR0ShiftAction(1);

        assertFalse(action.equals(otherAction));
    }
}
