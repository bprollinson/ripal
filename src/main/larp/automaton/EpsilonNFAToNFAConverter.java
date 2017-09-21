package larp.automaton;

import java.util.Vector;

public class EpsilonNFAToNFAConverter
{
    public NFA convert(EpsilonNFA nfa)
    {
        State startState = null;

        Vector<State> pendingEpsilonNFAStates = new Vector<State>();
        pendingEpsilonNFAStates.add(nfa.getStartState());

        while (pendingEpsilonNFAStates.size() > 0)
        {
            State epsilonNFAState = pendingEpsilonNFAStates.get(0);
            startState = new NFAState("", epsilonNFAState.isAccepting());

            Vector<StateTransition> transitions = epsilonNFAState.getTransitions();
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

            pendingEpsilonNFAStates.remove(0);
        }

        return new NFA(startState);
    }
}
