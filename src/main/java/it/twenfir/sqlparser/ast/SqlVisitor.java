package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstVisitor;

public interface SqlVisitor<ValueT> extends AstVisitor<ValueT> {
    ValueT visitCloseStatement(CloseStatement node);
    ValueT visitCombinedInputParameter(CombinedInputParameter node);
    ValueT visitCombinedOutputParameter(CombinedOutputParameter node);
    ValueT visitDeclareCursorStatement(DeclareCursorStatement node);
    ValueT visitExpression(Expression node);
    ValueT visitExprList(ExprList node);
    ValueT visitFactor(Factor node);
    ValueT visitFetchStatement(FetchStatement node);
    ValueT visitFunction(Function node);
    ValueT visitFunctionCall(FunctionCall node);
    ValueT visitIntoClause(IntoClause node);
    ValueT visitLocalTableDefinition(LocalTableDefinition node);
    ValueT visitOpenStatement(OpenStatement node);
    ValueT visitOptionClause(OptionClause node);
    ValueT visitParameter(Parameter node);
    ValueT visitPrepareStatement(PrepareStatement node);
    ValueT visitSelectColumn(SelectColumn node);
    ValueT visitSelectExpression(SelectExpression node);
    ValueT visitSelectStatement(SelectStatement node);
    ValueT visitSetStatement(SetStatement node);
    ValueT visitSetOptionStatement(SetOptionStatement node);
    ValueT visitSimpleSelect(SimpleSelect node);
    ValueT visitStatement(Statement node);
    ValueT visitTerm(Term node);
    ValueT visitUsingClause(UsingClause node);
    ValueT visitWhereClause(WhereClause node);
}
