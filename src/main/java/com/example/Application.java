package com.example;

import com.example.utils.PythonHelper;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static com.example.utils.ProcessHelper.*;
import static com.example.utils.PythonHelper.*;
import static java.lang.StringTemplate.STR;


public class Application {


    public static void main(String[] args) {
        var rootPath = getProjectRoot();
        System.out.println(STR."Root directory of the project: \{rootPath}");

        prepareToExecutePythonFile(rootPath);

//        execPythonFileByPath();

        var pythonPath=STR."\{rootPath}/python/demo.py";
        try (Context context = Context.newBuilder()
                .allowAllAccess(true)
                .build()) {

            Path path = Paths.get(pythonPath);

            if (Files.exists(path)) {
                var envPath = STR."\{rootPath}/env";
                var pythonSourceCommands = List.of("source",STR."\{envPath}/bin/activate");
                executePythonProcess(pythonSourceCommands);

//                var envPath = STR."\{rootPath}/env";
                context.getBindings(PYTHON_LANG_ID)
                        .putMember("sys", context.eval(PYTHON_LANG_ID,
                                STR."import sys\nsys.path.append('\{envPath}')"));
//                // Execute Python script
                Value result = context.eval(PYTHON_LANG_ID, STR."\{rootPath}/env/bin/python3 \{pythonPath}");
//                Value result = context.eval(PYTHON_LANG_ID, Files.readString(path));

                // Retrieve output value from Python execution
                String outputValue = result.getMember("get_post").execute().asString();
                System.out.println("Output from Python: " + outputValue);
            } else {
                System.out.println("File not found: " + pythonPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void prepareToExecutePythonFile(String rootPath) {
        System.out.println("Start preparation before executing python... ");
        var pythonVersionCommands = List.of(PYTHON_LANG_ID, "--version");
        executePythonProcess(pythonVersionCommands);

        var envPath = STR."\{rootPath}/env";

        var pythonEnvCommands = List.of("python3", "-m", "venv", envPath);
        executePythonProcess(pythonEnvCommands);

        var pythonSourceCommands = List.of("source",STR."\{rootPath}/env/bin/activate");
        executePythonProcess(pythonSourceCommands);

        var pipUpdateCommands = List.of(STR."\{envPath}/bin/pip", "install", "--upgrade", "pip");
        executePythonProcess(pipUpdateCommands);

        var pipInstallRequirementsCommands = List.of(
                STR."\{envPath}/bin/pip", "install", "-r", STR."\{rootPath}/python/requirements.txt");
        executePythonProcess(pipInstallRequirementsCommands);

        var pipFreezeCommands = List.of(STR."\{envPath}/bin/pip", "freeze");
        executePythonProcess(pipFreezeCommands);
    }

//    public static void main(String[] args) {
//        // Set PYTHONPATH to point to the directory containing the Python script and its dependencies
//        String pythonScriptPath = "src/main/java/com/example/python/test.py";
//        String pythonPath = "env/bin/python3";
//        String pythonPathCommand = "PYTHONPATH=" + pythonPath;
//
//        // Execute Python script
//        execPythonFileByPath(pythonScriptPath, pythonPathCommand);
//    }

}
