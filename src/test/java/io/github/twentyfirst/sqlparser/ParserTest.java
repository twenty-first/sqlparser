package io.github.twentyfirst.sqlparser;

import org.antlr.v4.runtime.RecognitionException;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ParserTest extends TestBase {

	static final Logger log = LoggerFactory.getLogger(TestBase.class);

	public ParserTest() {
		super(log);
	}

	@Test
	public void testSimpleSelect() throws RecognitionException {
		Assert.assertNotNull(helper.parse("select c from t"));
	}
}
