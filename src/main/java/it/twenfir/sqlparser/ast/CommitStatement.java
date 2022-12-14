package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class CommitStatement extends Statement {

    public CommitStatement(Location location) {
        super(location);
    }
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitCommitStatement(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
