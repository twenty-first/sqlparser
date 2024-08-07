package it.twenfir.sqlparser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.RuleNode;

import it.twenfir.antlr.api.ErrorListener;
import it.twenfir.antlr.ast.AstHelper;
import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.Location;
import it.twenfir.antlr.exception.AstException;
import it.twenfir.antlr.parser.DefaultErrorListener;
import it.twenfir.sqlparser.SqlParser.AlterTableStatementContext;
import it.twenfir.sqlparser.SqlParser.CloseStatementContext;
import it.twenfir.sqlparser.SqlParser.ColumnExpressionContext;
import it.twenfir.sqlparser.SqlParser.CombinedInputParameterContext;
import it.twenfir.sqlparser.SqlParser.CombinedOutputParameterContext;
import it.twenfir.sqlparser.SqlParser.CommitStatementContext;
import it.twenfir.sqlparser.SqlParser.CreateIndexStatementContext;
import it.twenfir.sqlparser.SqlParser.CreateTableStatementContext;
import it.twenfir.sqlparser.SqlParser.DeclareCursorStatementContext;
import it.twenfir.sqlparser.SqlParser.DeclareTempTableStatementContext;
import it.twenfir.sqlparser.SqlParser.DeleteStatementContext;
import it.twenfir.sqlparser.SqlParser.DropTableStatementContext;
import it.twenfir.sqlparser.SqlParser.ExecuteStatementContext;
import it.twenfir.sqlparser.SqlParser.ExprListContext;
import it.twenfir.sqlparser.SqlParser.ExpressionContext;
import it.twenfir.sqlparser.SqlParser.FactorContext;
import it.twenfir.sqlparser.SqlParser.FetchStatementContext;
import it.twenfir.sqlparser.SqlParser.FromClauseContext;
import it.twenfir.sqlparser.SqlParser.FunctionCallContext;
import it.twenfir.sqlparser.SqlParser.FunctionContext;
import it.twenfir.sqlparser.SqlParser.IndicatorContext;
import it.twenfir.sqlparser.SqlParser.InputParameterContext;
import it.twenfir.sqlparser.SqlParser.InsertStatementContext;
import it.twenfir.sqlparser.SqlParser.IntoClauseContext;
import it.twenfir.sqlparser.SqlParser.LocalTableDefinitionContext;
import it.twenfir.sqlparser.SqlParser.OpenStatementContext;
import it.twenfir.sqlparser.SqlParser.OptionClauseContext;
import it.twenfir.sqlparser.SqlParser.OutputParameterContext;
import it.twenfir.sqlparser.SqlParser.ParameterContext;
import it.twenfir.sqlparser.SqlParser.PrepareStatementContext;
import it.twenfir.sqlparser.SqlParser.SelectColumnContext;
import it.twenfir.sqlparser.SqlParser.SelectExpressionContext;
import it.twenfir.sqlparser.SqlParser.SelectStatementContext;
import it.twenfir.sqlparser.SqlParser.SetOptionStatementContext;
import it.twenfir.sqlparser.SqlParser.SetStatementContext;
import it.twenfir.sqlparser.SqlParser.SimpleInputParameterContext;
import it.twenfir.sqlparser.SqlParser.SimpleOutputParameterContext;
import it.twenfir.sqlparser.SqlParser.SimpleSelectContext;
import it.twenfir.sqlparser.SqlParser.StatementContext;
import it.twenfir.sqlparser.SqlParser.TermContext;
import it.twenfir.sqlparser.SqlParser.UpdateStatementContext;
import it.twenfir.sqlparser.SqlParser.UsingClauseContext;
import it.twenfir.sqlparser.SqlParser.ValuesStatementContext;
import it.twenfir.sqlparser.SqlParser.WhereClauseContext;
import it.twenfir.sqlparser.SqlParser.WithUrClauseContext;
import it.twenfir.sqlparser.ast.AlterTableStatement;
import it.twenfir.sqlparser.ast.CloseStatement;
import it.twenfir.sqlparser.ast.ColumnExpression;
import it.twenfir.sqlparser.ast.CombinedInputParameter;
import it.twenfir.sqlparser.ast.CombinedOutputParameter;
import it.twenfir.sqlparser.ast.CommitStatement;
import it.twenfir.sqlparser.ast.CreateIndexStatement;
import it.twenfir.sqlparser.ast.CreateTableStatement;
import it.twenfir.sqlparser.ast.DeclareCursorStatement;
import it.twenfir.sqlparser.ast.DeclareTempTableStatement;
import it.twenfir.sqlparser.ast.DeleteStatement;
import it.twenfir.sqlparser.ast.DropTableStatement;
import it.twenfir.sqlparser.ast.ExecuteStatement;
import it.twenfir.sqlparser.ast.ExprList;
import it.twenfir.sqlparser.ast.Expression;
import it.twenfir.sqlparser.ast.Factor;
import it.twenfir.sqlparser.ast.FetchStatement;
import it.twenfir.sqlparser.ast.FromClause;
import it.twenfir.sqlparser.ast.Function;
import it.twenfir.sqlparser.ast.FunctionCall;
import it.twenfir.sqlparser.ast.InsertStatement;
import it.twenfir.sqlparser.ast.IntoClause;
import it.twenfir.sqlparser.ast.LocalTableDefinition;
import it.twenfir.sqlparser.ast.OpenStatement;
import it.twenfir.sqlparser.ast.OptionClause;
import it.twenfir.sqlparser.ast.Parameter;
import it.twenfir.sqlparser.ast.PrepareStatement;
import it.twenfir.sqlparser.ast.SelectColumn;
import it.twenfir.sqlparser.ast.SelectExpression;
import it.twenfir.sqlparser.ast.SelectStatement;
import it.twenfir.sqlparser.ast.SetOptionStatement;
import it.twenfir.sqlparser.ast.SetStatement;
import it.twenfir.sqlparser.ast.SimpleInputParameter;
import it.twenfir.sqlparser.ast.SimpleOutputParameter;
import it.twenfir.sqlparser.ast.SimpleSelect;
import it.twenfir.sqlparser.ast.Statement;
import it.twenfir.sqlparser.ast.Term;
import it.twenfir.sqlparser.ast.UpdateStatement;
import it.twenfir.sqlparser.ast.UsingClause;
import it.twenfir.sqlparser.ast.ValuesStatement;
import it.twenfir.sqlparser.ast.WhereClause;
import it.twenfir.sqlparser.ast.WithUrClause;

public class AstBuilder extends SqlParserBaseVisitor<AstNode> {
	
	@SuppressWarnings("unused")
	private ErrorListener listener;

	public AstBuilder(ErrorListener listener) {
		this.listener = listener != null ? listener : new DefaultErrorListener();
	}

	@Override
	public AstNode visitChildren(RuleNode node) {
		return AstHelper.visit(this, (ParserRuleContext)node);
	}

	@Override
	public AlterTableStatement visitAlterTableStatement(AlterTableStatementContext ctx) {
		Location location = AstHelper.location(ctx);
		AlterTableStatement node = new AlterTableStatement(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public CloseStatement visitCloseStatement(CloseStatementContext ctx) {
		Location location = AstHelper.location(ctx);
		String name = ctx.identifier().getText();
		CloseStatement node = new CloseStatement(location, name);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public CombinedInputParameter visitCombinedInputParameter(CombinedInputParameterContext ctx) {
		Location location = AstHelper.location(ctx);
		Parameter parameter = null;
		if ( ctx.inputParameter() != null ) {
			parameter = (Parameter)AstHelper.visitChild(this, ctx.inputParameter());
		}
		Parameter indicator = null;
		if ( ctx.indicator() != null ) {
			indicator = (Parameter)AstHelper.visitChild(this, ctx.indicator());
		}
		CombinedInputParameter node = new CombinedInputParameter(location, parameter, indicator);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public CombinedOutputParameter visitCombinedOutputParameter(CombinedOutputParameterContext ctx) {
		Location location = AstHelper.location(ctx);
		Parameter parameter = null;
		if ( ctx.outputParameter() != null ) {
			parameter = (Parameter)AstHelper.visitChild(this, ctx.outputParameter());
		}
		Parameter indicator = null;
		if ( ctx.indicator() != null ) {
			indicator = (Parameter)AstHelper.visitChild(this, ctx.indicator());
		}
		CombinedOutputParameter node = new CombinedOutputParameter(location, parameter, indicator);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public ColumnExpression visitColumnExpression(ColumnExpressionContext ctx) {
		Location location = AstHelper.location(ctx);
		String name = ctx.identifier() != null ? ctx.identifier().getText() : null;
		ColumnExpression node = new ColumnExpression(location, name);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public CommitStatement visitCommitStatement(CommitStatementContext ctx) {
		Location location = AstHelper.location(ctx);
		CommitStatement node = new CommitStatement(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public CreateIndexStatement visitCreateIndexStatement(CreateIndexStatementContext ctx) {
		Location location = AstHelper.location(ctx);
		CreateIndexStatement node = new CreateIndexStatement(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public CreateTableStatement visitCreateTableStatement(CreateTableStatementContext ctx) {
		Location location = AstHelper.location(ctx);
		CreateTableStatement node = new CreateTableStatement(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public DeclareCursorStatement visitDeclareCursorStatement(DeclareCursorStatementContext ctx) {
		Location location = AstHelper.location(ctx);
		String name = ctx.name.getText();
		String stmt = ctx.stmt != null ? ctx.stmt.getText() : null;
		DeclareCursorStatement node = new DeclareCursorStatement(location, name, stmt);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public DeclareTempTableStatement visitDeclareTempTableStatement(DeclareTempTableStatementContext ctx) {
		Location location = AstHelper.location(ctx);
		DeclareTempTableStatement node = new DeclareTempTableStatement(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public DeleteStatement visitDeleteStatement(DeleteStatementContext ctx) {
		Location location = AstHelper.location(ctx);
		DeleteStatement node = new DeleteStatement(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public DropTableStatement visitDropTableStatement(DropTableStatementContext ctx) {
		Location location = AstHelper.location(ctx);
		String table = ctx.table().getText();
		DropTableStatement node = new DropTableStatement(location, table);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}
	
	@Override
	public ExecuteStatement visitExecuteStatement(ExecuteStatementContext ctx) {
		Location location = AstHelper.location(ctx);
		ExecuteStatement node = new ExecuteStatement(location);
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
		String name = ctx.identifier().getText();
		FetchStatement node = new FetchStatement(location, name);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public FromClause visitFromClause(FromClauseContext ctx) {
		Location location = AstHelper.location(ctx);
		FromClause node = new FromClause(location);
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
		FunctionCall node = new FunctionCall(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public Parameter visitIndicator(IndicatorContext ctx) {
		Parameter node = (Parameter)AstHelper.visitChild(this, ctx);
		return node;
}

	@Override
	public Parameter visitInputParameter(InputParameterContext ctx) {
		Parameter node = (Parameter)AstHelper.visitChild(this, ctx);
		return node;
	}

	@Override
	public InsertStatement visitInsertStatement(InsertStatementContext ctx) {
		Location location = AstHelper.location(ctx);
		InsertStatement node = new InsertStatement(location);
		AstHelper.visitChildren(this, ctx, node);
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
		String name = ctx.identifier().getText();
		OpenStatement node = new OpenStatement(location, name);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public OptionClause visitOptionClause(OptionClauseContext ctx) {
		Location location = AstHelper.location(ctx);
		String name = null;
		if ( ctx.optionName().identifier() != null ) {
			name = ctx.optionName().identifier().getText();
		}
		else if ( ctx.optionName().COMMIT() != null ) {
			name = ctx.optionName().COMMIT().getText();
		}
		String value = null;
		if ( ctx.optionValue().DB2_CONSTANT() != null ) {
			value = ctx.optionValue().DB2_CONSTANT().getText();
		}
		OptionClause node = new OptionClause(location, name, value);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public Parameter visitOutputParameter(OutputParameterContext ctx) {
		Parameter node = (Parameter)AstHelper.visitChild(this, ctx);
		return node;
	}

	@Override
	public Parameter visitParameter(ParameterContext ctx) {
		Location location = AstHelper.location(ctx);
		String name = ctx.identifier(0).getText();
		if ( ctx.identifier().size() == 2 ) {
			name += "." + ctx.identifier(1).getText();
		}
		Integer index = ctx.INTEGER() != null ? Integer.decode(ctx.INTEGER().getText()) : null;
		Parameter node = new Parameter(location, name, index);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public PrepareStatement visitPrepareStatement(PrepareStatementContext ctx) {
		Location location = AstHelper.location(ctx);
		String name = ctx.identifier().getText();
		PrepareStatement node = new PrepareStatement(location, name);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public SelectColumn visitSelectColumn(SelectColumnContext ctx) {
		Location location = AstHelper.location(ctx);
		String outputName = ctx.identifier() != null ? ctx.identifier().getText() : null;
		SelectColumn node = new SelectColumn(location, outputName);
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
	public SetStatement visitSetStatement(SetStatementContext ctx) {
		Location location = AstHelper.location(ctx);
		String name = null;
		if ( ctx.identifier() != null ) {
			name = ctx.identifier().getText();
		}
		SetStatement node = new SetStatement(location, name);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public SimpleInputParameter visitSimpleInputParameter(SimpleInputParameterContext ctx) {
		Location location = AstHelper.location(ctx);
		Parameter parameter = null;
		if ( ctx.inputParameter() != null ) {
			parameter = (Parameter)AstHelper.visitChild(this, ctx.inputParameter());
		}
		SimpleInputParameter node = new SimpleInputParameter(location, parameter);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public SimpleOutputParameter visitSimpleOutputParameter(SimpleOutputParameterContext ctx) {
		Location location = AstHelper.location(ctx);
		Parameter parameter = null;
		if ( ctx.outputParameter() != null ) {
			parameter = (Parameter)AstHelper.visitChild(this, ctx.outputParameter());
		}
		SimpleOutputParameter node = new SimpleOutputParameter(location, parameter);
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

	@Override
	public Statement visitStatement(StatementContext ctx) {
		AstNode node = AstHelper.visitChild(this, ctx);
		if ( ! ( node instanceof Statement ) ) {
			throw new AstException("Statement type not supported: " + ctx.getChild(0));
		}
		return (Statement)node;
	}

	@Override
	public Term visitTerm(TermContext ctx) {
		Location location = AstHelper.location(ctx);
		Term node = new Term(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public UpdateStatement visitUpdateStatement(UpdateStatementContext ctx) {
		Location location = AstHelper.location(ctx);
		UpdateStatement node = new UpdateStatement(location);
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
	public ValuesStatement visitValuesStatement(ValuesStatementContext ctx) {
		Location location = AstHelper.location(ctx);
		ValuesStatement node = new ValuesStatement(location);
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

	@Override
	public WithUrClause visitWithUrClause(WithUrClauseContext ctx) {
		Location location = AstHelper.location(ctx);
		WithUrClause node = new WithUrClause(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

}
