package it.twenfir.sqlparser;

import org.slf4j.Logger;

import it.twenfir.sqlparser.SqlParser.StatementContext;
import it.twenfir.sqlparser.ast.SqlVisitor;
import it.twenfir.sqlparser.ast.Statement;

public class Helper {

	Logger log;
	boolean failed = false;
	
	public Helper(Logger log) {
		this.log = log;
	}
	
	private SqlParserDriver driver(String src) {
		failed = false;
		return new SqlParserDriver(src);
	}
	
	public StatementContext	parse(String src) {
		SqlParserDriver d = driver(src);
		StatementContext sc = d.parse();
		return failed ? null : sc;
	}

	public Statement ast(String src) {
		SqlParserDriver d = driver(src);
		Statement s = d.makeAst();
		return failed ? null : s;
	}
	
	public <T> T visit(String src, SqlVisitor<T> v) {
		SqlParserDriver d = driver(src);
		Statement s = d.makeAst();
		return v.visitStatement(s);
	}
}
