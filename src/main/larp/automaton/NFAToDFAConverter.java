package larp.automaton;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Vector;

public class NFAToDFAConverter
{
    public DFA convert(NFA nfa)
    {
        Vector<State> stateSet = new Vector<State>();
        stateSet.add(nfa.getStartState());
        return new DFA(this.convertNode(stateSet, new HashMap<Vector<State>, State>()));
    }

    private State convertNode(Vector<State> stateSet, HashMap<Vector<State>, State> coveredStateSetsToStates)
    {
        for (Map.Entry<Vector<State>, State> entry: coveredStateSetsToStates.entrySet())
        {
            HashSet<State> testStateSet = new HashSet<State>();
            testStateSet.addAll(stateSet);
            HashSet<State> testCoveredStateSet = new HashSet<State>();
            testCoveredStateSet.addAll(entry.getKey());
            if (testStateSet.equals(testCoveredStateSet))
            {
                return entry.getValue();
            }
        }

        State startState = new NFAState("", false);
        coveredStateSetsToStates.put(stateSet, startState);

        HashMap<Character, Vector<State>> characterToStateSet = new HashMap<Character, Vector<State>>();

        for (int i = 0; i < stateSet.size(); i++)
        {
            State NFAState = stateSet.get(i);
            Vector<StateTransition> transitions = NFAState.getTransitions();
            for (int j = 0; j < transitions.size(); j++)
            {
                StateTransition transition = transitions.get(j);
                Vector<State> characterStateSet = characterToStateSet.get(transition.getInput());
                if (characterStateSet == null)
                {
                     characterStateSet = new Vector<State>();
                }
                if (!characterStateSet.contains(transition.getNextState()))
                {
                    characterStateSet.add(transition.getNextState());
                }
                characterToStateSet.put(transition.getInput(), characterStateSet);
            }

            if (NFAState.isAccepting())
            {
                startState.setAccepting(true);
            }
        }

        for (Map.Entry<Character, Vector<State>> entry: characterToStateSet.entrySet())
        {
            startState.addTransition(new StateTransition(entry.getKey(), this.convertNode(entry.getValue(), coveredStateSetsToStates)));
        }

        return startState;
    }
}
