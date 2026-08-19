package it.twenfir.sqlparser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.RuleNode;

import it.twenfir.antlr.api.ErrorListener;
import it.twenfir.antlr.ast.AstHelper;
import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.Location;
import it.twenfir.antlr.ast.Node;
import it.twenfir.antlr.exception.AstException;
import it.twenfir.antlr.parser.DefaultErrorListener;
import it.twenfir.sqlparser.SqlParser.AlterTableStatementContext;
import it.twenfir.sqlparser.SqlParser.BinaryOpContext;
import it.twenfir.sqlparser.SqlParser.CloseStatementContext;
import it.twenfir.sqlparser.SqlParser.ColumnDefinitionContext;
import it.twenfir.sqlparser.SqlParser.ColumnExpressionContext;
import it.twenfir.sqlparser.SqlParser.ColumnLabelContext;
import it.twenfir.sqlparser.SqlParser.CombinedInputParameterContext;
import it.twenfir.sqlparser.SqlParser.CombinedOutputParameterContext;
import it.twenfir.sqlparser.SqlParser.CommitStatementContext;
import it.twenfir.sqlparser.SqlParser.ConnectStatementContext;
import it.twenfir.sqlparser.SqlParser.CreateIndexStatementContext;
import it.twenfir.sqlparser.SqlParser.CreateSequenceStatementContext;
import it.twenfir.sqlparser.SqlParser.CreateTableStatementContext;
import it.twenfir.sqlparser.SqlParser.CurrentClauseContext;
import it.twenfir.sqlparser.SqlParser.DataClauseContext;
import it.twenfir.sqlparser.SqlParser.DeclareCursorStatementContext;
import it.twenfir.sqlparser.SqlParser.DeclareTempTableStatementContext;
import it.twenfir.sqlparser.SqlParser.DeleteStatementContext;
import it.twenfir.sqlparser.SqlParser.DisconnectStatementContext;
import it.twenfir.sqlparser.SqlParser.DropAliasStatementContext;
import it.twenfir.sqlparser.SqlParser.DropIndexStatementContext;
import it.twenfir.sqlparser.SqlParser.DropTableStatementContext;
import it.twenfir.sqlparser.SqlParser.ExecuteStatementContext;
import it.twenfir.sqlparser.SqlParser.ExprListContext;
import it.twenfir.sqlparser.SqlParser.ExpressionContext;
import it.twenfir.sqlparser.SqlParser.FactorContext;
import it.twenfir.sqlparser.SqlParser.FetchStatementContext;
import it.twenfir.sqlparser.SqlParser.ForClauseContext;
import it.twenfir.sqlparser.SqlParser.FromClauseContext;
import it.twenfir.sqlparser.SqlParser.FunctionCallContext;
import it.twenfir.sqlparser.SqlParser.FunctionContext;
import it.twenfir.sqlparser.SqlParser.GetDiagnosticsStatementContext;
import it.twenfir.sqlparser.SqlParser.IndexContext;
import it.twenfir.sqlparser.SqlParser.IndicatorContext;
import it.twenfir.sqlparser.SqlParser.InputParameterContext;
import it.twenfir.sqlparser.SqlParser.InsertStatementContext;
import it.twenfir.sqlparser.SqlParser.IntoClauseContext;
import it.twenfir.sqlparser.SqlParser.IsolationClauseContext;
import it.twenfir.sqlparser.SqlParser.LabelStatementContext;
import it.twenfir.sqlparser.SqlParser.LocalTableDefinitionContext;
import it.twenfir.sqlparser.SqlParser.MediaClauseContext;
import it.twenfir.sqlparser.SqlParser.MemoryClauseContext;
import it.twenfir.sqlparser.SqlParser.OpenStatementContext;
import it.twenfir.sqlparser.SqlParser.OptionClauseContext;
import it.twenfir.sqlparser.SqlParser.OrReplaceClauseContext;
import it.twenfir.sqlparser.SqlParser.OrderByClauseContext;
import it.twenfir.sqlparser.SqlParser.OutputParameterContext;
import it.twenfir.sqlparser.SqlParser.ParameterContext;
import it.twenfir.sqlparser.SqlParser.PrepareStatementContext;
import it.twenfir.sqlparser.SqlParser.QualifiedNameContext;
import it.twenfir.sqlparser.SqlParser.RecordFormatClauseContext;
import it.twenfir.sqlparser.SqlParser.SelectColumnContext;
import it.twenfir.sqlparser.SqlParser.SelectExpressionContext;
import it.twenfir.sqlparser.SqlParser.SelectStatementContext;
import it.twenfir.sqlparser.SqlParser.SequenceContext;
import it.twenfir.sqlparser.SqlParser.SetOptionStatementContext;
import it.twenfir.sqlparser.SqlParser.SetStatementContext;
import it.twenfir.sqlparser.SqlParser.SetTargetContext;
import it.twenfir.sqlparser.SqlParser.SimpleInputParameterContext;
import it.twenfir.sqlparser.SqlParser.SimpleOutputParameterContext;
import it.twenfir.sqlparser.SqlParser.SimpleSelectContext;
import it.twenfir.sqlparser.SqlParser.StatementContext;
import it.twenfir.sqlparser.SqlParser.TableContext;
import it.twenfir.sqlparser.SqlParser.TermContext;
import it.twenfir.sqlparser.SqlParser.TruncateStatementContext;
import it.twenfir.sqlparser.SqlParser.UpdateClauseContext;
import it.twenfir.sqlparser.SqlParser.UpdateStatementContext;
import it.twenfir.sqlparser.SqlParser.UsingClauseContext;
import it.twenfir.sqlparser.SqlParser.ValuesStatementContext;
import it.twenfir.sqlparser.SqlParser.WhereClauseContext;
import it.twenfir.sqlparser.ast.AlterTableStatement;
import it.twenfir.sqlparser.ast.BinaryOp;
import it.twenfir.sqlparser.ast.CloseStatement;
import it.twenfir.sqlparser.ast.ColumnDefinition;
import it.twenfir.sqlparser.ast.ColumnExpression;
import it.twenfir.sqlparser.ast.ColumnLabel;
import it.twenfir.sqlparser.ast.CombinedInputParameter;
import it.twenfir.sqlparser.ast.CombinedOutputParameter;
import it.twenfir.sqlparser.ast.CommitStatement;
import it.twenfir.sqlparser.ast.ConnectStatement;
import it.twenfir.sqlparser.ast.CreateIndexStatement;
import it.twenfir.sqlparser.ast.CreateSequenceStatement;
import it.twenfir.sqlparser.ast.CreateTableStatement;
import it.twenfir.sqlparser.ast.CurrentClause;
import it.twenfir.sqlparser.ast.DataClause;
import it.twenfir.sqlparser.ast.DeclareCursorStatement;
import it.twenfir.sqlparser.ast.DeclareTempTableStatement;
import it.twenfir.sqlparser.ast.DeleteStatement;
import it.twenfir.sqlparser.ast.DisconnectStatement;
import it.twenfir.sqlparser.ast.DropAliasStatement;
import it.twenfir.sqlparser.ast.DropIndexStatement;
import it.twenfir.sqlparser.ast.DropTableStatement;
import it.twenfir.sqlparser.ast.ExecuteStatement;
import it.twenfir.sqlparser.ast.ExprList;
import it.twenfir.sqlparser.ast.Expression;
import it.twenfir.sqlparser.ast.Factor;
import it.twenfir.sqlparser.ast.FetchStatement;
import it.twenfir.sqlparser.ast.ForClause;
import it.twenfir.sqlparser.ast.FromClause;
import it.twenfir.sqlparser.ast.Function;
import it.twenfir.sqlparser.ast.FunctionCall;
import it.twenfir.sqlparser.ast.GetDiagnosticsStatement;
import it.twenfir.sqlparser.ast.Index;
import it.twenfir.sqlparser.ast.InsertStatement;
import it.twenfir.sqlparser.ast.IntoClause;
import it.twenfir.sqlparser.ast.IsolationClause;
import it.twenfir.sqlparser.ast.LabelStatement;
import it.twenfir.sqlparser.ast.LocalTableDefinition;
import it.twenfir.sqlparser.ast.MediaClause;
import it.twenfir.sqlparser.ast.MemoryClause;
import it.twenfir.sqlparser.ast.OpenStatement;
import it.twenfir.sqlparser.ast.OptionClause;
import it.twenfir.sqlparser.ast.OrReplaceClause;
import it.twenfir.sqlparser.ast.OrderByClause;
import it.twenfir.sqlparser.ast.Parameter;
import it.twenfir.sqlparser.ast.PrepareStatement;
import it.twenfir.sqlparser.ast.RecordFormatClause;
import it.twenfir.sqlparser.ast.SelectColumn;
import it.twenfir.sqlparser.ast.SelectExpression;
import it.twenfir.sqlparser.ast.SelectStatement;
import it.twenfir.sqlparser.ast.Sequence;
import it.twenfir.sqlparser.ast.SetOptionStatement;
import it.twenfir.sqlparser.ast.SetStatement;
import it.twenfir.sqlparser.ast.SetTarget;
import it.twenfir.sqlparser.ast.SimpleInputParameter;
import it.twenfir.sqlparser.ast.SimpleOutputParameter;
import it.twenfir.sqlparser.ast.SimpleSelect;
import it.twenfir.sqlparser.ast.Statement;
import it.twenfir.sqlparser.ast.Table;
import it.twenfir.sqlparser.ast.Term;
import it.twenfir.sqlparser.ast.TruncateStatement;
import it.twenfir.sqlparser.ast.UnhandledNode;
import it.twenfir.sqlparser.ast.UpdateClause;
import it.twenfir.sqlparser.ast.UpdateStatement;
import it.twenfir.sqlparser.ast.UsingClause;
import it.twenfir.sqlparser.ast.ValuesStatement;
import it.twenfir.sqlparser.ast.WhereClause;

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
	
	private <T extends AstNode> T add(ParserRuleContext ctx, java.util.function.Function<Location, T> make) {
		Location location = AstHelper.location(ctx);
		T node = make.apply(location);
		if ( ! ( node instanceof UnhandledNode ) ) {
			AstHelper.visitChildren(this, ctx, node);
		}
		return node;
	}
	
	@SuppressWarnings("unused")
	private Node add(ParserRuleContext ctx) {
		return add(ctx, l -> new Node(l));
	}

	private String[] extractQualifiedName(QualifiedNameContext ctx) {
		String name = ctx.name.getText();
		String schema = ctx.schema != null ? ctx.schema.getText() : null;
		String library = ctx.library != null ? ctx.library.getText() : null;
		return new String[] { name, schema, library };
	}
	
	@Override
	public AlterTableStatement visitAlterTableStatement(AlterTableStatementContext ctx) {
		return add(ctx, l -> new AlterTableStatement(l));
	}

	@Override
	public BinaryOp visitBinaryOp(BinaryOpContext ctx) {
		return add(ctx, l -> new BinaryOp(l));
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
	public ColumnDefinition visitColumnDefinition(ColumnDefinitionContext ctx) {
		return add(ctx, l -> new ColumnDefinition(l));
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
	public ColumnLabel visitColumnLabel(ColumnLabelContext ctx) {
		return add(ctx, l -> new ColumnLabel(l));
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
	public CommitStatement visitCommitStatement(CommitStatementContext ctx) {
		return add(ctx, l -> new CommitStatement(l));
	}

	@Override
	public ConnectStatement visitConnectStatement(ConnectStatementContext ctx) {
		return add(ctx, l -> new ConnectStatement(l));
	}
	
	@Override
	public CreateIndexStatement visitCreateIndexStatement(CreateIndexStatementContext ctx) {
		Location location = AstHelper.location(ctx);
		CreateIndexStatement node = new CreateIndexStatement(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public CreateSequenceStatement visitCreateSequenceStatement(CreateSequenceStatementContext ctx) {
		return add(ctx, l -> new CreateSequenceStatement(l));
	}

	@Override
	public CreateTableStatement visitCreateTableStatement(CreateTableStatementContext ctx) {
		Location location = AstHelper.location(ctx);
		CreateTableStatement node = new CreateTableStatement(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public CurrentClause visitCurrentClause(CurrentClauseContext ctx) {
		return add(ctx, l -> new CurrentClause(l));
	}

	@Override
	public DataClause visitDataClause(DataClauseContext ctx) {
		boolean data = ctx.NO() == null && ctx.ONLY() == null;
		return add(ctx, l -> new DataClause(l, data));
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
	public DisconnectStatement visitDisconnectStatement(DisconnectStatementContext ctx) {
		return add(ctx, l -> new DisconnectStatement(l));
	}
	
	@Override
	public DropAliasStatement visitDropAliasStatement(DropAliasStatementContext ctx) {
		return add(ctx, l -> new DropAliasStatement(l));
	}
	
	@Override
	public DropIndexStatement visitDropIndexStatement(DropIndexStatementContext ctx) {
		return add(ctx, l -> new DropIndexStatement(l));
	}
	
	@Override
	public DropTableStatement visitDropTableStatement(DropTableStatementContext ctx) {
		return add(ctx, l -> new DropTableStatement(l));
	}
	
	@Override
	public ExecuteStatement visitExecuteStatement(ExecuteStatementContext ctx) {
		Location location = AstHelper.location(ctx);
		String name = null;
		if ( ctx.identifier() != null ) {
			name = ctx.identifier().getText();
		}
		ExecuteStatement node = new ExecuteStatement(location, name);
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
	public ForClause visitForClause(ForClauseContext ctx) {
		Integer count = ctx.INTEGER() != null ? Integer.decode(ctx.INTEGER().getText()) : null;
		return add(ctx, l -> new ForClause(l, count));
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
	public GetDiagnosticsStatement visitGetDiagnosticsStatement(GetDiagnosticsStatementContext ctx) {
		return add(ctx, l -> new GetDiagnosticsStatement(l));
	}
	
	@Override
	public Index visitIndex(IndexContext ctx) {
		String[] identifiers = extractQualifiedName(ctx.qualifiedName());
		return add(ctx, l -> new Index(l, identifiers));
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
	public IsolationClause visitIsolationClause(IsolationClauseContext ctx) {
		Location location = AstHelper.location(ctx);
		IsolationClause node = new IsolationClause(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public LabelStatement visitLabelStatement(LabelStatementContext ctx) {
		return add(ctx, l -> new LabelStatement(l));
	}
	
	@Override
	public LocalTableDefinition visitLocalTableDefinition(LocalTableDefinitionContext ctx) {
		Location location = AstHelper.location(ctx);
		LocalTableDefinition node = new LocalTableDefinition(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public MediaClause visitMediaClause(MediaClauseContext ctx) {
		return add(ctx, l -> new MediaClause(l));
	}
	
	@Override
	public MemoryClause visitMemoryClause(MemoryClauseContext ctx) {
		return add(ctx, l -> new MemoryClause(l));
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
	public OrderByClause visitOrderByClause(OrderByClauseContext ctx) {
		return add(ctx, l -> new OrderByClause(l));
	}

	@Override
	public OrReplaceClause visitOrReplaceClause(OrReplaceClauseContext ctx) {
		return add(ctx, l -> new OrReplaceClause(l));
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
	public RecordFormatClause visitRecordFormatClause(RecordFormatClauseContext ctx) {
		return add(ctx, l -> new RecordFormatClause(l));
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
	public Sequence visitSequence(SequenceContext ctx) {
		String[] identifiers = extractQualifiedName(ctx.qualifiedName());
		return add(ctx, l -> new Sequence(l, identifiers));
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
		return add(ctx, l -> new SetStatement(l));
	}

	@Override
	public SetTarget visitSetTarget(SetTargetContext ctx) {
		String name = ctx.identifier() != null ? ctx.identifier().getText() : null;
		return add(ctx, l -> new SetTarget(l, name));
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
	public Table visitTable(TableContext ctx) {
		String[] identifiers = extractQualifiedName(ctx.qualifiedName());
		return add(ctx, l -> new Table(l, identifiers));
	}
	
	@Override
	public Term visitTerm(TermContext ctx) {
		Location location = AstHelper.location(ctx);
		Term node = new Term(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public TruncateStatement visitTruncateStatement(TruncateStatementContext ctx) {
		return add(ctx, l -> new TruncateStatement(l));
	}
	
	@Override
	public UpdateStatement visitUpdateStatement(UpdateStatementContext ctx) {
		Location location = AstHelper.location(ctx);
		UpdateStatement node = new UpdateStatement(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public UpdateClause visitUpdateClause(UpdateClauseContext ctx) {
		return add(ctx, l -> new UpdateClause(l));
	}

	@Override
	public UsingClause visitUsingClause(UsingClauseContext ctx) {
		return add(ctx, l -> new UsingClause(l));
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

}
