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

    public boolean equals(Object other)
    {
        if (!(other instanceof State))
        {
            return false;
        }

        return this.accepting == ((State)other).isAccepting();
    }
}
