package larp.parser.contextfreelanguage;

import larp.assertion.Assertion;
import larp.parser.regularlanguage.State;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;

public class LR0ParseTableCellAvailableAssertion implements Assertion
{
    private LR0ParseTable parseTable;
    private State state;
    private ContextFreeGrammarSyntaxNode syntaxNode;
    private LR0ParseTableAction action;

    public LR0ParseTableCellAvailableAssertion(LR0ParseTable parseTable, State state, ContextFreeGrammarSyntaxNode syntaxNode, LR0ParseTableAction action)
    {
        this.parseTable = parseTable;
        this.state = state;
        this.syntaxNode = syntaxNode;
        this.action = action;
    }

    public void validate() throws AmbiguousLR0ParseTableException
    {
        if (this.action.isRowLevelAction() && this.parseTable.getRow(this.state) != null)
        {
            throw new AmbiguousLR0ParseTableException();
        }

        if (this.parseTable.getCell(this.state, this.syntaxNode) != null)
        {
            throw new AmbiguousLR0ParseTableException();
        }
    }
}
