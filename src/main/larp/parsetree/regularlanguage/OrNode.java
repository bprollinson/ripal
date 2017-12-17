package larp.parsetree.regularlanguage;

public class OrNode extends RegularExpressionSyntaxNode
{
    public void addChild(RegularExpressionSyntaxNode childNode)
    {
        this.childNodes.add(childNode);
    }
}
