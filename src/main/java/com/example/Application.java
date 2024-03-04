package com.example;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotAccess;
import org.graalvm.polyglot.Value;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Application {
    private static final String PYTHON_LANG_ID = "python";

    public static void main(String[] args) {
        String pythonScriptPath = ""; // Update this with the full path to your Python script

        execPythonFileByPath(pythonScriptPath);
    }

    private static void execPythonFileByPath(String pythonScriptPath) {
        try (Context context = Context.newBuilder()
                .option("engine.WarnInterpreterOnly", "false")
                .allowAllAccess(true)
                .build()) {
            Path path = Paths.get(pythonScriptPath);

            if (Files.exists(path)) {
                Value value = context.eval(PYTHON_LANG_ID, "exec(open('" + pythonScriptPath + "').read())");
                if (value.isString()) {
                    String valueString = value.asString();
                    System.out.println("valueString = " + valueString);
                } else {
                    System.out.println("value = " + value);
                }

            } else {
                System.out.println("File not found: " + pythonScriptPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
