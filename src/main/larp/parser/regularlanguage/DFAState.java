package larp.parser.regularlanguage;

public class DFAState extends State
{
    public DFAState(String name, boolean accepting)
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
