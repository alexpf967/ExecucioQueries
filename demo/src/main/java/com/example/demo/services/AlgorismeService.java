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
    @Autowired
    private EntradaService entradaService;

    private final String stringClass = "package com.example.demo;\n" +
            "import com.example.demo.classes.*;\n" +
            "import com.example.demo.repositories.*;\n" +
            "import com.example.demo.services.*;\n" +
            "import javassist.CtClass;\n" +
            "import javassist.CtMethod;\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import javassist.ClassPool;\n" +
            "import org.springframework.context.annotation.Bean;\n" +
            "import org.springframework.context.annotation.Scope;\n" +
            "import org.springframework.stereotype.Component;\n" +
            "\n" +
            "import java.io.BufferedReader;\n" +
            "import java.io.FileReader;\n" +
            "import java.lang.instrument.Instrumentation;\n" +
            "import java.lang.invoke.MethodHandle;\n" +
            "import java.lang.invoke.MethodHandles;\n" +
            "import java.lang.reflect.Field;\n" +
            "import java.lang.reflect.Method;\n" +
            "import java.util.List;\n" +
            "\n" +
            "@Component\n" +
            "public class Script {\n" +
            "    @Autowired\n" +
            "    public TuplaService tuplaService;\n" +
            "    @Autowired\n" +
            "    public BlocService blocService;\n" +
            "    @Autowired\n" +
            "    public TaulaService taulaService;\n" +
            "    @Autowired\n" +
            "    public IndexBService indexBService;\n" +
            "    @Autowired\n" +
            "    public IndexHashService indexHashService;\n" +
            "    @Autowired\n" +
            "    public EntradaService entradaService;\n" +
            "\n" +
            "    @Autowired\n" +
            "    public Script(TuplaService tuplaService, BlocService blocService, TaulaService taulaService, IndexBService indexBService, IndexHashService indexHashService, EntradaService entradaService) {\n" +
            "        this.tuplaService = tuplaService;\n" +
            "        this.blocService = blocService;\n" +
            "        this.taulaService = taulaService;\n" +
            "        this.indexBService = indexBService;\n" +
            "        this.entradaService = entradaService;\n" +
            "        this.indexHashService = indexHashService;\n" +
            "    }\n";
    public String llegirFitxer(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder(stringClass);
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        sb.append("}");
        br.close();
        return sb.toString();
    }
    public String llegirContent (String content) {
        String res = stringClass;
        res = res.concat(content+"\n}");
        return res;
    }

    public void createIt(String classname, String content) throws IOException {
        String outputDirectory = System.getProperty("user.dir") + File.separator +"src"+ File.separator +"main"+ File.separator +"java"+ File.separator+"com"+ File.separator+"example"+ File.separator+"demo"+ File.separator;
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
            String outputDirectory = System.getProperty("user.dir") + File.separator + "target" + File.separator + "classes" + File.separator;
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
                IndexHashService.class,
                EntradaService.class
        );
        Object iClass = constructor.newInstance(
                tuplaService,
                blocService,
                taulaService,
                indexBService,
                indexHashService,
                entradaService
        );
        Method thisMethod = thisClass.getDeclaredMethod("execute");
        thisMethod.invoke(iClass);
    }

    public void carregarAlgPath(String filePath) throws IOException {
        String clase = llegirFitxer(filePath);
        createIt("Script.java", clase);
        boolean compilat = compilar("Script", clase);
        if (compilat) {
            DemoApplication.cost=0;
        } else throw new RuntimeException("Error de copilacio, si us plau revisa el codi");


    }
    public void carregarAlgContent(String contentExecute) throws IOException {
        String contentclase = llegirContent(contentExecute);
        createIt("Script.java", contentclase);
        boolean compilat = compilar("Script", contentclase);
        if (compilat) {
            DemoApplication.cost=0;
        } else throw new RuntimeException("Error de copilacio, si us plau revisa el codi");
    }
    public void executar() {
        try {
            DemoApplication.cost=0;
            runIt("com.example.demo.Script");
        }
        catch (ClassNotFoundException|InvocationTargetException| InstantiationException| IllegalAccessException| NoSuchMethodException e) {
            throw new RuntimeException("No s'ha pogut executar l'algorisme, segurament no s'ha carregat correctament");
        }
    }
}
