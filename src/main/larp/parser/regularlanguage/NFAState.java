package larp.parser.regularlanguage;

import java.util.List;

public class NFAState extends State<Character>
{
    public NFAState(String name, boolean accepting)
    {
        super(name, accepting);
    }

    public List<StateTransition<Character>> getTransitions()
    {
        return this.transitions;
    }
}
