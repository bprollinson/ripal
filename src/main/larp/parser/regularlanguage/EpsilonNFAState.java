package larp.parser.regularlanguage;

public class EpsilonNFAState extends State
{
    public EpsilonNFAState(String name, boolean accepting)
    {
        super(name, accepting);
    }

    public void addTransition(StateTransition<Character> transition)
    {
        this.transitions.add(transition);
    }

    public int countTransitions()
    {
        return this.transitions.size();
    }
}
