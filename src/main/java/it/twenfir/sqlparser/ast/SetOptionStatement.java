package it.twenfir.sqlparser.ast;

import java.util.Iterator;

import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class SetOptionStatement extends Statement {

	public SetOptionStatement(Location location) {
		super(location);
	}
	
	public Iterator<OptionClause> getOptionClauses() {
		return getChildren(OptionClause.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitSetOptionStatement(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
