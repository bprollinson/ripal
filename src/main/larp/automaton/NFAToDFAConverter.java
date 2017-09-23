package larp.automaton;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class NFAToDFAConverter
{
    public DFA convert(NFA nfa)
    {
        return new DFA(this.convertNode(nfa.getStartState()));
    }

    private State convertNode(State NFAState)
    {
        State startState = new NFAState("", false);

        HashMap<Character, Vector<State>> characterToStateSet = new HashMap<Character, Vector<State>>();

        Vector<StateTransition> transitions = NFAState.getTransitions();
        for (int i = 0; i < transitions.size(); i++)
        {
            StateTransition transition = transitions.get(i);
            Vector<State> characterStateSet = characterToStateSet.get(transition.getInput());
            if (characterStateSet == null)
            {
                 characterStateSet = new Vector<State>();
            }
            characterStateSet.add(transition.getNextState());
            characterToStateSet.put(transition.getInput(), characterStateSet);
        }

        for (Map.Entry<Character, Vector<State>> entry: characterToStateSet.entrySet())
        {
            startState.addTransition(new StateTransition(entry.getKey(), new NFAState("", false)));
        }

        return startState;
    }
}
