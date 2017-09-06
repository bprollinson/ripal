package larp.statemachine;

import java.util.Vector;

public class State
{
    private String name;
    private Vector<StateTransition> transitions;

    public State(String name)
    {
        this.name = name;
        this.transitions = new Vector<StateTransition>();
    }

    public void addTransition(StateTransition transition)
    {
        this.transitions.add(transition);
    }
}
