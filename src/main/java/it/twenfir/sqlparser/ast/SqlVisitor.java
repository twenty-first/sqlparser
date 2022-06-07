package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstVisitor;

public interface SqlVisitor<ValueT> extends AstVisitor<ValueT> {
    ValueT visit(CombinedInputParameter node);
    ValueT visit(CombinedOutputParameter node);
    ValueT visit(Expression node);
    ValueT visit(ExprList node);
    ValueT visit(Factor node);
    ValueT visit(Function node);
    ValueT visit(FunctionCall node);
    ValueT visit(Indicator node);
    ValueT visit(InputParameter node);
    ValueT visit(IntoClause node);
    ValueT visit(LocalTableDefinition node);
    ValueT visit(OptionClause node);
    ValueT visit(OptionName node);
    ValueT visit(OptionValue node);
    ValueT visit(OutputParameter node);
    ValueT visit(Parameter node);
    ValueT visit(SelectColumn node);
    ValueT visit(SelectExpression node);
    ValueT visit(SelectStatement node);
    ValueT visit(SetOptionStatement node);
    ValueT visit(SimpleSelect node);
    ValueT visit(Statement node);
    ValueT visit(Term node);
    ValueT visit(WhereClause node);
}
