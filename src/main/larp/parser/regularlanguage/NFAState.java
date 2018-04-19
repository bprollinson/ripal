package larp.parser.regularlanguage;

public class NFAState extends State<Character, NFAState>
{
    public NFAState(String name, boolean accepting)
    {
        super(name, accepting);
    }
}
