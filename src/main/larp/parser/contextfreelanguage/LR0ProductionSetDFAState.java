package larp.parser.contextfreelanguage;

import larp.parser.regularlanguage.State;
import larp.parser.regularlanguage.StateTransition;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LR0ProductionSetDFAState extends State<Character>
{
    protected List<StateTransition<Character>> transitions;
    private Set<ContextFreeGrammarSyntaxNode> productionSet;

    public LR0ProductionSetDFAState(String name, boolean accepting, Set<ContextFreeGrammarSyntaxNode> productionSet)
    {
        super(name, accepting);

        this.transitions = new ArrayList<StateTransition<Character>>();
        this.productionSet = productionSet;
    }

    public void addTransition(StateTransition<Character> transition)
    {
        this.transitions.add(transition);
    }

    public Set<ContextFreeGrammarSyntaxNode> getProductionSet()
    {
        return this.productionSet;
    }

    public int countTransitions()
    {
        return this.transitions.size();
    }

    public List<StateTransition<Character>> getTransitions()
    {
        return this.transitions;
    }

    protected boolean equalsState(State otherState, List<State> ourCoveredStates, List<State> otherCoveredStates)
    {
        if (!super.equalsState(otherState, ourCoveredStates, otherCoveredStates))
        {
            return false;
        }

        return this.productionSet.equals(((LR0ProductionSetDFAState)otherState).getProductionSet());
    }
}
