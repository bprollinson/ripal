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

        boolean epsilonToAccepting = this.epsilonToAccepting(epsilonNFAState);
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
                Vector<StateTransition> nextTransitions = transition.getNextState().getTransitions();
                for (int j = 0; j < nextTransitions.size(); j++)
                {
                    StateTransition nextTransition = nextTransitions.get(j);
                    if (nextTransition.getInput() != null)
                    {
                        startState.addTransition(new StateTransition(nextTransition.getInput(), nextTransition.getNextState()));
                    }
                }
            }
        }

        return startState;
    }

    private boolean epsilonToAccepting(State startState)
    {
        if (startState.isAccepting())
        {
            return true;
        }

        Vector<StateTransition> transitions = startState.getTransitions();
        for (int i = 0; i < transitions.size(); i++)
        {
            StateTransition transition = transitions.get(i);
            if (transition.getInput() == null && this.epsilonToAccepting(transition.getNextState()))
            {
                return true;
            }
        }

        return false;
    }
}
