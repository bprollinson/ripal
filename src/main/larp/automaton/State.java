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

    public Vector<StateTransition> getTransitions()
    {
        return this.transitions;
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof State))
        {
            return false;
        }

        State otherState = (State)other;

        if (this.accepting != otherState.isAccepting())
        {
            return false;
        }

        if (transitions.size() != otherState.countTransitions())
        {
            return false;
        }

        Vector<StateTransition> ourTransitions = (Vector<StateTransition>)this.transitions.clone();
        Vector<StateTransition> otherTransitions = (Vector<StateTransition>)otherState.getTransitions().clone();

        while (ourTransitions.size() > 0)
        {
            boolean found = false;

            StateTransition current = ourTransitions.get(0);

            for (int i = 0; i < otherTransitions.size(); i++)
            {
                if (current.getInput() == otherTransitions.get(i).getInput() && current.getNextState().equals(otherTransitions.get(i).getNextState()))                
                {
                    found = true;
                    ourTransitions.remove(0);
                    otherTransitions.remove(i);
                    break;
                }
            }

            if (!found)
            {
                return false;
            }
        }

        return true;
    }
}
