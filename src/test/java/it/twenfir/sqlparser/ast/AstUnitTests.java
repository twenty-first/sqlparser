package it.twenfir.sqlparser.ast;

import java.util.Iterator;

import org.junit.Assert;
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
	public void test() {
		String s = "select count(*) into :c :b from t where p=:a and f='V' and o in ('P','A')";
		Statement ast = helper.ast(s);
		Assert.assertNotNull(ast);
		Iterator<IntoClause> iter = ast.getIntoClauses();
		Assert.assertNotNull(iter.next());
		Assert.assertFalse(iter.hasNext());
	}
}
