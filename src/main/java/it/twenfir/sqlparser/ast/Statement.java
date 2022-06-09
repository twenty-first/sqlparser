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
	
	public Iterator<SetOptionStatement> getSetOptionStatements() {
		return getChildren(SetOptionStatement.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitStatement(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
