package io.github.twentyfirst.sqlparser;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.twentyfirst.sqlparser.ast.IntoClause;
import io.github.twentyfirst.sqlparser.ast.Statement;

public class AstTest extends TestBase {

	static final Logger log = LoggerFactory.getLogger(AstTest.class);
	
	public AstTest() {
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
