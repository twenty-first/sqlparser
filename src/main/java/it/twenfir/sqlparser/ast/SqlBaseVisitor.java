package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.BaseAstVisitor;

public abstract class SqlBaseVisitor<ValueT> extends BaseAstVisitor<ValueT> implements SqlVisitor<ValueT> {

    @Override
    public ValueT visit(Statement node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visit(IntoClause node) {
        return visitChildren(node);
    }
    
}
