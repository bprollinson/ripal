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
        return new DFA(this.convertNode(stateSet));
    }

    private State convertNode(Vector<State> stateSet)
    {
        State startState = new NFAState("", false);

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
                characterStateSet.add(transition.getNextState());
                characterToStateSet.put(transition.getInput(), characterStateSet);
            }

            if (NFAState.isAccepting())
            {
                startState.setAccepting(true);
            }
        }

        for (Map.Entry<Character, Vector<State>> entry: characterToStateSet.entrySet())
        {
            startState.addTransition(new StateTransition(entry.getKey(), this.convertNode(entry.getValue())));
        }

        return startState;
    }
}
