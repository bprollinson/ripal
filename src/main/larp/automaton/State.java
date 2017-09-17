package larp.automaton;

import java.util.Vector;

public abstract class State
{
    private String name;
    private boolean accepting;
    protected Vector<StateTransition> transitions;

    public State(String name, boolean accepting)
    {
        this.name = name;
        this.accepting = accepting;
        this.transitions = new Vector<StateTransition>();
    }

    public void addTransition(StateTransition transition)
    {
        this.transitions.add(transition);
    }

    public boolean isAccepting()
    {
        return this.accepting;
    }

    public int countTransitions()
    {
        return this.transitions.size();
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof State))
        {
            return false;
        }

        if (this.accepting != ((State)other).isAccepting())
        {
            return false;
        }

        return this.transitions.size() == ((State)other).countTransitions();
    }
}
