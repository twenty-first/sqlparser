package it.twenfir.sqlparser.ast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Iterator;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.twenfir.antlr.exception.ParseException;
import it.twenfir.sqlparser.TestBase;

public class AstUnitTests extends TestBase {

	static final Logger log = LoggerFactory.getLogger(AstUnitTests.class);
	
	public AstUnitTests() {
		super(log);
	}

	@Test
	public void selectCountTest() {
		String s = "select count(*) into :c :b from t where p=:a and f='V' and o in ('P','A')";
		Statement statement = helper.ast(s);
		SimpleSelect simpleSelect = statement.getSelectStatements().next().getSelectExpression().getSimpleSelects().next();
		CombinedOutputParameter cop = simpleSelect.getIntoClause().getCombinedOutputParameters().next();
		assertEquals("c", cop.getOutputParameter().getParameter().getName());
		assertEquals("b", cop.getIndicator().getParameter().getName());
		Expression expression = simpleSelect.getWhereClause().getExpression();
		Iterator<Term> termIter = expression.getTerms();
		termIter.next();
		assertEquals("a", termIter.next().getFactor().getCombinedInputParameter().getInputParameter().getParameter().getName());
	}
	

    @Test
    public void testDb2SetOptions() throws ParseException
    {
    	Statement statement = helper.ast("set option naming=*sys, commit=*none");
    	OptionClause oc = statement.getSetOptionStatements().next().getOptionClauses().next();
    	assertEquals("naming", oc.getOptionName().getName());
    	assertEquals("*sys", oc.getOptionValue().getValue());
    }

    private void checkLocate(FunctionCall fc, int argc) {
    	assertEquals("locate", fc.getName());
    	Iterator<Expression> expIter = fc.getExprList().getExpressions();
    	for ( int i = 0; i < argc; i++ ) {
    		expIter.next();
    	}
    	assertFalse(expIter.hasNext());
    }
    
    @Test
    public void testLocate() {
    	Statement statement = helper.ast(
    			"with t as ( select a, b, locate('&h=', upper(b)) as s, locate('&', upper(b), locate('&h=', upper(b))+1 ) as f from u " +
    			"where locate('&h=', upper(b)) > 0) select c into :o from t join v on a = d and e = :p and upper(substr(b, s+6, " +
				"(case when f > 0 then f else (s+16) end)-(s+6))) = upper(:h) and c = 's' fetch first rows only");
    	SimpleSelect ss = statement.getSelectStatements().next().getLocalTableDefinitions().next().getSelectExpression().getSimpleSelects().next();
    	Iterator<SelectColumn> scIter = ss.getSelectColumns();
    	scIter.next();
    	scIter.next();
    	checkLocate(scIter.next().getExpression().getTerms().next().getFunctionCall(), 2);
    	FunctionCall fc = scIter.next().getExpression().getTerms().next().getFunctionCall();
    	checkLocate(fc, 3);
    	Iterator<Expression> expIter = fc.getExprList().getExpressions();
    	expIter.next();
    	expIter.next();
    	checkLocate(expIter.next().getTerms().next().getFunctionCall(), 2);
    	checkLocate(ss.getWhereClause().getExpression().getTerms().next().getFunctionCall(), 2);
    }
}
