package larp.parser.regularlanguage;

import java.util.ArrayList;
import java.util.List;

public class DFAState extends State
{
    protected List<StateTransition<Character>> transitions;

    public DFAState(String name, boolean accepting)
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

    public State getNextState(Character input)
    {
        for (int i = 0; i < this.transitions.size(); i++)
        {
            StateTransition transition = this.transitions.get(i);
            if (transition.inputEquals(input))
            {
                return transition.getNextState();
            }
        }

        return null;
    }
}
