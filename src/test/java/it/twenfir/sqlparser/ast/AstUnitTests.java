package it.twenfir.sqlparser.ast;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.Iterator;

import org.junit.jupiter.api.Test;
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
		SelectStatement statement = (SelectStatement)helper.ast(s);
		SimpleSelect simpleSelect = statement.getSelectExpression().getSimpleSelects().next();
		CombinedOutputParameter cop = (CombinedOutputParameter)simpleSelect.getIntoClause().getOutputParameters().next();
		assertEquals("c", cop.getParameter().getName());
		assertEquals("b", cop.getIndicator().getName());
		Expression expression = simpleSelect.getWhereClause().getExpression();
		Iterator<Term> termIter = expression.getTerms();
		termIter.next();
		assertEquals("a", termIter.next().getFactor().getCombinedInputParameter().getParameter().getName());
	}

	@Test
	public void qualifiedParameterNameTest() throws ParseException {
		SelectStatement statement = (SelectStatement)helper.ast("select c from t where d = :e.f");
		InputParameter p = statement.getInputParameters().next();
		assertEquals("e.f", p.getParameter().getName());
	}

    @Test
    public void testDb2SetOptions() throws ParseException
    {
    	SetOptionStatement statement = (SetOptionStatement)helper.ast("set option naming=*sys, commit=*none");
    	OptionClause oc = statement.getOptionClauses().next();
    	assertEquals("naming", oc.getName());
    	assertEquals("*sys", oc.getValue());
    }

    private void checkLocate(FunctionCall fc, int argc) {
    	assertEquals("locate", fc.getFunction().getName());
    	Iterator<Expression> expIter = fc.getExprList().getExpressions();
    	for ( int i = 0; i < argc; i++ ) {
    		expIter.next();
    	}
    	assertFalse(expIter.hasNext());
    }
    
    @Test
    public void testLocate() {
    	SelectStatement statement = (SelectStatement)helper.ast(
    			"with t as ( select a, b, locate('&h=', upper(b)) as s, locate('&', upper(b), locate('&h=', upper(b))+1 ) as f from u " +
    			"where locate('&h=', upper(b)) > 0) select c into :o from t join v on a = d and e = :p and upper(substr(b, s+6, " +
				"(case when f > 0 then f else (s+16) end)-(s+6))) = upper(:h) and c = 's' fetch first rows only");
    	SimpleSelect ss = statement.getLocalTableDefinitions().next().getSelectExpression().getSimpleSelects().next();
    	Iterator<SelectColumn> scIter = ss.getSelectColumns();
    	scIter.next();
    	scIter.next();
    	checkLocate(scIter.next().getColumnExpression().getExpression().getTerms().next().getFunctionCall(), 2);
    	FunctionCall fc = scIter.next().getColumnExpression().getExpression().getTerms().next().getFunctionCall();
    	checkLocate(fc, 3);
    	Iterator<Expression> expIter = fc.getExprList().getExpressions();
    	expIter.next();
    	expIter.next();
    	checkLocate(expIter.next().getTerms().next().getFunctionCall(), 2);
    	checkLocate(ss.getWhereClause().getExpression().getTerms().next().getFunctionCall(), 2);
    }

	@Test
	public void testColumnName() {
    	SelectStatement statement = (SelectStatement)helper.ast(
    			"with t as ( select a, b, locate('&h=', upper(b)) as s, locate('&', upper(b), locate('&h=', upper(b))+1 ) as f from u " +
    			"where locate('&h=', upper(b)) > 0) select c into :o from t join v on a = d and e = :p and upper(substr(b, s+6, " +
				"(case when f > 0 then f else (s+16) end)-(s+6))) = upper(:h) and c = 's' fetch first rows only");
    	SimpleSelect ss = statement.getSelectExpression().getSimpleSelects().next();
		Iterator<SelectColumn> sc = ss.getSelectColumns();
		ColumnExpression ce = sc.next().getColumnExpression();
		assertNotNull(ce);
		assertEquals("c", ce.getName());
	}

    @Test
    public void testParameterNamedTemp() throws ParseException
    {
        SelectStatement statement = (SelectStatement)helper.ast("select count(f) into :op from t where g = :ip and " + 
                "f like '' concat rtrim(:temp) concat '%' concat '' and h <> 'ok' and i <> 0");
    	SimpleSelect ss = statement.getSelectExpression().getSimpleSelects().next();
		WhereClause wc = ss.getWhereClause();
		Iterator<InputParameter> iter = wc.getDescendants(InputParameter.class);
		iter.next();
		InputParameter p = iter.next();
		assertEquals("temp", p.getParameter().getName());
    }

    @Test
    public void testSelectIntoWithIndicator() throws ParseException
    {
    	SelectStatement statement = (SelectStatement)helper.ast("select c1 into :p :i from s.t where c2 = 'v'");
		Iterator<CombinedOutputParameter> iter = statement.getDescendants(CombinedOutputParameter.class);
		CombinedOutputParameter p = iter.next();
		assertEquals("i", p.getIndicator().getName());
    }
}
