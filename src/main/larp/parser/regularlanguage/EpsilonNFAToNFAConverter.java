package larp.parser.regularlanguage;

import java.util.ArrayList;
import java.util.List;

public class EpsilonNFAToNFAConverter
{
    public NFA convert(EpsilonNFA nfa)
    {
        return new NFA(this.convertNode(nfa.getStartState(), new ArrayList<State>(), new ArrayList<State>()));
    }

    private State convertNode(State epsilonNFAState, List<State> coveredEpsilonNFAStates, List<State> coveredNFAStates)
    {
        int firstIndexOfState = coveredEpsilonNFAStates.indexOf(epsilonNFAState);
        if (firstIndexOfState != -1)
        {
            return coveredNFAStates.get(firstIndexOfState);
        }

        State startState = new NFAState("", false);
        coveredEpsilonNFAStates.add(epsilonNFAState);
        coveredNFAStates.add(startState);

        boolean epsilonToAccepting = this.epsilonToAccepting(epsilonNFAState, new ArrayList<State>());
        if (epsilonToAccepting)
        {
            startState.setAccepting(true);
        }

        List<StateTransition<Character>> transitions = epsilonNFAState.getTransitions();
        for (int i = 0; i < transitions.size(); i++)
        {
            StateTransition<Character> transition = transitions.get(i);
            if (!transition.inputEquals(null))
            {
                startState.addTransition(new StateTransition<Character>(transition.getInput(), this.convertNode(transition.getNextState(), coveredEpsilonNFAStates, coveredNFAStates)));
            }

            if (transition.inputEquals(null))
            {
                List<StateTransition<Character>> tangibleStateTransitions = this.tangibleStateTransitions(transition, new ArrayList<StateTransition>());

                for (int j = 0; j < tangibleStateTransitions.size(); j++)
                {
                    StateTransition<Character> nextTransition = tangibleStateTransitions.get(j);
                    startState.addTransition(new StateTransition<Character>(nextTransition.getInput(), this.convertNode(nextTransition.getNextState(), coveredEpsilonNFAStates, coveredNFAStates)));
                }
            }
        }

        return startState;
    }

    private boolean epsilonToAccepting(State startState, List<State> processedStates)
    {
        if (processedStates.contains(startState))
        {
            return false;
        }
        processedStates.add(startState);

        if (startState.isAccepting())
        {
            return true;
        }

        List<StateTransition<Character>> transitions = startState.getTransitions();
        for (int i = 0; i < transitions.size(); i++)
        {
            StateTransition<Character> transition = transitions.get(i);
            if (transition.inputEquals(null) && this.epsilonToAccepting(transition.getNextState(), processedStates))
            {
                return true;
            }
        }

        return false;
    }

    private List<StateTransition<Character>> tangibleStateTransitions(StateTransition startTransition, List<StateTransition> processedTransitions)
    {
        List<StateTransition<Character>> result = new ArrayList<StateTransition<Character>>();
        if (processedTransitions.contains(startTransition))
        {
            return result;
        }
        processedTransitions.add(startTransition);

        State nextState = startTransition.getNextState();
        List<StateTransition<Character>> nextStateTransitions = nextState.getTransitions();

        for (int i = 0; i < nextStateTransitions.size(); i++)
        {
            StateTransition<Character> nextTransition = nextStateTransitions.get(i);
            if (!nextTransition.inputEquals(null))
            {
                result.add(nextTransition);
            }
            else
            {
                List<StateTransition<Character>> subsequentTransitions = this.tangibleStateTransitions(nextTransition, processedTransitions);
                result.addAll(subsequentTransitions);
            }
        }

        return result;
    }
}
