package larp.parser.regularlanguage;

import java.util.ArrayList;
import java.util.List;

public class NFAState extends State<Character>
{
    protected List<StateTransition<Character>> transitions;

    public NFAState(String name, boolean accepting)
    {
        super(name, accepting);

        this.transitions = new ArrayList<StateTransition<Character>>();
    }

    public void addTransition(StateTransition<Character> transition)
    {
        this.transitions.add(transition);
    }

    public int countTransitions()
    {
        return this.transitions.size();
    }

    public List<StateTransition<Character>> getTransitions()
    {
        return this.transitions;
    }
}
