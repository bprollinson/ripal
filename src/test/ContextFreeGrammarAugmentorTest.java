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

        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        cfg.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        ContextFreeGrammar expectedCfg = new ContextFreeGrammar();
        expectedCfg.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        expectedCfg.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        expectedCfg.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        assertEquals(expectedCfg, augmentor.augment(cfg));
    }

    @Test
    public void testAugmentCalculatesNewStartSymbolNameFromExistingStartSymbol()
    {
        ContextFreeGrammarAugmentor augmentor = new ContextFreeGrammarAugmentor();

        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("Q"), new NonTerminalNode("R"));

        ContextFreeGrammar expectedCfg = new ContextFreeGrammar();
        expectedCfg.addProduction(new NonTerminalNode("Q'"), new NonTerminalNode("Q"), new EndOfStringNode());
        expectedCfg.addProduction(new NonTerminalNode("Q"), new NonTerminalNode("R"));
        assertEquals(expectedCfg, augmentor.augment(cfg));
    }

    @Test
    public void testAugmentResolvesNamingConflictRelatedtoNewStartSymbolName()
    {
        ContextFreeGrammarAugmentor augmentor = new ContextFreeGrammarAugmentor();

        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new NonTerminalNode("S'"));
        cfg.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S''"));
        cfg.addProduction(new NonTerminalNode("S''"), new NonTerminalNode("S'''"));

        ContextFreeGrammar expectedCfg = new ContextFreeGrammar();
        expectedCfg.addProduction(new NonTerminalNode("S''''"), new NonTerminalNode("S"), new EndOfStringNode());
        expectedCfg.addProduction(new NonTerminalNode("S"), new NonTerminalNode("S'"));
        expectedCfg.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S''"));
        expectedCfg.addProduction(new NonTerminalNode("S''"), new NonTerminalNode("S'''"));
        assertEquals(expectedCfg, augmentor.augment(cfg));
    }

    @Test
    public void testAugmentSplitsMultiCharacterTerminalNodeIntoMultipleNodes()
    {
        ContextFreeGrammarAugmentor augmentor = new ContextFreeGrammarAugmentor();

        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        cfg.addProduction(new NonTerminalNode("A"), new TerminalNode("abc"));

        ContextFreeGrammar expectedCfg = new ContextFreeGrammar();
        expectedCfg.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        expectedCfg.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        expectedCfg.addProduction(new NonTerminalNode("A"), new TerminalNode("a"), new TerminalNode("b"), new TerminalNode("c"));
        assertEquals(expectedCfg, augmentor.augment(cfg));
    }
}
