package larp.parser.regularlanguage;

import java.util.List;

public class EpsilonNFAState extends State<Character>
{
    public EpsilonNFAState(String name, boolean accepting)
    {
        super(name, accepting);
    }

    public List<StateTransition<Character>> getTransitions()
    {
        return this.transitions;
    }
}
