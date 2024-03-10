package com.example.utils;

import lombok.experimental.UtilityClass;

import java.io.IOException;

@UtilityClass
public class PythonHelper {
    private static void createVirtualEnvironment(String virtualEnvPath) throws IOException {
        // Create virtual environment
        ProcessBuilder pbCreateEnv = new ProcessBuilder("python", "-m", "venv", virtualEnvPath);
        pbCreateEnv.inheritIO();
        Process processCreateEnv = pbCreateEnv.start();
        try {
            processCreateEnv.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Upgrade pip
        ProcessBuilder pbUpgradePip = new ProcessBuilder(virtualEnvPath + "/bin/pip", "install", "--upgrade", "pip");
        pbUpgradePip.inheritIO();
        Process processUpgradePip = pbUpgradePip.start();
        try {
            processUpgradePip.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Source the virtual environment to activate it
        ProcessBuilder pbSourceEnv = new ProcessBuilder("source", virtualEnvPath + "/bin/activate");
        pbSourceEnv.inheritIO();
        Process processSourceEnv = pbSourceEnv.start();
        try {
            processSourceEnv.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
