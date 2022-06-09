package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstVisitor;

public interface SqlVisitor<ValueT> extends AstVisitor<ValueT> {
    ValueT visitCombinedInputParameter(CombinedInputParameter node);
    ValueT visitCombinedOutputParameter(CombinedOutputParameter node);
    ValueT visitExpression(Expression node);
    ValueT visitExprList(ExprList node);
    ValueT visitFactor(Factor node);
    ValueT visitFunction(Function node);
    ValueT visitFunctionCall(FunctionCall node);
    ValueT visitIndicator(Indicator node);
    ValueT visitInputParameter(InputParameter node);
    ValueT visitIntoClause(IntoClause node);
    ValueT visitLocalTableDefinition(LocalTableDefinition node);
    ValueT visitOptionClause(OptionClause node);
    ValueT visitOptionName(OptionName node);
    ValueT visitOptionValue(OptionValue node);
    ValueT visitOutputParameter(OutputParameter node);
    ValueT visitParameter(Parameter node);
    ValueT visitSelectColumn(SelectColumn node);
    ValueT visitSelectExpression(SelectExpression node);
    ValueT visitSelectStatement(SelectStatement node);
    ValueT visitSetOptionStatement(SetOptionStatement node);
    ValueT visitSimpleSelect(SimpleSelect node);
    ValueT visitStatement(Statement node);
    ValueT visitTerm(Term node);
    ValueT visitWhereClause(WhereClause node);
}
