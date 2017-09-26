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
        return new DFA(this.convertNode(stateSet, new Vector<Vector<State>>(), new Vector<State>()));
    }

    private State convertNode(Vector<State> stateSet, Vector<Vector<State>> coveredStateSets, Vector<State> coveredStates)
    {
        for (int i = 0; i < coveredStateSets.size(); i++)
        {
            HashSet<State> testStateSet = new HashSet<State>();
            testStateSet.addAll(stateSet);
            HashSet<State> testCoveredStateSet = new HashSet<State>();
            testCoveredStateSet.addAll(coveredStateSets.get(i));
            if (testStateSet.equals(testCoveredStateSet))
            {
                return coveredStates.get(i);
            }
        }

        State startState = new NFAState("", false);
        coveredStateSets.add(stateSet);
        coveredStates.add(startState);

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
            startState.addTransition(new StateTransition(entry.getKey(), this.convertNode(entry.getValue(), coveredStateSets, coveredStates)));
        }

        return startState;
    }
}
