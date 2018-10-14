import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.LR0Parser;
import larp.parser.contextfreelanguage.LR0ParseTable;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;

import java.util.HashSet;

public class LR0ParserTest
{
    @Test
    public void testStructureEqualsReturnsTrueWhenParseTablesHaveSameStructure()
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        LR0ProductionSetDFAState startState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ParseTable parseTable = new LR0ParseTable(cfg, startState);
        LR0Parser parser = new LR0Parser(parseTable);

        throw new RuntimeException();
    }

    @Test
    public void testStructureEqualsReturnsFalseWhenParseTablesHaveDifferentStructure()
    {
        throw new RuntimeException();
    }
}
