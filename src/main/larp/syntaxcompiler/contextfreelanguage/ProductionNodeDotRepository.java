package larp.syntaxcompiler.contextfreelanguage;

import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.DotNode;

import java.util.List;

public class ProductionNodeDotRepository
{
    public ContextFreeGrammarSyntaxNode findProductionSymbolAfterDot(ContextFreeGrammarSyntaxNode productionNode)
    {
        List<ContextFreeGrammarSyntaxNode> childNodes = productionNode.getChildNodes().get(1).getChildNodes();

        boolean lastNodeWasDot = false;
        for (ContextFreeGrammarSyntaxNode childNode: childNodes)
        {
            if (lastNodeWasDot)
            {
                return childNode;
            }

            lastNodeWasDot = childNode instanceof DotNode;
        }

        return null;
    }
}
