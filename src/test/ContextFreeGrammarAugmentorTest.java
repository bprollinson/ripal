import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parsetree.contextfreelanguage.EndOfStringNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;
import larp.syntaxcompiler.contextfreelanguage.ContextFreeGrammarAugmentor;

public class ContextFreeGrammarAugmentorTest
{
    @Test
    public void testAugmentAddsNewStartStateToValidGrammar()
    {
        ContextFreeGrammarAugmentor augmentor = new ContextFreeGrammarAugmentor();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        ContextFreeGrammar expectedGrammar = new ContextFreeGrammar();
        expectedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        expectedGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        expectedGrammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        assertEquals(expectedGrammar, augmentor.augment(grammar));
    }

    @Test
    public void testAugmentCalculatesNewStartSymbolNameFromExistingStartSymbol()
    {
        ContextFreeGrammarAugmentor augmentor = new ContextFreeGrammarAugmentor();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("Q"), new NonTerminalNode("R"));

        ContextFreeGrammar expectedGrammar = new ContextFreeGrammar();
        expectedGrammar.addProduction(new NonTerminalNode("Q'"), new NonTerminalNode("Q"), new EndOfStringNode());
        expectedGrammar.addProduction(new NonTerminalNode("Q"), new NonTerminalNode("R"));
        assertEquals(expectedGrammar, augmentor.augment(grammar));
    }

    @Test
    public void testAugmentResolvesNamingConflictRelatedtoNewStartSymbolName()
    {
        ContextFreeGrammarAugmentor augmentor = new ContextFreeGrammarAugmentor();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("S'"));
        grammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S''"));
        grammar.addProduction(new NonTerminalNode("S''"), new NonTerminalNode("S'''"));

        ContextFreeGrammar expectedGrammar = new ContextFreeGrammar();
        expectedGrammar.addProduction(new NonTerminalNode("S''''"), new NonTerminalNode("S"), new EndOfStringNode());
        expectedGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("S'"));
        expectedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S''"));
        expectedGrammar.addProduction(new NonTerminalNode("S''"), new NonTerminalNode("S'''"));
        assertEquals(expectedGrammar, augmentor.augment(grammar));
    }

    @Test
    public void testAugmentSplitsMultiCharacterTerminalNodeIntoMultipleNodes()
    {
        ContextFreeGrammarAugmentor augmentor = new ContextFreeGrammarAugmentor();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("abc"));

        ContextFreeGrammar expectedGrammar = new ContextFreeGrammar();
        expectedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        expectedGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        expectedGrammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"), new TerminalNode("b"), new TerminalNode("c"));
        assertEquals(expectedGrammar, augmentor.augment(grammar));
    }
}
