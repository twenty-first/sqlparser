package it.twenfir.sqlparser;

import it.twenfir.antlr.ast.AstHelper;
import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.Location;
import it.twenfir.sqlparser.SqlParser.CloseStatementContext;
import it.twenfir.sqlparser.SqlParser.CombinedInputParameterContext;
import it.twenfir.sqlparser.SqlParser.CombinedOutputParameterContext;
import it.twenfir.sqlparser.SqlParser.DeclareCursorStatementContext;
import it.twenfir.sqlparser.SqlParser.ExprListContext;
import it.twenfir.sqlparser.SqlParser.ExpressionContext;
import it.twenfir.sqlparser.SqlParser.FactorContext;
import it.twenfir.sqlparser.SqlParser.FetchStatementContext;
import it.twenfir.sqlparser.SqlParser.FunctionCallContext;
import it.twenfir.sqlparser.SqlParser.FunctionContext;
import it.twenfir.sqlparser.SqlParser.IndicatorContext;
import it.twenfir.sqlparser.SqlParser.InputParameterContext;
import it.twenfir.sqlparser.SqlParser.IntoClauseContext;
import it.twenfir.sqlparser.SqlParser.LocalTableDefinitionContext;
import it.twenfir.sqlparser.SqlParser.OpenStatementContext;
import it.twenfir.sqlparser.SqlParser.OptionClauseContext;
import it.twenfir.sqlparser.SqlParser.OptionNameContext;
import it.twenfir.sqlparser.SqlParser.OptionValueContext;
import it.twenfir.sqlparser.SqlParser.OutputParameterContext;
import it.twenfir.sqlparser.SqlParser.PrepareStatementContext;
import it.twenfir.sqlparser.SqlParser.SelectColumnContext;
import it.twenfir.sqlparser.SqlParser.SelectExpressionContext;
import it.twenfir.sqlparser.SqlParser.SelectStatementContext;
import it.twenfir.sqlparser.SqlParser.SetOptionStatementContext;
import it.twenfir.sqlparser.SqlParser.SimpleSelectContext;
import it.twenfir.sqlparser.SqlParser.StatementContext;
import it.twenfir.sqlparser.SqlParser.TermContext;
import it.twenfir.sqlparser.SqlParser.UsingClauseContext;
import it.twenfir.sqlparser.SqlParser.WhereClauseContext;
import it.twenfir.sqlparser.ast.CloseStatement;
import it.twenfir.sqlparser.ast.CombinedInputParameter;
import it.twenfir.sqlparser.ast.CombinedOutputParameter;
import it.twenfir.sqlparser.ast.DeclareCursorStatement;
import it.twenfir.sqlparser.ast.ExprList;
import it.twenfir.sqlparser.ast.Expression;
import it.twenfir.sqlparser.ast.Factor;
import it.twenfir.sqlparser.ast.FetchStatement;
import it.twenfir.sqlparser.ast.Function;
import it.twenfir.sqlparser.ast.FunctionCall;
import it.twenfir.sqlparser.ast.Indicator;
import it.twenfir.sqlparser.ast.InputParameter;
import it.twenfir.sqlparser.ast.IntoClause;
import it.twenfir.sqlparser.ast.LocalTableDefinition;
import it.twenfir.sqlparser.ast.OpenStatement;
import it.twenfir.sqlparser.ast.OptionClause;
import it.twenfir.sqlparser.ast.OptionName;
import it.twenfir.sqlparser.ast.OptionValue;
import it.twenfir.sqlparser.ast.OutputParameter;
import it.twenfir.sqlparser.ast.PrepareStatement;
import it.twenfir.sqlparser.ast.SelectColumn;
import it.twenfir.sqlparser.ast.SelectExpression;
import it.twenfir.sqlparser.ast.SelectStatement;
import it.twenfir.sqlparser.ast.SetOptionStatement;
import it.twenfir.sqlparser.ast.SimpleSelect;
import it.twenfir.sqlparser.ast.Statement;
import it.twenfir.sqlparser.ast.Term;
import it.twenfir.sqlparser.ast.UsingClause;
import it.twenfir.sqlparser.ast.WhereClause;

public class AstBuilder extends SqlParserBaseVisitor<AstNode> {

	@Override
	public CloseStatement visitCloseStatement(CloseStatementContext ctx) {
		Location location = AstHelper.location(ctx);
		String name = ctx.IDENTIFIER().getText();
		CloseStatement node = new CloseStatement(location, name);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public CombinedInputParameter visitCombinedInputParameter(CombinedInputParameterContext ctx) {
		Location location = AstHelper.location(ctx);
		CombinedInputParameter node = new CombinedInputParameter(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public CombinedOutputParameter visitCombinedOutputParameter(CombinedOutputParameterContext ctx) {
		Location location = AstHelper.location(ctx);
		CombinedOutputParameter node = new CombinedOutputParameter(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public DeclareCursorStatement visitDeclareCursorStatement(DeclareCursorStatementContext ctx) {
		Location location = AstHelper.location(ctx);
		String name = ctx.name.getText();
		String stmt = ctx.stmt.getText();
		DeclareCursorStatement node = new DeclareCursorStatement(location, name, stmt);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}
	
	@Override
	public Expression visitExpression(ExpressionContext ctx) {
		Location location = AstHelper.location(ctx);
		Expression node = new Expression(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public ExprList visitExprList(ExprListContext ctx) {
		Location location = AstHelper.location(ctx);
		ExprList node = new ExprList(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public Factor visitFactor(FactorContext ctx) {
		Location location = AstHelper.location(ctx);
		Factor node = new Factor(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public FetchStatement visitFetchStatement(FetchStatementContext ctx) {
		Location location = AstHelper.location(ctx);
		String name = ctx.IDENTIFIER().getText();
		FetchStatement node = new FetchStatement(location, name);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public Function visitFunction(FunctionContext ctx) {
		Location location = AstHelper.location(ctx);
		String name = ctx.getText();
		Function node = new Function(location, name);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public FunctionCall visitFunctionCall(FunctionCallContext ctx) {
		Location location = AstHelper.location(ctx);
		String name = ctx.IDENTIFIER() != null ? ctx.IDENTIFIER().getText() : null;
		FunctionCall node = new FunctionCall(location, name);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public AstNode visitIndicator(IndicatorContext ctx) {
		Location location = AstHelper.location(ctx);
		String name = ctx.parameter().IDENTIFIER().getText();
		Integer index = ctx.parameter().INTEGER() != null ? Integer.decode(ctx.parameter().INTEGER().getText()) : null;
		Indicator node = new Indicator(location, name, index);
//		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public InputParameter visitInputParameter(InputParameterContext ctx) {
		Location location = AstHelper.location(ctx);
		String name = ctx.parameter().IDENTIFIER().getText();
		Integer index = ctx.parameter().INTEGER() != null ? Integer.decode(ctx.parameter().INTEGER().getText()) : null;
		InputParameter node = new InputParameter(location, name, index);
//		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public IntoClause visitIntoClause(IntoClauseContext ctx) {
		Location location = AstHelper.location(ctx);
		IntoClause node = new IntoClause(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public LocalTableDefinition visitLocalTableDefinition(LocalTableDefinitionContext ctx) {
		Location location = AstHelper.location(ctx);
		LocalTableDefinition node = new LocalTableDefinition(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public OpenStatement visitOpenStatement(OpenStatementContext ctx) {
		Location location = AstHelper.location(ctx);
		String name = ctx.IDENTIFIER().getText();
		OpenStatement node = new OpenStatement(location, name);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public OptionClause visitOptionClause(OptionClauseContext ctx) {
		Location location = AstHelper.location(ctx);
		OptionClause node = new OptionClause(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public OptionName visitOptionName(OptionNameContext ctx) {
		Location location = AstHelper.location(ctx);
		String name = null;
		if ( ctx.IDENTIFIER() != null ) {
			name = ctx.IDENTIFIER().getText();
		}
		else if ( ctx.COMMIT() != null ) {
			name = ctx.COMMIT().getText();
		}
		OptionName node = new OptionName(location, name);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public OptionValue visitOptionValue(OptionValueContext ctx) {
		Location location = AstHelper.location(ctx);
		String value = null;
		if ( ctx.DB2_CONSTANT() != null ) {
			value = ctx.DB2_CONSTANT().getText();
		}
		OptionValue node = new OptionValue(location, value);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public OutputParameter visitOutputParameter(OutputParameterContext ctx) {
		Location location = AstHelper.location(ctx);
		String name = ctx.parameter().IDENTIFIER().getText();
		Integer index = ctx.parameter().INTEGER() != null ? Integer.decode(ctx.parameter().INTEGER().getText()) : null;
		OutputParameter node = new OutputParameter(location, name, index);
//		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public SelectColumn visitSelectColumn(SelectColumnContext ctx) {
		Location location = AstHelper.location(ctx);
		String outputName = ctx.IDENTIFIER() != null ? ctx.IDENTIFIER().getText() : null;
		SelectColumn node = new SelectColumn(location, outputName);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public PrepareStatement visitPrepareStatement(PrepareStatementContext ctx) {
		Location location = AstHelper.location(ctx);
		String name = ctx.IDENTIFIER().getText();
		PrepareStatement node = new PrepareStatement(location, name);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public SelectExpression visitSelectExpression(SelectExpressionContext ctx) {
		Location location = AstHelper.location(ctx);
		SelectExpression node = new SelectExpression(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public SelectStatement visitSelectStatement(SelectStatementContext ctx) {
		Location location = AstHelper.location(ctx);
		SelectStatement node = new SelectStatement(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public SetOptionStatement visitSetOptionStatement(SetOptionStatementContext ctx) {
		Location location = AstHelper.location(ctx);
		SetOptionStatement node = new SetOptionStatement(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public SimpleSelect visitSimpleSelect(SimpleSelectContext ctx) {
		Location location = AstHelper.location(ctx);
		SimpleSelect node = new SimpleSelect(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

//	@Override
//	public Statement visitStatement(StatementContext ctx) {
//		Location location = AstHelper.location(ctx);
//		Statement node = new Statement(location);
//		AstHelper.visitChildren(this, ctx, node);
//		return node;
//	}

	@Override
	public Statement visitStatement(StatementContext ctx) {
		Statement node = (Statement)AstHelper.visitChild(this, ctx);
		return node;
	}

	@Override
	public Term visitTerm(TermContext ctx) {
		Location location = AstHelper.location(ctx);
		Term node = new Term(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public UsingClause visitUsingClause(UsingClauseContext ctx) {
		Location location = AstHelper.location(ctx);
		UsingClause node = new UsingClause(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public WhereClause visitWhereClause(WhereClauseContext ctx) {
		Location location = AstHelper.location(ctx);
		WhereClause node = new WhereClause(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

}
