package io.github.twentyfirst.sqlparser;

import io.github.twentyfirst.sqlparser.SqlParser.Into_clauseContext;
import io.github.twentyfirst.sqlparser.SqlParser.StatementContext;
import io.github.twentyfirst.sqlparser.ast.IntoClause;
import io.github.twentyfirst.sqlparser.ast.Statement;

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
