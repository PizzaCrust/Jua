package online.pizzacrust.jua.compiler;

import java.util.List;

//todo
public class JuaClass {

    private final List<JuaFunction> functionList;
    private final String name;

    public JuaClass(List<JuaFunction> functionList, String name) {
        this.functionList = functionList;
        this.name = name;
    }



}
