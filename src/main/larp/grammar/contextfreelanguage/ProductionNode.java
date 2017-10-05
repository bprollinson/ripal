package larp.grammar.contextfreelanguage;

public class ProductionNode extends ContextFreeGrammarSyntaxNode
{
    public void addChild(ContextFreeGrammarSyntaxNode childNode)
    {
        this.childNodes.add(childNode);
    }
}
