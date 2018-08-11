package online.pizzacrust.jua.compiler;

import org.antlr.v4.runtime.misc.Pair;

import online.pizzacrust.jua.antlr.JuaParser;

public class VariableDeclaration {

    private final String name;

    public VariableDeclaration(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "var " + name + " = nil\n";
    }

    public static Pair<VariableDeclaration, VariableAssignment> from(JuaParser.VarDeclareContext varDeclareContext) {
        if (varDeclareContext.expression() == null) {
            return new Pair<>(new VariableDeclaration(varDeclareContext.Q_NAME().toString()), null);
        }
        return new Pair<>(new VariableDeclaration(varDeclareContext.Q_NAME().toString()),
                new VariableAssignment(varDeclareContext.Q_NAME().toString(),
                        varDeclareContext.expression()));
    }

}
