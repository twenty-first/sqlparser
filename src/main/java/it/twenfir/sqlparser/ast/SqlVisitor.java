package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstVisitor;

public interface SqlVisitor<ValueT> extends AstVisitor<ValueT> {
    ValueT visit(CombinedInputParameter node);
    ValueT visit(CombinedOutputParameter node);
    ValueT visit(Expression node);
    ValueT visit(Factor node);
    ValueT visit(Indicator node);
    ValueT visit(InputParameter node);
    ValueT visit(IntoClause node);
    ValueT visit(OutputParameter node);
    ValueT visit(Parameter node);
    ValueT visit(SelectExpression node);
    ValueT visit(SelectStatement node);
    ValueT visit(SimpleSelect node);
    ValueT visit(Statement node);
    ValueT visit(Term node);
    ValueT visit(WhereClause node);
}
