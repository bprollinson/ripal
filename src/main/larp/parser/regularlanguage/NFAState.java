package larp.parser.regularlanguage;

public class NFAState extends State
{
    public NFAState(String name, boolean accepting)
    {
        super(name, accepting);
    }

    public int countTransitions()
    {
        return this.transitions.size();
    }
}
