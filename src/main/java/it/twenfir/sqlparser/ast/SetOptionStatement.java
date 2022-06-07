package it.twenfir.sqlparser.ast;

import java.util.Iterator;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class SetOptionStatement extends AstNode {

	public SetOptionStatement(Location location) {
		super(location);
	}
	
	public Iterator<OptionClause> getOptionClauses() {
		return getChildren(OptionClause.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
    	return visitor.visit(this);
    }

}
