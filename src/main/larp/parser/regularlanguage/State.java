package larp.parser.regularlanguage;

import larp.ComparableStructure;

import java.util.ArrayList;
import java.util.List;

public abstract class State<I, S extends State> implements ComparableStructure
{
    private String name;
    private boolean accepting;
    protected List<StateTransition<I, S>> transitions;

    public State(String name, boolean accepting)
    {
        this.name = name;
        this.accepting = accepting;
        this.transitions = new ArrayList<StateTransition<I, S>>();
    }

    public void addTransition(StateTransition<I, S> transition)
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

    public List<StateTransition<I, S>> getTransitions()
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
        List<State> ourCoveredStates = new ArrayList<State>();
        List<State> otherCoveredStates = new ArrayList<State>();

        return this.equalsState(otherState, ourCoveredStates, otherCoveredStates);
    }

    protected boolean equalsState(State otherState, List<State> ourCoveredStates, List<State> otherCoveredStates)
    {
        return this.buildStateComparator().equalsState(this, otherState, ourCoveredStates, otherCoveredStates);
    }

    protected StateComparator buildStateComparator()
    {
        return new StateComparator();
    }

    protected class StateComparator
    {
        public boolean equalsState(State state, State otherState, List<State> ourCoveredStates, List<State> otherCoveredStates)
        {
            if (this.statesObviouslyNotEqual(state, otherState))
            {
                return false;
            }

            List<StateTransition> ourTransitions = new ArrayList<StateTransition>();
            for (Object rawStateTransition: state.getTransitions())
            {
                StateTransition stateTransition = (StateTransition)rawStateTransition;
                StateTransition<Object, State> testTransition = new StateTransition<Object, State>(stateTransition.getInput(), stateTransition.getNextState());
                ourTransitions.add(testTransition);
            }

            List<StateTransition> otherTransitions = new ArrayList<StateTransition>();
            for (Object rawStateTransition: otherState.getTransitions())
            {
                StateTransition stateTransition = (StateTransition)rawStateTransition;
                StateTransition<Object, State> testTransition = new StateTransition<Object, State>(stateTransition.getInput(), stateTransition.getNextState());
                otherTransitions.add(testTransition);
            }

            List<State> ourNextCoveredStates = new ArrayList<State>(ourCoveredStates);
            ourNextCoveredStates.add(state);
            List<State> otherNextCoveredStates = new ArrayList<State>(otherCoveredStates);
            otherNextCoveredStates.add(otherState);

            while (ourTransitions.size() > 0)
            {
                boolean found = false;

                StateTransition current = ourTransitions.get(0);

                for (int i = 0; i < otherTransitions.size(); i++)
                {
                    StateTransition otherTransition = otherTransitions.get(i);

                    if (current.inputEqualsOtherTransition(otherTransition))
                    {
                        int ourNextStatePosition = ourCoveredStates.indexOf(current.getNextState());
                        boolean ourNextStateLoops = ourNextStatePosition != -1;
                        int otherNextStatePosition = otherCoveredStates.indexOf(otherTransition.getNextState());
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
                            nextStatesEqual = this.equalsState(current.getNextState(), otherTransition.getNextState(), ourNextCoveredStates, otherNextCoveredStates);
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

            ourCoveredStates.add(state);
            otherCoveredStates.add(otherState);

            return true;
        }

        private boolean statesObviouslyNotEqual(State state, State otherState)
        {
            if (!otherState.getClass().equals(state.getClass()))
            {
                return true;
            }

            if (state.isAccepting() != otherState.isAccepting())
            {
                return true;
            }

            if (state.countTransitions() != otherState.countTransitions())
            {
                return true;
            }

            return false;
        }
    }
}
