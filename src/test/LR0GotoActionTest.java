import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

import larp.parser.contextfreelanguage.LR0GotoAction;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parser.contextfreelanguage.LR0ShiftAction;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;

import java.util.HashSet;

public class LR0GotoActionTest
{
    @Test
    public void testEqualsReturnsTrueForGotoActionWithSameState()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0GotoAction action = new LR0GotoAction(state);
        LR0GotoAction otherAction = new LR0GotoAction(state);

        assertEquals(otherAction, action);
    }

    @Test
    public void testEqualsReturnsFalseForGotoActionWithDifferentState()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0GotoAction action = new LR0GotoAction(state);
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0GotoAction otherAction = new LR0GotoAction(otherState);

        assertNotEquals(otherAction, action);
    }

    @Test
    public void testEqualsReturnsFalseForActionOfOtherType()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0GotoAction action = new LR0GotoAction(state);
        LR0ShiftAction otherAction = new LR0ShiftAction(otherState);

        assertNotEquals(otherAction, action);
    }
}
