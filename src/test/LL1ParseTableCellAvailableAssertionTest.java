import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.AmbiguousLL1ParseTableException;
import larp.parser.contextfreelanguage.LL1ParseTable;
import larp.parser.contextfreelanguage.LL1ParseTableCellAvailableAssertion;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

public class LL1ParseTableCellAvailableAssertionTest
{
    @Test(expected = AmbiguousLL1ParseTableException.class)
    public void testValidateThrowsExceptionForCellThatAlreadyExists() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        LL1ParseTableCellAvailableAssertion assertion = new LL1ParseTableCellAvailableAssertion(parseTable, new NonTerminalNode("S"), new TerminalNode("a"));

        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForCellThatDoesNotAlreadyExist() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        LL1ParseTable parseTable = new LL1ParseTable(grammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        LL1ParseTableCellAvailableAssertion assertion = new LL1ParseTableCellAvailableAssertion(parseTable, new NonTerminalNode("S"), new TerminalNode("b"));

        assertion.validate();
    }
}
