package larp.parser.contextfreelanguage;

import larp.parser.regularlanguage.State;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;

import java.util.List;
import java.util.Set;

public class LR0ProductionSetDFAState extends State<Character, LR0ProductionSetDFAState>
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

    protected StateComparator buildStateComparator()
    {
        return new LR0ProductionSetDFAStateComparator();
    }

    private class LR0ProductionSetDFAStateComparator extends StateComparator
    {
        public boolean equalsState(State state, State otherState, List<State> ourCoveredStates, List<State> otherCoveredStates)
        {
            if (!super.equalsState(state, otherState, ourCoveredStates, otherCoveredStates))
            {
                return false;
            }

            return ((LR0ProductionSetDFAState)state).getProductionSet().equals(((LR0ProductionSetDFAState)otherState).getProductionSet());
        }
    }
}
