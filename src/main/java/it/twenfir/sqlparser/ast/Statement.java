package it.twenfir.sqlparser.ast;

import java.util.Iterator;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class Statement extends AstNode {

	public Statement(Location location) {
		super(location);
	}
	
	public Iterator<SelectStatement> getSelectStatements() {
		return getChildren(SelectStatement.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
    	return visitor.visit(this);
    }

}
