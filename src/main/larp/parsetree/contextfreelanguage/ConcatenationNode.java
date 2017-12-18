package larp.parsetree.contextfreelanguage;

public class ConcatenationNode extends ContextFreeGrammarSyntaxNode
{
    public void addChild(ContextFreeGrammarSyntaxNode childNode)
    {
        this.childNodes.add(childNode);
    }
}
