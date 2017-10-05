package larp.grammar.contextfreelanguage;

public class NonTerminalNode extends ContextFreeGrammarSyntaxNode
{
    private String name;

    public NonTerminalNode(String name)
    {
        this.name = name;
    }
}
