package larp.automaton;

import java.util.Vector;

public class State
{
    private String name;
    private boolean accepting;
    private Vector<StateTransition> transitions;

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

    public State getNextState(Character input)
    {
        for (int i = 0; i < this.transitions.size(); i++)
        {
            StateTransition transition = this.transitions.get(i);
            if (input.equals(transition.getInput()))
            {
                return transition.getNextState();
            }
        }

        return null;
    }
}
