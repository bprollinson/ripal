package larp.automaton;

public class NFAToDFAConverter
{
    public DFA convert(NFA nfa)
    {
        return new DFA(nfa.getStartState());
    }
}
