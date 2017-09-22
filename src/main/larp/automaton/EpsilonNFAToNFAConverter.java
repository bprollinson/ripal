package larp.automaton;

import java.util.Vector;

public class EpsilonNFAToNFAConverter
{
    public NFA convert(EpsilonNFA nfa)
    {
        return new NFA(this.convertNode(nfa.getStartState()));
    }

    private State convertNode(State epsilonNFAState)
    {
        State startState = new NFAState("", false);

        boolean epsilonToAccepting = this.epsilonToAccepting(epsilonNFAState, new Vector<State>());
        if (epsilonToAccepting)
        {
            startState.setAccepting(true);
        }

        Vector<StateTransition> transitions = epsilonNFAState.getTransitions();
        for (int i = 0; i < transitions.size(); i++)
        {
            StateTransition transition = transitions.get(i);
            if (transition.getInput() != null)
            {
                startState.addTransition(new StateTransition(transition.getInput(), this.convertNode(transition.getNextState())));
            }

            if (transition.getInput() == null)
            {
                Vector<StateTransition> tangibleStateTransitions = this.tangibleStateTransitions(transition);

                for (int j = 0; j < tangibleStateTransitions.size(); j++)
                {
                    StateTransition nextTransition = tangibleStateTransitions.get(j);
                    startState.addTransition(new StateTransition(nextTransition.getInput(), nextTransition.getNextState()));
                }
            }
        }

        return startState;
    }

    private boolean epsilonToAccepting(State startState, Vector<State> processedStates)
    {
        for (int i = 0; i < processedStates.size(); i++)
        {
            if (processedStates.get(i) == startState)
            {
                return false;
            }
        }

        processedStates.add(startState);

        if (startState.isAccepting())
        {
            return true;
        }

        Vector<StateTransition> transitions = startState.getTransitions();
        for (int i = 0; i < transitions.size(); i++)
        {
            StateTransition transition = transitions.get(i);
            if (transition.getInput() == null && this.epsilonToAccepting(transition.getNextState(), processedStates))
            {
                return true;
            }
        }

        return false;
    }

    private Vector<StateTransition> tangibleStateTransitions(StateTransition startTransition)
    {
        Vector<StateTransition> result = new Vector<StateTransition>();

        State nextState = startTransition.getNextState();
        Vector<StateTransition> nextStateTransitions = nextState.getTransitions();

        for (int i = 0; i < nextStateTransitions.size(); i++)
        {
            StateTransition nextTransition = nextStateTransitions.get(i);
            if (nextTransition.getInput() != null)
            {
                result.add(nextTransition);
            }
            else
            {
                Vector<StateTransition> subsequentTransitions = this.tangibleStateTransitions(nextTransition);
                for (int j = 0; j < subsequentTransitions.size(); j++)
                {
                    result.add(subsequentTransitions.get(j));
                }
            }
        }

        return result;
    }
}
