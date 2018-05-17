package larp.parser.regularlanguage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NFAToDFAConverter
{
    public DFA convert(NFA nfa)
    {
        Set<NFAState> stateSet = new HashSet<NFAState>();
        stateSet.add(nfa.getStartState());
        return new DFA(this.convertNode(stateSet, new HashMap<Set<NFAState>, DFAState>()));
    }

    private DFAState convertNode(Set<NFAState> stateSet, HashMap<Set<NFAState>, DFAState> coveredStateSetsToStates)
    {
        if (coveredStateSetsToStates.keySet().contains(stateSet))
        {
            return coveredStateSetsToStates.get(stateSet);
        }

        DFAState startState = new DFAState("", false);
        coveredStateSetsToStates.put(stateSet, startState);

        Map<Character, Set<NFAState>> characterToStateSet = new HashMap<Character, Set<NFAState>>();

        for (NFAState NFAState: stateSet)
        {
            List<StateTransition<Character, NFAState>> transitions = NFAState.getTransitions();
            for (int j = 0; j < transitions.size(); j++)
            {
                StateTransition<Character, NFAState> transition = transitions.get(j);
                Set<NFAState> characterStateSet = characterToStateSet.get(transition.getInput());
                if (characterStateSet == null)
                {
                     characterStateSet = new HashSet<NFAState>();
                }
                characterStateSet.add(transition.getNextState());
                characterToStateSet.put(transition.getInput(), characterStateSet);
            }

            if (NFAState.isAccepting())
            {
                startState.setAccepting(true);
            }
        }

        for (Map.Entry<Character, Set<NFAState>> entry: characterToStateSet.entrySet())
        {
            startState.addTransition(new StateTransition<Character, DFAState>(entry.getKey(), this.convertNode(entry.getValue(), coveredStateSetsToStates)));
        }

        return startState;
    }
}
