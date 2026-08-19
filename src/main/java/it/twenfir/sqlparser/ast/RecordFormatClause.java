package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class RecordFormatClause extends AstNode {

	public RecordFormatClause(Location location) {
		super(location);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitRecordFormatClause(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
