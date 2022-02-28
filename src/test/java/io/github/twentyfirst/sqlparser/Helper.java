package io.github.twentyfirst.sqlparser;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.slf4j.Logger;

import io.github.twentyfirst.sqlparser.SqlParser.StatementContext;

public class Helper extends BaseErrorListener {

	Logger log;
	boolean failed = false;
	
	public Helper(Logger log) {
		this.log = log;
	}
	
	public StatementContext	parse(String src) {
		failed = false;
		Driver d = new Driver(src, this);
		StatementContext sc = d.parse();
		return failed ? null : sc;
	}

	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
			int charPositionInLine, String msg, RecognitionException e) {
		log.error(String.format("(%d, %d): %s", line, charPositionInLine, msg));
		failed = true;
	}
	
	boolean isFailed() {
		return failed;
	}
}
