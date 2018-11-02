package larp.parser.contextfreelanguage;

import larp.parser.regularlanguage.State;

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

    public Object peek()
    {
        return this.stack.peek();
    }

    public Object pop()
    {
        return this.stack.pop();
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
