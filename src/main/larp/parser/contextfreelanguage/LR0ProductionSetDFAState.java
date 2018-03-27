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
}
