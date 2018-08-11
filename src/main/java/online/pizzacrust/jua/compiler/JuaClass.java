package online.pizzacrust.jua.compiler;

import java.util.ArrayList;
import java.util.List;

import online.pizzacrust.jua.antlr.JuaParser;

//todo
public class JuaClass {

    private final List<JuaFunction> functionList;
    private final String name;

    public JuaClass(List<JuaFunction> functionList, String name) {
        this.functionList = functionList;
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append(" = ").append("{}").append("\n");
        stringBuilder.append(name).append(".__index = ").append(name).append("\n");
        stringBuilder.append("class = ").append(name).append("\n");
        functionList.forEach((f) -> stringBuilder.append(f.toString()));
        stringBuilder.append("return ").append(name).append("\n");
        return stringBuilder.toString();
    }

    public static JuaClass from(JuaParser.Class_declareContext declaration) {
        List<JuaFunction> functionList = new ArrayList<>();
        for (JuaParser.ClassBodyExprContext classBodyExprContext :
                declaration.classBody().classBodyExpr()) {
            if (classBodyExprContext.constructor() != null) {
                functionList.add(JuaFunction.fromConstructor(classBodyExprContext.constructor()));
                continue;
            }
            if (classBodyExprContext.function() != null) {
                functionList.add(JuaFunction.from(classBodyExprContext.function()));
            }
        }
        return new JuaClass(functionList, declaration.Q_NAME(0).toString());
    }

}
