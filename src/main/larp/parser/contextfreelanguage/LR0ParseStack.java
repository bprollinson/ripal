package larp.parser.contextfreelanguage;

import larp.parser.regularlanguage.State;

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

    public State getTopState()
    {
        return null;
    }

    public Stack<Object> getStack()
    {
        return this.stack;
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof LR0ParseStack))
        {
            return false;
        }

        return this.stack.equals(((LR0ParseStack)other).getStack());
    }
}
