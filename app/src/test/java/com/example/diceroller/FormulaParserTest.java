package com.example.diceroller;

import org.junit.Assert;
import org.junit.Test;

public class FormulaParserTest {

    @Test
    public void parse_ok() {
        FormulaParser formula = new FormulaParser("4d10 +2 * 3-11+5");
        try {
            formula.parse();
        }
        catch (Exception ex) {
            Assert.fail();
        }
        /*for (String sign: formula.signs) {
            System.out.println(sign);
        }
        for (String str: formula.processedParts) {
            System.out.println(str);
        }*/

        System.out.println(formula.prettyFormula + " = " + formula.result);
        Assert.assertEquals(1, formula.processedParts.size());
    }

    @Test
    public void parse_multiply() {
        FormulaParser formula = new FormulaParser("10*5*6");
        try {
            formula.parse();
        }
        catch (Exception ex) {
            Assert.fail();
        }
        System.out.println(formula.prettyFormula + " = " + formula.result);
        Assert.assertEquals("300", formula.result);
    }

    @Test
    public void parse_deduction() {
        FormulaParser formula = new FormulaParser("10-2-4");
        try {
            formula.parse();
        }
        catch (Exception ex) {
            Assert.fail();
        }

        System.out.println(formula.prettyFormula + " = " + formula.result);
        Assert.assertEquals("4", formula.result);
    }

    @Test
    public void parse_differentActions() {
        FormulaParser formula = new FormulaParser("1 +5* 2-8");
        try {
            formula.parse();
        }
        catch (Exception ex) {
            Assert.fail();
        }

        System.out.println(formula.prettyFormula + " = " + formula.result);
        Assert.assertEquals("3", formula.result);
    }

    @Test
    public void parse_generateRandom() {
        FormulaParser formula = new FormulaParser("1d20+5-4d6-2+10d10*3");
        try {
            formula.parse();
        }
        catch (Exception ex) {
            Assert.fail();
        }

        System.out.println(formula.prettyFormula + " = " + formula.result);
        Assert.assertTrue("Просто смотрю на результат", true);
    }

    @Test
    public void parse_wrongFormula() {
        //FormulaParser formula = new FormulaParser("1 + 5k$@*2- 8");
        FormulaParser formula = new FormulaParser("(1d6+2)*3");
        try {
            formula.parse();
        }
        catch (Exception ex) {
            formula.result = "Ошибка";
        }

        System.out.println(formula.prettyFormula + " = " + formula.result);
        Assert.assertEquals("Ошибка", formula.result);
    }
}