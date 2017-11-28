package larp.automaton;

import larp.ComparableStructure;

import java.util.List;
import java.util.Vector;

public abstract class State implements ComparableStructure
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

    public void setAccepting(boolean accepting)
    {
        this.accepting = accepting;
    }

    public int countTransitions()
    {
        return this.transitions.size();
    }

    public List<StateTransition> getTransitions()
    {
        return this.transitions;
    }

    public boolean structureEquals(Object other)
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
        Vector<StateTransition> otherTransitions = new Vector<StateTransition>(otherState.getTransitions());

        Vector<State> ourNextCoveredStates = (Vector<State>)ourCoveredStates.clone();
        ourNextCoveredStates.add(this);
        Vector<State> otherNextCoveredStates = (Vector<State>)otherCoveredStates.clone();
        otherNextCoveredStates.add(otherState);

        while (ourTransitions.size() > 0)
        {
            boolean found = false;

            StateTransition current = ourTransitions.get(0);

            for (int i = 0; i < otherTransitions.size(); i++)
            {
                StateTransition otherTransition = otherTransitions.get(i);

                if (current.getInput() == otherTransition.getInput())
                {
                    int ourNextStatePosition = this.coveredStatesPosition(ourCoveredStates, current.getNextState());
                    boolean ourNextStateLoops = ourNextStatePosition != -1;
                    int otherNextStatePosition = this.coveredStatesPosition(otherCoveredStates, otherTransition.getNextState());
                    boolean otherNextStateLoops = otherNextStatePosition != -1;
                    boolean nextStatesLoop = ourNextStateLoops && otherNextStateLoops;

                    if (ourNextStateLoops && !nextStatesLoop || otherNextStateLoops && !nextStatesLoop)
                    {
                        return false;
                    }

                    if (nextStatesLoop && ourNextStatePosition != otherNextStatePosition)
                    {
                        return false;
                    }

                    boolean nextStatesEqual = false;
                    if (!nextStatesLoop)
                    {
                        nextStatesEqual = current.getNextState().equalsState(otherTransition.getNextState(), ourNextCoveredStates, otherNextCoveredStates);
                    }
                    boolean equal = nextStatesEqual || nextStatesLoop;
                    if (nextStatesEqual)
                    {
                        for (int j = 0; j < ourNextCoveredStates.size(); j++)
                        {
                            ourCoveredStates.add(ourNextCoveredStates.get(j));
                            otherCoveredStates.add(otherNextCoveredStates.get(j));
                        }
                    }

                    if (equal)
                    {
                        found = true;
                        ourTransitions.remove(0);
                        otherTransitions.remove(i);
                        break;
                    }
                }
            }

            if (!found)
            {
                return false;
            }
        }

        ourCoveredStates.add(this);
        otherCoveredStates.add(otherState);

        return true;
    }

    private int coveredStatesPosition(Vector<State> coveredStates, State state)
    {
        for (int i = 0; i < coveredStates.size(); i++)
        {
            if (coveredStates.get(i) == state)
            {
                return i;
            }
        }

        return -1;
    }
}
