/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parser.contextfreelanguage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.parsetree.contextfreelanguage.Node;
import larp.parsetree.contextfreelanguage.NonTerminalNode;

import java.util.HashSet;
import java.util.Stack;

public class LR0ParseStackTest
{
    @Test
    public void testPeekReturnsTopObject() throws LR0ParseStackEmptyException
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<Node>());

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
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<Node>());

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
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<Node>());

        LR0ParseStack stack = new LR0ParseStack();
        stack.push(new LR0ProductionSetDFAState("", true, new HashSet<Node>()));
        stack.push(new NonTerminalNode("A"));
        stack.push(state);

        assertEquals(state, stack.getTopState());
    }

    @Test
    public void testGetTopStateReturnsLowerElement() throws LR0ParseStackEmptyException
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<Node>());

        LR0ParseStack stack = new LR0ParseStack();
        stack.push(new LR0ProductionSetDFAState("", true, new HashSet<Node>()));
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
    public void testStackEqualsReturnsTrue()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<Node>());

        LR0ParseStack stack = new LR0ParseStack();
        stack.push(state);
        stack.push(new NonTerminalNode("A"));

        Stack<Object> rawStack = new Stack<Object>();
        rawStack.push(state);
        rawStack.push(new NonTerminalNode("A"));

        assertTrue(stack.stackEquals(rawStack));
    }

    @Test
    public void testStackEqualsReturnsFalse()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<Node>());

        LR0ParseStack stack = new LR0ParseStack();
        stack.push(state);
        stack.push(new NonTerminalNode("A"));

        Stack<Object> rawStack = new Stack<Object>();
        rawStack.push(state);
        rawStack.push(new NonTerminalNode("B"));

        assertFalse(stack.stackEquals(rawStack));
    }

    @Test
    public void testEqualsReturnsTrueForStacksContainingSameObjects()
    {
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<Node>());

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
        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<Node>());

        LR0ParseStack stack = new LR0ParseStack();
        stack.push(state);
        stack.push(new NonTerminalNode("A"));

        LR0ParseStack otherStack = new LR0ParseStack();
        otherStack.push(state);
        otherStack.push(new NonTerminalNode("B"));

        assertNotEquals(otherStack, stack);
    }

    @Test
    public void testEqualsReturnsFalseForObjectWithDifferentClass()
    {
        LR0ParseStack stack = new LR0ParseStack();

        assertNotEquals(new Object(), stack);
    }
}
