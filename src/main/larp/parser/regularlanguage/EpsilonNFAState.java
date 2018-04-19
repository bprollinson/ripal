package larp.parser.regularlanguage;

public class EpsilonNFAState extends State<Character, EpsilonNFAState>
{
    public EpsilonNFAState(String name, boolean accepting)
    {
        super(name, accepting);
    }
}
