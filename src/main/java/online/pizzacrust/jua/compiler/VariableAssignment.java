package online.pizzacrust.jua.compiler;

import online.pizzacrust.jua.antlr.JuaParser;

public class VariableAssignment {

    private final String variableName;
    private final JuaParser.ExpressionContext value;

    public VariableAssignment(String variableName, JuaParser.ExpressionContext value) {
        this.variableName = variableName;
        this.value = value;
    }

    public static String primitiveToString(JuaParser.PrimitiveContext context) {
        if (context.Q_NAME() != null) return context.Q_NAME().toString();
        if (context.DOUBLE() != null) return context.DOUBLE().toString();
        if (context.INTEGER() != null) return context.INTEGER().toString();
        if (context.STRING() != null) return context.STRING().toString();
        throw new RuntimeException();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (value.invocation() != null || value.staticInvocation() != null) {
            builder.append(MethodInvocation.from(value));
        } else if (value == null) {
            builder.append("nil");
        } else {
            builder.append(primitiveToString(value.primitive()));
        }
        return variableName + " = " + builder.toString().trim() + "\n";
    }


}