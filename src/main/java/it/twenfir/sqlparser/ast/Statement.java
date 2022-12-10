package it.twenfir.sqlparser.ast;

import java.util.Iterator;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.Location;

public abstract class Statement extends AstNode {

    protected Statement(Location location) {
        super(location);
    }
    
    public Iterator<InputParameter> getInputParameters() {
        return getDescendants(InputParameter.class);
    }
    
    public Iterator<OutputParameter> getOutputParameters() {
        return getDescendants(OutputParameter.class);
    }
}
