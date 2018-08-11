package online.pizzacrust.jua.compiler;

import online.pizzacrust.jua.antlr.JuaParser;

public class MethodInvocation {

    private final String callingClass;
    private final String callingMethod;
    private final String parameters;

    public MethodInvocation(String callingClass, String callingMethod, String parameters) {
        this.callingClass = callingClass;
        this.callingMethod = callingMethod;
        this.parameters = parameters;
    }

    public static MethodInvocation from(JuaParser.ExpressionContext expressionContext) {
        if (expressionContext.invocation() != null) {
            String parameters = expressionContext.invocation().args().toString();
            return new MethodInvocation(null, expressionContext.invocation().Q_NAME().toString(),
                    parameters);
        } else {
            String parameters = expressionContext.staticInvocation().args().getText();
            return new MethodInvocation(expressionContext.staticInvocation().Q_NAME(0).toString(),
                    expressionContext.staticInvocation().Q_NAME(1).toString(), parameters);
        }
    }

    public static MethodInvocation from(JuaParser.BodyExpressionContext expressionContext) {
        System.out.println(expressionContext.getText());
        if (expressionContext.invocation() != null) {
            String parameters = expressionContext.invocation().args().getText();
            return new MethodInvocation(null, expressionContext.invocation().Q_NAME().toString(),
                    parameters);
        } else {
            String parameters = expressionContext.staticInvocation().args().getText();
            return new MethodInvocation(expressionContext.staticInvocation().Q_NAME(0).toString(),
                    expressionContext.staticInvocation().Q_NAME(1).toString(), parameters);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (callingClass != null) stringBuilder.append(callingClass).append(".");
        stringBuilder.append(callingMethod).append("(").append(parameters).append(")\n");
        return stringBuilder.toString();
    }

}
