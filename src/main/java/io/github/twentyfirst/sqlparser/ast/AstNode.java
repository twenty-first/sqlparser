package io.github.twentyfirst.sqlparser.ast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

public abstract class AstNode {
	
    private ParserRuleContext context;
    private List<AstNode> children = new ArrayList<>();

    public AstNode(ParserRuleContext context) {
        this.context = context;
    }

    public ParserRuleContext getContext() {
    	return context;
    }

    protected void addChild(AstNode child) {
    	if ( child != null ) {
        	children.add(child);
    	}
    }
    
    protected <N extends AstNode> void addChildren(Collection<N> children) {
    	if ( children != null ) {
        	this.children.addAll(children);
    	}
    }
    
    public Iterator<AstNode> getChildren() {
    	return children.iterator();
    }

	public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
    	return visitor.visit(this);
    }

    public String getType() {
        return this.getClass().getSimpleName();
    }
}
