package larp.parser.regularlanguage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class NFAToDFAConverter
{
    public DFA convert(NFA nfa)
    {
        HashSet<State> stateSet = new HashSet<State>();
        stateSet.add(nfa.getStartState());
        return new DFA(this.convertNode(stateSet, new HashMap<HashSet<State>, DFAState>()));
    }

    private DFAState convertNode(HashSet<State> stateSet, HashMap<HashSet<State>, DFAState> coveredStateSetsToStates)
    {
        if (coveredStateSetsToStates.keySet().contains(stateSet))
        {
            return coveredStateSetsToStates.get(stateSet);
        }

        DFAState startState = new DFAState("", false);
        coveredStateSetsToStates.put(stateSet, startState);

        HashMap<Character, HashSet<State>> characterToStateSet = new HashMap<Character, HashSet<State>>();

        for (State NFAState: stateSet)
        {
            List<StateTransition<Character, State>> transitions = NFAState.getTransitions();
            for (int j = 0; j < transitions.size(); j++)
            {
                StateTransition<Character, State> transition = transitions.get(j);
                HashSet<State> characterStateSet = characterToStateSet.get(transition.getInput());
                if (characterStateSet == null)
                {
                     characterStateSet = new HashSet<State>();
                }
                characterStateSet.add(transition.getNextState());
                characterToStateSet.put(transition.getInput(), characterStateSet);
            }

            if (NFAState.isAccepting())
            {
                startState.setAccepting(true);
            }
        }

        for (Map.Entry<Character, HashSet<State>> entry: characterToStateSet.entrySet())
        {
            startState.addTransition(new StateTransition<Character, DFAState>(entry.getKey(), this.convertNode(entry.getValue(), coveredStateSetsToStates)));
        }

        return startState;
    }
}
