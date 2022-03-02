package io.github.twentyfirst.sqlparser.ast;

import java.util.Iterator;

import org.antlr.v4.runtime.ParserRuleContext;

public class Statement extends AstNode {

	public Statement(ParserRuleContext context) {
		super(context);
	}
	
	public void addIntoClause(IntoClause ic) {
		addChild(ic);
	}
	
	public Iterator<IntoClause> getIntoClauses() {
		return new ChildrenIterator<IntoClause>(getChildren(), IntoClause.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
    	return visitor.visit(this);
    }

}
