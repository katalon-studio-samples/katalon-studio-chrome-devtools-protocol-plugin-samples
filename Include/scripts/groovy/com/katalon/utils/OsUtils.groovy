package com.katalon.utils

import org.apache.commons.lang3.SystemUtils

import java.nio.file.Path
import java.nio.file.Paths

class OsUtils {

    static boolean runCommand(
            String command,
            Path workingDirectory,
            Map<String, String> environmentVariablesMap)
            throws IOException, InterruptedException {

        String[] cmdArray = SystemUtils.IS_OS_WINDOWS ? ["cmd", "/c", command] : ["sh", "-c", command]

        if (workingDirectory == null) {
            workingDirectory = Paths.get('.').toAbsolutePath()
        }

        if (environmentVariablesMap == null) {
            environmentVariablesMap = Collections.emptyMap()
        }

        println("Executing command: ${Arrays.toString(cmdArray)} in ${workingDirectory.toAbsolutePath()}")

        ProcessBuilder pb = new ProcessBuilder(cmdArray)
        Map<String, String> env = pb.environment()
        if (environmentVariablesMap != null) {
            env.putAll(environmentVariablesMap)
        }
        pb.directory(workingDirectory.toFile())
        pb.redirectErrorStream(true)
        Process cmdProc = pb.start()
        cmdProc.waitForProcessOutput(System.out, System.err)
        return cmdProc.exitValue() == 0
    }
}
