package larp.parser.contextfreelanguage;

import larp.assertion.Assertion;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;

public class LL1ParseTableCellAvailableAssertion implements Assertion
{
    private LL1ParseTable parseTable;
    private NonTerminalNode nonTerminalNode;
    private ContextFreeGrammarSyntaxNode terminalNode;

    public LL1ParseTableCellAvailableAssertion(LL1ParseTable parseTable, NonTerminalNode nonTerminalNode, ContextFreeGrammarSyntaxNode terminalNode)
    {
        this.parseTable = parseTable;
        this.nonTerminalNode = nonTerminalNode;
        this.terminalNode = terminalNode;
    }

    public void validate() throws AmbiguousLL1ParseTableException
    {
        if (this.parseTable.getCell(this.nonTerminalNode, this.terminalNode) != null)
        {
            throw new AmbiguousLL1ParseTableException();
        }
    }
}
