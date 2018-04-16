package larp.parser.regularlanguage;

import java.util.List;

public class DFAState extends State<Character>
{
    public DFAState(String name, boolean accepting)
    {
        super(name, accepting);
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
