package it.twenfir.sqlparser.ast;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.twenfir.sqlparser.TestBase;

public class VisitorUnitTests extends TestBase {
    
	private static Logger log = LoggerFactory.getLogger(VisitorUnitTests.class);
	
	public VisitorUnitTests() {
		super(log);
	}
	
    @Test
    public void simpleTest() {
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
        Statement s = new SelectStatement(null);
        s.addChild(ic1);
        s.addChild(ic2);
        int i = visitor.visit(s);
        assertEquals(3, i);
    }
    
    @Test
    public void testLocate() {
    	Statement statement = helper.ast(
    			"with t as ( select a, b, locate('&h=', upper(b)) as s, locate('&', upper(b), locate('&h=', upper(b))+1 ) as f from u " +
    			"where locate('&h=', upper(b)) > 0) select c into :o from t join v on a = d and e = :p and upper(substr(b, s+6, " +
				"(case when f > 0 then f else (s+16) end)-(s+6))) = upper(:h) and c = 's' fetch first rows only");
    	
    	SqlVisitor<? extends Integer> visitor = new SqlBaseVisitor<Integer>() {

            @Override
            public Integer aggregate(Integer accumulator, Integer value) {
                return accumulator + value;
            }

            @Override
            public Integer defaultValue() {
                return 0;
            }

			@Override
			public Integer visitFunction(Function node) {
				int v = node.getName().equals("locate") ? 1 : 0;
				return visitChildren(node) + v;
			}

    	};
    	int i = visitor.visitStatement(statement);
    	assertEquals(4, i);
    }
}
