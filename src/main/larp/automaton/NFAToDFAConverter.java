package larp.automaton;

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

        Vector<StateTransition> transitions = NFAState.getTransitions();
        for (int i = 0; i < transitions.size(); i++)
        {
            StateTransition transition = transitions.get(i);
            if (transition.getInput() != null)
            {
                startState.addTransition(new StateTransition(transition.getInput(), transition.getNextState()));
            }
        }

        return startState;
    }
}
