package online.pizzacrust.jua.compiler;

import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;
import online.pizzacrust.jua.antlr.JuaParser;

public class JuaFunction {

    private final List<Object> body;
    private final List<String> parameterNames;
    private final String name;

    public JuaFunction(String name, List<String> parameterNames, List<Object> body) {
        this.body = body;
        this.name = name;
        this.parameterNames = parameterNames;
    }

    public static JuaFunction fromConstructor(JuaParser.ConstructorContext functionContext) {
        String name = "class.new";
        List<String> parameters = new ArrayList<>();
        for (TerminalNode terminalNode : functionContext.functionArgs().Q_NAME()) {
            parameters.add(terminalNode.toString());
        }
        List<Object> body = new ArrayList<>();
        for (JuaParser.BodyExpressionContext bodyExpressionContext :
                functionContext.constructorBody().body().bodyExpression()) {
            if (bodyExpressionContext.varDeclare() != null) {
                Pair<VariableDeclaration, VariableAssignment> pair =
                        VariableDeclaration.from(bodyExpressionContext.varDeclare());
                body.add(pair.a);
                body.add(pair.b);
            } else if (bodyExpressionContext.varAssign() != null) {
                body.add(new VariableAssignment(bodyExpressionContext.varAssign().Q_NAME().toString(), bodyExpressionContext.varAssign().expression()));
            }
            if (bodyExpressionContext.invocation() != null || bodyExpressionContext.staticInvocation() != null) {
                body.add(MethodInvocation.from(bodyExpressionContext));
            }
        }
        body.add(new MethodInvocation("super",
                "new",functionContext.constructorBody().superInvocation().args().getText()));
        return new JuaFunction(name, parameters, body);
    }

    public static JuaFunction from(JuaParser.FunctionContext functionContext) {
        String name = functionContext.Q_NAME().toString();
        List<String> parameters = new ArrayList<>();
        for (TerminalNode terminalNode : functionContext.functionArgs().Q_NAME()) {
            parameters.add(terminalNode.toString());
        }
        List<Object> body = new ArrayList<>();
        for (JuaParser.BodyExpressionContext bodyExpressionContext : functionContext.body().bodyExpression()) {
            if (bodyExpressionContext.varDeclare() != null) {
                Pair<VariableDeclaration, VariableAssignment> pair =
                        VariableDeclaration.from(bodyExpressionContext.varDeclare());
                body.add(pair.a);
                body.add(pair.b);
            } else if (bodyExpressionContext.varAssign() != null) {
                body.add(new VariableAssignment(bodyExpressionContext.varAssign().Q_NAME().toString(), bodyExpressionContext.varAssign().expression()));
            }
            if (bodyExpressionContext.invocation() != null || bodyExpressionContext.staticInvocation() != null) {
                body.add(MethodInvocation.from(bodyExpressionContext));
            }
        }
        return new JuaFunction(name, parameters, body);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("function ").append(name).append("(");
        for (int i = 0; i < parameterNames.size(); i++) {
            if ((parameterNames.size() - 1) == i) {
                stringBuilder.append(parameterNames.get(i));
            } else {
                stringBuilder.append(parameterNames.get(i)).append(",");
            }
        }
        stringBuilder.append(")\n");
        body.forEach((o) -> stringBuilder.append(o.toString()));
        stringBuilder.append("end\n");
        return stringBuilder.toString();
    }

}
