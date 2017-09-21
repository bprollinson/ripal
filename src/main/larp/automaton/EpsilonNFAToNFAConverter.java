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
        State startState = new NFAState("", epsilonNFAState.isAccepting());

        Vector<StateTransition> transitions = epsilonNFAState.getTransitions();
        for (int i = 0; i < transitions.size(); i++)
        {
            StateTransition transition = transitions.get(i);
            if (transition.getInput() != null)
            {
                startState.addTransition(new StateTransition(transition.getInput(), this.convertNode(transition.getNextState())));
            }
            else if (transition.getNextState().isAccepting())
            {
                startState.setAccepting(true);
            }
        }

        return startState;
    }
}
