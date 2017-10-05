package larp.grammar.contextfreelanguage;

import java.util.Vector;

public class ContextFreeGrammarSyntaxNode
{
    protected Vector<ContextFreeGrammarSyntaxNode> childNodes;

    public ContextFreeGrammarSyntaxNode()
    {
        this.childNodes = new Vector<ContextFreeGrammarSyntaxNode>();
    }
}
