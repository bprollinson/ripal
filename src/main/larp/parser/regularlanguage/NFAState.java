package larp.parser.regularlanguage;

public class NFAState extends State<Character>
{
    public NFAState(String name, boolean accepting)
    {
        super(name, accepting);
    }
}
