package com.example.demo.services;

import com.example.demo.DemoApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.tools.*;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;

@Service
public class AlgorismeService {
    @Autowired
    private TuplaService tuplaService;
    @Autowired
    private BlocService blocService;
    @Autowired
    private TaulaService taulaService;
    @Autowired
    private IndexBService indexBService;
    @Autowired
    private IndexHashService indexHashService;
    public String llegirClase (String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();
        return sb.toString();
    }

    public void createIt(String classname, String content) throws IOException {
        String outputDirectory = System.getProperty("user.dir") + File.separator + "demo"+ File.separator +"src"+ File.separator +"main"+ File.separator +"java"+ File.separator+"com"+ File.separator+"example"+ File.separator+"demo"+ File.separator;
        File directory = new File(outputDirectory);
        File outputFile = new File(directory, classname);
        FileWriter aw = new FileWriter(outputFile);
        aw.write(content);
        aw.flush();
        aw.close();
    }
    public boolean compilar(String classname, String content) {
        String [] source = {new String(content)};
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            JavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
            String outputDirectory = System.getProperty("user.dir") + File.separator + "demo" + File.separator + "target" + File.separator + "classes" + File.separator;
            ((StandardJavaFileManager) fileManager).setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(new File(outputDirectory)));

            JavaFileObject s = new DynamicJavaFileObject(classname, content);
            Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(s);

            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compilationUnits);
            boolean success = task.call();

            return success;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static class DynamicJavaFileObject extends SimpleJavaFileObject {
        private final String code;

        public DynamicJavaFileObject(String className, String code) {
            super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return code;
        }
    }
    public void runIt(String classname) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<?> thisClass = Class.forName(classname);
        Constructor<?> constructor = thisClass.getConstructor(
                TuplaService.class,
                BlocService.class,
                TaulaService.class,
                IndexBService.class,
                IndexHashService.class
        );
        Object iClass = constructor.newInstance(
                tuplaService,
                blocService,
                taulaService,
                indexBService,
                indexHashService
        );
        Method thisMethod = thisClass.getDeclaredMethod("execute");
        thisMethod.invoke(iClass);
    }

    public void executarAlgorismePath(String filePath) {
        try {
            String clase = llegirClase(filePath);
            createIt("Script1.java", clase);
            boolean compilat = compilar("Script1", clase);
            if (compilat) {
                runIt("com.example.demo.Script1");
            } else System.out.println("Error de copilacio");
        }
        catch (IOException|ClassNotFoundException|InvocationTargetException|InstantiationException|IllegalAccessException|NoSuchMethodException e) {
            System.out.println(e.getMessage());
        }

    }
    public void executarAlgorismeContent(String contentclase) {
        try {
            createIt("Script1.java", contentclase);
            boolean compilat = compilar("Script1", contentclase);
            if (compilat) {
                runIt("com.example.demo.Script1");
            } else System.out.println("Error de copilacio");
        }
        catch (IOException|ClassNotFoundException|InvocationTargetException|InstantiationException|IllegalAccessException|NoSuchMethodException e) {
            System.out.println(e.getMessage());
        }

    }
}
