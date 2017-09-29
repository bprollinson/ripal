package larp.grammar.contextfreelanguage;

public class NonTerminalToken extends ContextFreeGrammarSyntaxToken
{
    private String name;

    public NonTerminalToken(String name)
    {
        this.name = name;
    }
}
