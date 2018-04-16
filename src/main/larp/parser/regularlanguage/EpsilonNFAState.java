package larp.parser.regularlanguage;

import java.util.List;

public class EpsilonNFAState extends State<Character>
{
    public EpsilonNFAState(String name, boolean accepting)
    {
        super(name, accepting);
    }

    public void addTransition(StateTransition<Character> transition)
    {
        this.transitions.add(transition);
    }

    public List<StateTransition<Character>> getTransitions()
    {
        return this.transitions;
    }
}
