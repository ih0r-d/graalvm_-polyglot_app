package com.example.utils;

import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@UtilityClass
public class ProcessHelper {

    private static final List<String> ROOT_PWD = List.of("sh", "-c", "pwd");

    public static void executePythonProcess(List<String> commands) {
        final ProcessBuilder pb = new ProcessBuilder(commands);
        pb.inheritIO();
        try {
            Process process = pb.start();
            process.waitFor();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(e.getMessage());
        }
    }

    public static String getProjectRoot() {
        ProcessBuilder pb = new ProcessBuilder();
        pb.command(ROOT_PWD);
        pb.directory(new File("."));
        String output = "";
        try {
            Process process = pb.start();
            // Read the output of the command
            output = readProcessOutput(process);

            // Wait for the process to finish
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Failed to retrieve project root directory.");
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(e.getMessage());
        }

        // Trim any leading/trailing whitespace and return the result
        return output.trim();
    }

    public static String readProcessOutput(Process process) throws IOException {
        StringBuilder output = new StringBuilder();
        try (var reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }
        }
        return output.toString();
    }

}
