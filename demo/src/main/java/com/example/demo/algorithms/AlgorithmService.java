package com.example.demo.algorithms;

import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlgorithmService {
    public void executeCode(String code) {
        try {
            Class<?> dynamicClass = compileAndLoadCode(code);
            invokeMainMethod(dynamicClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Class<?> compileAndLoadCode(String code) throws Exception {
        // Crear un directorio temporal para guardar el archivo fuente y la clase compilada
        File tempDir = new File(System.getProperty("java.io.tmpdir") + File.separator + "Script");
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }

        // Guardar el archivo fuente
        String sourceFilePath = tempDir.getAbsolutePath() + File.separator + "Script.java";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sourceFilePath, StandardCharsets.UTF_8))) {
            writer.write(code);
        }

        // Compilar el archivo fuente
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, sourceFilePath);

        // Cargar la clase compilada
        URLClassLoader classLoader = new URLClassLoader(getClassPathUrls(), AlgorithmService.class.getClassLoader());
        Class<?> Script = classLoader.loadClass("Script");


        return Script;
    }

    private void invokeMainMethod(Class<?> dynamicClass) throws Exception {
        Method method = dynamicClass.getDeclaredMethod("execute", String[].class);
        String[] methodArgs = {};
        method.invoke(null, (Object) methodArgs);
    }
    private URL[] getClassPathUrls() throws Exception {
        List<URL> urls = new ArrayList<>();
        // Agregar las dependencias del proyecto al classpath
        // Puedes ajustar esto según las dependencias específicas de tu proyecto

        // Ejemplo: Agregar la dependencia de Spring Boot
        String springBootJarPath = "target/demo-0.0.1-SNAPSHOT.jar";
        File springBootJarFile = new File(springBootJarPath);
        urls.add(springBootJarFile.toURI().toURL());

        return urls.toArray(new URL[0]);
    }

}
