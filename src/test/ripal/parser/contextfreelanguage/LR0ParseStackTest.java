/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parser.contextfreelanguage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import ripal.parsetree.contextfreelanguage.NonTerminalNode;

import java.util.Stack;

public class LR0ParseStackTest
{
    @Test
    public void testPeekReturnsTopObject() throws LR0ParseStackEmptyException
    {
        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseStack stack = new LR0ParseStack();
        stack.push(state);
        stack.push(new NonTerminalNode("A"));

        assertEquals(new NonTerminalNode("A"), stack.peek());
    }

    @Test
    public void testPeekThrowsExceptionWhenStackIsEmpty() throws LR0ParseStackEmptyException
    {
        LR0ParseStack stack = new LR0ParseStack();

        assertThrows(
            LR0ParseStackEmptyException.class,
            () -> {
                stack.peek();
            }
        );
    }

    @Test
    public void testPopReturnsAndRemovesTopObject() throws LR0ParseStackEmptyException
    {
        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseStack stack = new LR0ParseStack();
        stack.push(state);
        stack.push(new NonTerminalNode("A"));

        LR0ParseStack expectedStack = new LR0ParseStack();
        expectedStack.push(state);

        assertEquals(new NonTerminalNode("A"), stack.pop());
        assertEquals(expectedStack, stack);
    }

    @Test
    public void testPopThrowsExceptionWhenStackIsEmpty() throws LR0ParseStackEmptyException
    {
        LR0ParseStack stack = new LR0ParseStack();

        assertThrows(
            LR0ParseStackEmptyException.class,
            () -> {
                stack.pop();
            }
        );
    }

    @Test
    public void testGetTopStateReturnsTopElement() throws LR0ParseStackEmptyException
    {
        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseStack stack = new LR0ParseStack();
        stack.push(new LR0ClosureRuleSetDFAState("", true));
        stack.push(new NonTerminalNode("A"));
        stack.push(state);

        assertEquals(state, stack.getTopState());
    }

    @Test
    public void testGetTopStateReturnsLowerElement() throws LR0ParseStackEmptyException
    {
        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseStack stack = new LR0ParseStack();
        stack.push(new LR0ClosureRuleSetDFAState("", true));
        stack.push(new NonTerminalNode("A"));
        stack.push(state);
        stack.push(new NonTerminalNode("B"));

        assertEquals(state, stack.getTopState());
    }

    @Test
    public void testGetTopStateThrowsExceptionWhenStateNotFound() throws LR0ParseStackEmptyException
    {
        LR0ParseStack stack = new LR0ParseStack();

        assertThrows(
            LR0ParseStackEmptyException.class,
            () -> {
                stack.getTopState();
            }
        );
    }

    @Test
    public void testStackEqualsReturnsTrue()
    {
        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);

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
        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);

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
        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseStack stack = new LR0ParseStack();
        stack.push(state);
        stack.push(new NonTerminalNode("A"));

        LR0ParseStack otherStack = new LR0ParseStack();
        otherStack.push(state);
        otherStack.push(new NonTerminalNode("A"));

        assertTrue(stack.equals(otherStack));
    }

    @Test
    public void testEqualsReturnsFalseForStacksContainingDifferentObjects()
    {
        LR0ClosureRuleSetDFAState state = new LR0ClosureRuleSetDFAState("", false);

        LR0ParseStack stack = new LR0ParseStack();
        stack.push(state);
        stack.push(new NonTerminalNode("A"));

        LR0ParseStack otherStack = new LR0ParseStack();
        otherStack.push(state);
        otherStack.push(new NonTerminalNode("B"));

        assertFalse(stack.equals(otherStack));
    }

    @Test
    public void testEqualsReturnsFalseForObjectWithDifferentClass()
    {
        LR0ParseStack stack = new LR0ParseStack();

        assertFalse(stack.equals(new Object()));
    }
}
