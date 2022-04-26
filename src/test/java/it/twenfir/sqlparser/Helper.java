package it.twenfir.sqlparser;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.slf4j.Logger;

import it.twenfir.sqlparser.SqlParser.StatementContext;
import it.twenfir.sqlparser.ast.Statement;

public class Helper extends BaseErrorListener {

	Logger log;
	boolean failed = false;
	
	public Helper(Logger log) {
		this.log = log;
	}
	
	private Driver driver(String src) {
		failed = false;
		return new Driver(src, this);
	}
	
	public StatementContext	parse(String src) {
		Driver d = driver(src);
		StatementContext sc = d.parse();
		return failed ? null : sc;
	}

	public Statement ast(String src) {
		Driver d = driver(src);
		Statement s = d.makeAst();
		return failed ? null : s;
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
