/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

import larp.parser.contextfreelanguage.LR0ParseStack;
import larp.parser.contextfreelanguage.LR0ParseStackEmptyException;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;

import java.util.HashSet;

public class LR0ParseStackTest
{
    @Test
    public void testPeekReturnsTopObject() throws LR0ParseStackEmptyException
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseStack stack = new LR0ParseStack();
        stack.push(state);
        stack.push(new NonTerminalNode("A"));

        assertEquals(new NonTerminalNode("A"), stack.peek());
    }

    @Test(expected = LR0ParseStackEmptyException.class)
    public void testPeekThrowsExceptionWhenStackIsEmpty() throws LR0ParseStackEmptyException
    {
        LR0ParseStack stack = new LR0ParseStack();
        stack.peek();
    }

    @Test
    public void testPopReturnsAndRemovesTopObject() throws LR0ParseStackEmptyException
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseStack stack = new LR0ParseStack();
        stack.push(state);
        stack.push(new NonTerminalNode("A"));

        LR0ParseStack expectedStack = new LR0ParseStack();
        expectedStack.push(state);

        assertEquals(new NonTerminalNode("A"), stack.pop());
        assertEquals(expectedStack, stack);
    }

    @Test(expected = LR0ParseStackEmptyException.class)
    public void testPopThrowsExceptionWhenStackIsEmpty() throws LR0ParseStackEmptyException
    {
        LR0ParseStack stack = new LR0ParseStack();
        stack.pop();
    }

    @Test
    public void testGetTopStateReturnsTopElement() throws LR0ParseStackEmptyException
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseStack stack = new LR0ParseStack();
        stack.push(new LR0ProductionSetDFAState("", true, new HashSet<ContextFreeGrammarSyntaxNode>()));
        stack.push(new NonTerminalNode("A"));
        stack.push(state);

        assertEquals(state, stack.getTopState());
    }

    @Test
    public void testGetTopStateReturnsLowerElement() throws LR0ParseStackEmptyException
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseStack stack = new LR0ParseStack();
        stack.push(new LR0ProductionSetDFAState("", true, new HashSet<ContextFreeGrammarSyntaxNode>()));
        stack.push(new NonTerminalNode("A"));
        stack.push(state);
        stack.push(new NonTerminalNode("B"));

        assertEquals(state, stack.getTopState());
    }

    @Test(expected = LR0ParseStackEmptyException.class)
    public void testGetTopStateThrowsExceptionWhenStateNotFound() throws LR0ParseStackEmptyException
    {
        LR0ParseStack stack = new LR0ParseStack();
        stack.getTopState();
    }

    @Test
    public void testEqualsReturnsTrueForStacksContainingSameObjects()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseStack stack = new LR0ParseStack();
        stack.push(state);
        stack.push(new NonTerminalNode("A"));

        LR0ParseStack otherStack = new LR0ParseStack();
        otherStack.push(state);
        otherStack.push(new NonTerminalNode("A"));

        assertEquals(otherStack, stack);
    }

    @Test
    public void testEqualsReturnsFalseForStacksContainingDifferentObjects()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseStack stack = new LR0ParseStack();
        stack.push(state);
        stack.push(new NonTerminalNode("A"));

        LR0ParseStack otherStack = new LR0ParseStack();
        otherStack.push(state);
        otherStack.push(new NonTerminalNode("B"));

        assertNotEquals(otherStack, stack);
    }
}
