package it.twenfir.sqlparser;

import it.twenfir.antlr.ast.AstHelper;
import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.Location;
import it.twenfir.sqlparser.SqlParser.CombinedInputParameterContext;
import it.twenfir.sqlparser.SqlParser.CombinedOutputParameterContext;
import it.twenfir.sqlparser.SqlParser.ExpressionContext;
import it.twenfir.sqlparser.SqlParser.FactorContext;
import it.twenfir.sqlparser.SqlParser.IndicatorContext;
import it.twenfir.sqlparser.SqlParser.InputParameterContext;
import it.twenfir.sqlparser.SqlParser.IntoClauseContext;
import it.twenfir.sqlparser.SqlParser.OutputParameterContext;
import it.twenfir.sqlparser.SqlParser.ParameterContext;
import it.twenfir.sqlparser.SqlParser.SelectExpressionContext;
import it.twenfir.sqlparser.SqlParser.SelectStatementContext;
import it.twenfir.sqlparser.SqlParser.SimpleSelectContext;
import it.twenfir.sqlparser.SqlParser.StatementContext;
import it.twenfir.sqlparser.SqlParser.TermContext;
import it.twenfir.sqlparser.SqlParser.WhereClauseContext;
import it.twenfir.sqlparser.ast.CombinedInputParameter;
import it.twenfir.sqlparser.ast.CombinedOutputParameter;
import it.twenfir.sqlparser.ast.Expression;
import it.twenfir.sqlparser.ast.Factor;
import it.twenfir.sqlparser.ast.Indicator;
import it.twenfir.sqlparser.ast.InputParameter;
import it.twenfir.sqlparser.ast.IntoClause;
import it.twenfir.sqlparser.ast.OutputParameter;
import it.twenfir.sqlparser.ast.Parameter;
import it.twenfir.sqlparser.ast.SelectExpression;
import it.twenfir.sqlparser.ast.SelectStatement;
import it.twenfir.sqlparser.ast.SimpleSelect;
import it.twenfir.sqlparser.ast.Statement;
import it.twenfir.sqlparser.ast.Term;
import it.twenfir.sqlparser.ast.WhereClause;

public class AstBuilder extends SqlParserBaseVisitor<AstNode> {

	@Override
	public Statement visitStatement(StatementContext ctx) {
		Location location = AstHelper.location(ctx);
		Statement statement = new Statement(location);
		AstHelper.visitChildren(this, ctx, statement);
		return statement;
	}

	@Override
	public SelectStatement visitSelectStatement(SelectStatementContext ctx) {
		Location location = AstHelper.location(ctx);
		SelectStatement selectStatement = new SelectStatement(location);
		AstHelper.visitChildren(this, ctx, selectStatement);
		return selectStatement;
	}

	@Override
	public SelectExpression visitSelectExpression(SelectExpressionContext ctx) {
		Location location = AstHelper.location(ctx);
		SelectExpression selectExpression = new SelectExpression(location);
		AstHelper.visitChildren(this, ctx, selectExpression);
		return selectExpression;
	}

	@Override
	public SimpleSelect visitSimpleSelect(SimpleSelectContext ctx) {
		Location location = AstHelper.location(ctx);
		SimpleSelect simpleSelect = new SimpleSelect(location);
		AstHelper.visitChildren(this, ctx, simpleSelect);
		return simpleSelect;
	}

	@Override
	public IntoClause visitIntoClause(IntoClauseContext ctx) {
		Location location = AstHelper.location(ctx);
		IntoClause intoClause = new IntoClause(location);
		AstHelper.visitChildren(this, ctx, intoClause);
		return intoClause;
	}

	@Override
	public WhereClause visitWhereClause(WhereClauseContext ctx) {
		Location location = AstHelper.location(ctx);
		WhereClause whereClause = new WhereClause(location);
		AstHelper.visitChildren(this, ctx, whereClause);
		return whereClause;
	}

	@Override
	public Expression visitExpression(ExpressionContext ctx) {
		Location location = AstHelper.location(ctx);
		Expression expression = new Expression(location);
		AstHelper.visitChildren(this, ctx, expression);
		return expression;
	}

	@Override
	public Term visitTerm(TermContext ctx) {
		Location location = AstHelper.location(ctx);
		Term term = new Term(location);
		AstHelper.visitChildren(this, ctx, term);
		return term;
	}

	@Override
	public Factor visitFactor(FactorContext ctx) {
		Location location = AstHelper.location(ctx);
		Factor factor = new Factor(location);
		AstHelper.visitChildren(this, ctx, factor);
		return factor;
	}

	@Override
	public CombinedInputParameter visitCombinedInputParameter(CombinedInputParameterContext ctx) {
		Location location = AstHelper.location(ctx);
		CombinedInputParameter combinedInputParameter = new CombinedInputParameter(location);
		AstHelper.visitChildren(this, ctx, combinedInputParameter);
		return combinedInputParameter;
	}

	@Override
	public InputParameter visitInputParameter(InputParameterContext ctx) {
		Location location = AstHelper.location(ctx);
		InputParameter inputParameter = new InputParameter(location);
		AstHelper.visitChildren(this, ctx, inputParameter);
		return inputParameter;
	}

	@Override
	public CombinedOutputParameter visitCombinedOutputParameter(CombinedOutputParameterContext ctx) {
		Location location = AstHelper.location(ctx);
		CombinedOutputParameter combinedOutputParameter = new CombinedOutputParameter(location);
		AstHelper.visitChildren(this, ctx, combinedOutputParameter);
		return combinedOutputParameter;
	}

	@Override
	public OutputParameter visitOutputParameter(OutputParameterContext ctx) {
		Location location = AstHelper.location(ctx);
		OutputParameter outputParameter = new OutputParameter(location);
		AstHelper.visitChildren(this, ctx, outputParameter);
		return outputParameter;
	}

	@Override
	public AstNode visitIndicator(IndicatorContext ctx) {
		Location location = AstHelper.location(ctx);
		Indicator indicator = new Indicator(location);
		AstHelper.visitChildren(this, ctx, indicator);
		return indicator;
	}

	@Override
	public AstNode visitParameter(ParameterContext ctx) {
		Location location = AstHelper.location(ctx);
		String name = ctx.IDENTIFIER().getText();
		Integer index = ctx.INTEGER() != null ? Integer.decode(ctx.INTEGER().getText()) : null;
		Parameter parameter = new Parameter(location, name, index);
		AstHelper.visitChildren(this, ctx, parameter);
		return parameter;
	}

}
