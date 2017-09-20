package larp.automaton;

import java.util.Vector;

public class EpsilonNFAToNFAConverter
{
    public NFA convert(EpsilonNFA nfa)
    {
        State startState = new NFAState("", nfa.getStartState().isAccepting());

        Vector<StateTransition> transitions = nfa.getStartState().getTransitions();
        for (int i = 0; i < transitions.size(); i++)
        {
            StateTransition transition = transitions.get(i);
            if (transition.getInput() != null)
            {
                startState.addTransition(new StateTransition(transition.getInput(), new NFAState("", transition.getNextState().isAccepting())));
            }
            else if (transition.getNextState().isAccepting())
            {
                startState.setAccepting(true);
            }
        }

        return new NFA(startState);
    }
}
