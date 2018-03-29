package larp.parser.contextfreelanguage;

import larp.parser.regularlanguage.State;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;

import java.util.Set;

public class LR0ProductionSetDFAState extends State
{
    private Set<ContextFreeGrammarSyntaxNode> productionSet;

    public LR0ProductionSetDFAState(String name, boolean accepting, Set<ContextFreeGrammarSyntaxNode> productionSet)
    {
        super(name, accepting);

        this.productionSet = productionSet;
    }

    public Set<ContextFreeGrammarSyntaxNode> getProductionSet()
    {
        return this.productionSet;
    }

    public boolean structureEquals(Object other)
    {
        if (!super.structureEquals(other))
        {
            return false;
        }

        if (!(other instanceof LR0ProductionSetDFAState))
        {
            return false;
        }

        return this.productionSet.equals(((LR0ProductionSetDFAState)other).getProductionSet());
    }
}
