package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.BaseAstVisitor;

public abstract class SqlBaseVisitor<ValueT> extends BaseAstVisitor<ValueT> implements SqlVisitor<ValueT> {

    @Override
	public ValueT visitAlterTableStatement(AlterTableStatement node) {
        return visitChildren(node);
	}

	@Override
    public ValueT visitCloseStatement(CloseStatement node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitCombinedInputParameter(CombinedInputParameter node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitCombinedOutputParameter(CombinedOutputParameter node) {
        return visitChildren(node);
    }

    @Override
	public ValueT visitColumnExpression(ColumnExpression node) {
        return visitChildren(node);
	}

    @Override
	public ValueT visitCommitStatement(CommitStatement node) {
        return visitChildren(node);
	}

	@Override
	public ValueT visitCreateIndexStatement(CreateIndexStatement node) {
        return visitChildren(node);
	}

	@Override
    public ValueT visitCreateTableStatement(CreateTableStatement node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitDeclareCursorStatement(DeclareCursorStatement node) {
        return visitChildren(node);
    }

	@Override
	public ValueT visitDeclareTempTableStatement(DeclareTempTableStatement node) {
        return visitChildren(node);
	}

	@Override
	public ValueT visitDeleteStatement(DeleteStatement node) {
        return visitChildren(node);
	}

	@Override
	public ValueT visitExecuteStatement(ExecuteStatement node) {
        return visitChildren(node);
	}
    
    @Override
    public ValueT visitExpression(Expression node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitExprList(ExprList node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitFactor(Factor node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitFetchStatement(FetchStatement node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitFromClause(FromClause node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitFunction(Function node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitFunctionCall(FunctionCall node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitInputParameter(InputParameter node) {
        return visitChildren(node);
    }

    @Override
	public ValueT visitInsertStatement(InsertStatement node) {
        return visitChildren(node);
	}

	@Override
    public ValueT visitIntoClause(IntoClause node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitLocalTableDefinition(LocalTableDefinition node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitOpenStatement(OpenStatement node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitOptionClause(OptionClause node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitOrReplaceClause(OrReplaceClause node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitOutputParameter(OutputParameter node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitParameter(Parameter node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitPrepareStatement(PrepareStatement node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitSelectColumn(SelectColumn node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitSelectExpression(SelectExpression node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitSelectStatement(SelectStatement node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitSetOptionStatement(SetOptionStatement node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitSetStatement(SetStatement node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitSimpleInputParameter(SimpleInputParameter node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitSimpleOutputParameter(SimpleOutputParameter node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitSimpleSelect(SimpleSelect node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitStatement(Statement node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitTerm(Term node) {
        return visitChildren(node);
    }

	@Override
	public ValueT visitUpdateStatement(UpdateStatement node) {
        return visitChildren(node);
	}

    @Override
    public ValueT visitUsingClause(UsingClause node) {
        return visitChildren(node);
    }

	@Override
	public ValueT visitValuesStatement(ValuesStatement node) {
        return visitChildren(node);
	}

    @Override
    public ValueT visitWhereClause(WhereClause node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitWithUrClause(WithUrClause node) {
        return visitChildren(node);
    }
    
}
