package it.twenfir.sqlparser.ast;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VisitorUnitTests {
    
    @Test
    public void test() {
        SqlBaseVisitor<Integer> visitor = new SqlBaseVisitor<Integer>() {

            @Override
            public Integer aggregate(Integer accumulator, Integer value) {
                return accumulator + value;
            }

            @Override
            public Integer defaultValue() {
                return 1;
            }

        };

        IntoClause ic1 = new IntoClause(null);
        IntoClause ic2 = new IntoClause(null);
        Statement s = new Statement(null);
        s.addChild(ic1);
        s.addChild(ic2);
        int i = visitor.visit(s);
        assertEquals(3, i);
    }
}
