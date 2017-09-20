package larp.automaton;

public class EpsilonNFAToNFAConverter
{
    public NFA convert(EpsilonNFA nfa)
    {
        return new NFA(nfa.getStartState());
    }
}
