package larp.grammar.contextfreelanguage;

public class ConcatenationNode extends ContextFreeGrammarSyntaxNode
{
    public void addChild(ContextFreeGrammarSyntaxNode childNode)
    {
        this.childNodes.add(childNode);
    }
}
