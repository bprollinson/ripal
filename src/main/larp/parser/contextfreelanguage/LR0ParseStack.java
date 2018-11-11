/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parser.contextfreelanguage;

import larp.automaton.State;

import java.util.EmptyStackException;
import java.util.Stack;

public class LR0ParseStack
{
    private Stack<Object> stack;

    public LR0ParseStack()
    {
        this.stack = new Stack<Object>();
    }

    public void push(Object stackEntry)
    {
        this.stack.push(stackEntry);
    }

    public Object peek() throws LR0ParseStackEmptyException
    {
        try
        {
            return this.stack.peek();
        }
        catch (EmptyStackException ese)
        {
            throw new LR0ParseStackEmptyException();
        }
    }

    public Object pop() throws LR0ParseStackEmptyException
    {
        try
        {
            return this.stack.pop();
        }
        catch (EmptyStackException ese)
        {
            throw new LR0ParseStackEmptyException();
        }
    }

    public State getTopState() throws LR0ParseStackEmptyException
    {
        for (int i = this.stack.size() - 1; i >= 0; i--)
        {
            Object stackEntry = this.stack.get(i);
            if (stackEntry instanceof State)
            {
                return (State)stackEntry;
            }
        }

        throw new LR0ParseStackEmptyException();
    }

    public boolean stackEquals(Stack<Object> otherStack)
    {
        return this.stack.equals(otherStack);
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof LR0ParseStack))
        {
            return false;
        }

        return ((LR0ParseStack)other).stackEquals(this.stack);
    }
}
