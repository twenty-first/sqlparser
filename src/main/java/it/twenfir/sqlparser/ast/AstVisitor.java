package it.twenfir.sqlparser.ast;

public interface AstVisitor<ValueT> {

	ValueT defaultValue();
	ValueT aggregate(ValueT accumulator, ValueT value);

	ValueT visit(AstNode node);
	ValueT visitChildren(AstNode node);

	ValueT visit(IntoClause node);
	ValueT visit(Statement node);
}
