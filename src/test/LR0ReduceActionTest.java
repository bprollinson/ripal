import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parser.contextfreelanguage.LR0ReduceAction;
import larp.parser.contextfreelanguage.LR0ShiftAction;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;

import java.util.HashSet;

public class LR0ReduceActionTest
{
    @Test
    public void testEqualsReturnsTrueForReduceActionWithSameProductionIndex()
    {
        LR0ReduceAction action = new LR0ReduceAction(0);
        LR0ReduceAction otherAction = new LR0ReduceAction(0);

        assertEquals(otherAction, action);
    }

    @Test
    public void testEqualsReturnsFalseForReduceActionWithDifferentProductionIndex()
    {
        LR0ReduceAction action = new LR0ReduceAction(0);
        LR0ReduceAction otherAction = new LR0ReduceAction(1);

        assertNotEquals(otherAction, action);
    }

    @Test
    public void testEqualsReturnsFalseForActionOfOtherType()
    {
        LR0ReduceAction action = new LR0ReduceAction(0);
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ShiftAction otherAction = new LR0ShiftAction(state);

        assertNotEquals(otherAction, action);
    }
}
