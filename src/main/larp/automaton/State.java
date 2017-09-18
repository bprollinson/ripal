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
        Vector<State> ourCoveredStates = new Vector<State>();
        Vector<State> otherCoveredStates = new Vector<State>();

        return this.equalsState(otherState, ourCoveredStates, otherCoveredStates);
    }

    private boolean equalsState(State otherState, Vector<State> ourCoveredStates, Vector<State> otherCoveredStates)
    {
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
                StateTransition otherTransition = otherTransitions.get(i);
                Vector<State> ourNextCoveredStates = (Vector<State>)ourCoveredStates.clone();
                ourNextCoveredStates.add(this);
                Vector<State> otherNextCoveredStates = (Vector<State>)otherCoveredStates.clone();
                otherNextCoveredStates.add(otherState);
                boolean nextStatesLoop = this.coveredStatesContain(ourCoveredStates, current.getNextState()) && this.coveredStatesContain(otherCoveredStates, otherTransition.getNextState());
                boolean nextStatesEqual = false;
                if (!nextStatesLoop)
                {
                    nextStatesEqual = current.getNextState().equalsState(otherTransition.getNextState(), ourNextCoveredStates, otherNextCoveredStates);
                }
                boolean equal = nextStatesEqual || nextStatesLoop;

                if (current.getInput() == otherTransition.getInput() && equal)
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

    private boolean coveredStatesContain(Vector<State> coveredStates, State state)
    {
        for (int i = 0; i < coveredStates.size(); i++)
        {
            if (coveredStates.get(i) == state)
            {
                return true;
            }
        }

        return false;
    }
}
