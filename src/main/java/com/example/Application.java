package com.example;
import java.nio.file.*;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

public class Application {
    private static final String PYTHON_LANG_ID = "python";

    public static void main(String[] args) {
        // Set PYTHONPATH to point to the directory containing the Python script and its dependencies
        String pythonScriptPath = "src/main/java/com/example/python/test.py";
        String pythonPath = "env/bin/python3";
        String pythonPathCommand = "PYTHONPATH=" + pythonPath;

        // Execute Python script
        execPythonFileByPath(pythonScriptPath, pythonPathCommand);
    }

    private static void execPythonFileByPath(String pythonScriptPath, String pythonPathCommand) {
        try (Context context = Context.newBuilder()
                .option("engine.WarnInterpreterOnly", "false")
                .allowAllAccess(true)
                .build()) {
            Path path = Paths.get(pythonScriptPath);

            if (Files.exists(path)) {
                // Execute Python script with specified PYTHONPATH
                Value value = context.eval(PYTHON_LANG_ID, pythonPathCommand + " python3 " + pythonScriptPath);
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
