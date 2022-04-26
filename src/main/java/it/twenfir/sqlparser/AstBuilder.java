package it.twenfir.sqlparser;

import it.twenfir.sqlparser.SqlParser.Into_clauseContext;
import it.twenfir.sqlparser.SqlParser.StatementContext;
import it.twenfir.sqlparser.ast.IntoClause;
import it.twenfir.sqlparser.ast.Statement;

public class AstBuilder extends SqlParserBaseListener {

	private Statement statement;
	
	public AstBuilder() {
	}

	
	@Override
	public void enterStatement(StatementContext ctx) {
		statement = new Statement(ctx);
	}

	
	@Override
	public void enterInto_clause(Into_clauseContext ctx) {
		statement.addIntoClause(new IntoClause(ctx));
	}


	public Statement getStatement() {
		return statement;
	}
}
