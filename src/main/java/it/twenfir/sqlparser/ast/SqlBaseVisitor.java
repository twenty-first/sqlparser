package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.BaseAstVisitor;

public abstract class SqlBaseVisitor<ValueT> extends BaseAstVisitor<ValueT> implements SqlVisitor<ValueT> {

    @Override
    public ValueT visit(CombinedInputParameter node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visit(CombinedOutputParameter node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visit(Expression node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visit(Factor node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visit(Indicator node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visit(InputParameter node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visit(IntoClause node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visit(OutputParameter node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visit(Parameter node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visit(SelectExpression node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visit(SelectStatement node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visit(SimpleSelect node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visit(Statement node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visit(Term node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visit(WhereClause node) {
        return visitChildren(node);
    }
    
}
