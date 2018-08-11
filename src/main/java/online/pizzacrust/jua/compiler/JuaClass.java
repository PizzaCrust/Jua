package online.pizzacrust.jua.compiler;

import org.antlr.v4.runtime.misc.Pair;

import java.util.ArrayList;
import java.util.List;

import online.pizzacrust.jua.antlr.JuaParser;

//todo
public class JuaClass {

    private final List<JuaFunction> functionList;
    private final String name;
    private final List<Object> varSpace;

    public JuaClass(List<JuaFunction> functionList, String name, List<Object> varSpace) {
        this.functionList = functionList;
        this.name = name;
        this.varSpace = varSpace;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append(" = ").append("{}").append("\n");
        stringBuilder.append(name).append(".__index = ").append(name).append("\n");
        stringBuilder.append("class = ").append(name).append("\n");
        stringBuilder.append("--JUA VARSPACE\n");
        varSpace.forEach((s) -> {
            if (s != null) stringBuilder.append(s);
        });
        stringBuilder.append("--GENERATED CLASS\n");
        functionList.forEach((f) -> stringBuilder.append(f.toString()));
        stringBuilder.append("return ").append(name).append("\n");
        return stringBuilder.toString();
    }

    public static JuaClass from(JuaParser.Class_declareContext declaration) {
        List<JuaFunction> functionList = new ArrayList<>();
        List<Object> varSpace = new ArrayList<>();
        for (JuaParser.ClassBodyExprContext classBodyExprContext :
                declaration.classBody().classBodyExpr()) {
            if (classBodyExprContext.constructor() != null) {
                functionList.add(JuaFunction.fromConstructor(classBodyExprContext.constructor()));
                continue;
            }
            if (classBodyExprContext.function() != null) {
                functionList.add(JuaFunction.from(classBodyExprContext.function()));
            }
            if (classBodyExprContext.varDeclare() != null) {
                Pair<VariableDeclaration, VariableAssignment> pair =
                        VariableDeclaration.from(classBodyExprContext.varDeclare());
                varSpace.add(pair.a);
                varSpace.add(pair.b);
            }
        }
        return new JuaClass(functionList, declaration.Q_NAME(0).toString(), varSpace);
    }

}
