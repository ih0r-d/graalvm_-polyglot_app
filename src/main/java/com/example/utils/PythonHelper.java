package com.example.utils;

import lombok.experimental.UtilityClass;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@UtilityClass
public class PythonHelper {

    public static final String PYTHON_LANG_ID = "python";

    public static void execPythonFileByPath(String pythonScriptPath) {
        try (Context context = Context.newBuilder()
                .option("engine.WarnInterpreterOnly", "false")
                .allowAllAccess(true)
                .build()) {
            Path path = Paths.get(pythonScriptPath);

            if (Files.exists(path)) {
//                context.getBindings(PYTHON_LANG_ID)
//                        .putMember("sys", context.eval(PYTHON_LANG_ID,
//                                "import sys\nsys.path.append('" + en + "')"));
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
