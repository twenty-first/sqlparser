package it.twenfir.sqlparser.ast;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		assertEquals("a", termIter.next().getFactors().next().getCombinedInputParameter().getInputParameter().getParameter().getName());
	}
}
