package larp.parser.contextfreelanguage;

import larp.parser.regularlanguage.State;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;

import java.util.List;
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

    protected boolean equalsState(State otherState, List<State> ourCoveredStates, List<State> otherCoveredStates)
    {
        if (!(otherState instanceof LR0ProductionSetDFAState))
        {
            return false;
        }

        if (!super.equalsState(otherState, ourCoveredStates, otherCoveredStates))
        {
            return false;
        }

        return this.productionSet.equals(((LR0ProductionSetDFAState)otherState).getProductionSet());
    }
}
