import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

import larp.parser.contextfreelanguage.LR0ParseStack;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

import java.util.HashSet;

public class LR0ParseStackTest
{
    @Test
    public void testPeekReturnsTopObject()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseStack stack = new LR0ParseStack();
        stack.push(state);
        stack.push(new TerminalNode("a"));

        assertEquals(new TerminalNode("a"), stack.peek());
    }

    @Test
    public void testPeekThrowsExceptionWhenStackIsEmpty()
    {
        throw new RuntimeException();
    }

    @Test
    public void testPopReturnsAndRemovesTopObject()
    {
        throw new RuntimeException();
    }

    @Test
    public void testPopThrowsExceptionWhenStackIsEmpty()
    {
        throw new RuntimeException();
    }

    @Test
    public void testGetTopStateReturnsTopElement()
    {
        throw new RuntimeException();
    }

    @Test
    public void testGetTopStateReturnsLowerElement()
    {
        throw new RuntimeException();
    }

    @Test
    public void testGetTopStateThrowsExceptionWhenStateNotFound()
    {
        throw new RuntimeException();
    }

    @Test
    public void testEqualsReturnsTrueForStacksContainingSameObjects()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseStack stack = new LR0ParseStack();
        stack.push(state);
        stack.push(new TerminalNode("a"));

        LR0ParseStack otherStack = new LR0ParseStack();
        otherStack.push(state);
        otherStack.push(new TerminalNode("a"));

        assertEquals(otherStack, stack);
    }

    @Test
    public void testEqualsReturnsFalseForStacksContainingDifferentObjects()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseStack stack = new LR0ParseStack();
        stack.push(state);
        stack.push(new TerminalNode("a"));

        LR0ParseStack otherStack = new LR0ParseStack();
        otherStack.push(state);
        otherStack.push(new TerminalNode("b"));

        assertNotEquals(otherStack, stack);
    }
}
