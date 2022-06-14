package it.twenfir.sqlparser.ast;

import java.util.Iterator;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class Statement extends AstNode {

	public Statement(Location location) {
		super(location);
	}
	
	public Iterator<CloseStatement> getCloseStatements() {
		return getChildren(CloseStatement.class);
	}
	
	public Iterator<DeclareCursorStatement> getDeclareCursorStatements() {
		return getChildren(DeclareCursorStatement.class);
	}
	
	public Iterator<FetchStatement> getFetchStatements() {
		return getChildren(FetchStatement.class);
	}
	
	public Iterator<OpenStatement> getOpenStatements() {
		return getChildren(OpenStatement.class);
	}
	
	public Iterator<PrepareStatement> getPrepareStatements() {
		return getChildren(PrepareStatement.class);
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
