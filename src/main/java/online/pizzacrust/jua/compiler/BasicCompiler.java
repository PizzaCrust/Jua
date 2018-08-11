package online.pizzacrust.jua.compiler;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import online.pizzacrust.jua.antlr.JuaLexer;
import online.pizzacrust.jua.antlr.JuaParser;

public class BasicCompiler {

    public static void main(String... args) throws Exception {
        File file = new File(args[0]);
        ANTLRInputStream charStream = new ANTLRInputStream(new FileReader(file));
        JuaLexer lexer = new JuaLexer(charStream);
        JuaParser parser = new JuaParser(new CommonTokenStream(lexer));
        JuaClass juaClass = JuaClass.from(parser.class_declare());
        System.out.println(juaClass);
    }

}
