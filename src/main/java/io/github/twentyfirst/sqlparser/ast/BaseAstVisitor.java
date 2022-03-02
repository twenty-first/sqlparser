package io.github.twentyfirst.sqlparser.ast;

import java.util.Iterator;

public abstract class BaseAstVisitor<ValueT> implements AstVisitor<ValueT> {


	public BaseAstVisitor() {
	}

	@Override
	public ValueT defaultValue() {
		return null;
	}

	@Override
	public ValueT aggregate(ValueT accumulator, ValueT value) {
		return value;
	}

	@Override
	public ValueT visit(AstNode node) {
		return node.accept(this);
	}

	@Override
	public ValueT visitChildren(AstNode node) {
		ValueT result = defaultValue();
		Iterator<AstNode> i = node.getChildren();
		while ( i.hasNext() ) {
			AstNode c = i.next();
			result = aggregate(result, c.accept(this));
		}
		return result;
	}

	@Override
	public ValueT visit(IntoClause node) {
		return visitChildren(node);
	}

	@Override
	public ValueT visit(Statement node) {
		return visitChildren(node);
	}

}
