package larp.automaton;

import java.util.HashMap;
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
            if (this.stateSetsEqual(stateSet, coveredStateSets.get(i)))
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

    private boolean stateSetsEqual(Vector<State> stateSet1, Vector<State> stateSet2)
    {
        if (stateSet1.size() != stateSet2.size())
        {
            return false;
        }

        Vector<State> stateSet2Copy = (Vector<State>)stateSet2.clone();

        for (int i = 0; i < stateSet1.size(); i++)
        {
            boolean found = false;

            for (int j = 0; j < stateSet2Copy.size(); j++)
            {
                if (stateSet1.get(i) == stateSet2Copy.get(j))
                {
                    found = true;
                    stateSet2Copy.remove(j);
                    break;
                }
            }

            if (!found)
            {
                return false;
            }
        }

        return true;
    }
}
