package it.twenfir.sqlparser;

import it.twenfir.antlr.ast.AstHelper;
import it.twenfir.antlr.ast.Location;
import it.twenfir.sqlparser.SqlParser.IntoClauseContext;
import it.twenfir.sqlparser.SqlParser.StatementContext;
import it.twenfir.sqlparser.ast.IntoClause;
import it.twenfir.sqlparser.ast.Statement;

public class AstBuilder extends SqlParserBaseListener {

	private Statement statement;
	
	public AstBuilder() {
	}

	
	@Override
	public void enterStatement(StatementContext ctx) {
		Location location = AstHelper.location(ctx);
		statement = new Statement(location);
	}

	
	@Override
	public void enterIntoClause(IntoClauseContext ctx) {
		Location location = AstHelper.location(ctx);
		statement.addChild(new IntoClause(location));
	}


	public Statement getStatement() {
		return statement;
	}
}
