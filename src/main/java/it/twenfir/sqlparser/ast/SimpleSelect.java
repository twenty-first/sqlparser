package it.twenfir.sqlparser.ast;

import java.util.Iterator;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class SimpleSelect extends AstNode {

	public SimpleSelect(Location location) {
		super(location);
	}
	
	public Iterator<SelectColumn> getSelectColumns() {
		return getChildren(SelectColumn.class);
	}
	
	public IntoClause getIntoClause() {
		return getChild(IntoClause.class);
	}
	
	public WhereClause getWhereClause() {
		return getChild(WhereClause.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
    	return visitor.visit(this);
    }

}
