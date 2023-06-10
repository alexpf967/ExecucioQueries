package com.example.demo.services;

import com.example.demo.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
public class TestAlgorismeService {
    @SpyBean
    private AlgorismeService algorismeService;


    @Test
    public void llegirContent() {
        String expected = "package com.example.demo;\n" +
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
                "    }\n" +
                "public void execute() {\n" +
                "    long taula_id = taulaService.getIDbynomTaula(\"SS4\");\n" +
                "    int blocs = taulaService.nBlocs(taula_id);\n" +
                "    for(int i = 1; i <= blocs; ++i) {\n" +
                "        Bloc b = taulaService.getNBloc(taula_id, i);\n" +
                "        System.out.println(b.getId());\n" +
                "    }\n" +
                "}\n" +
                "}";
        String content = "public void execute() {\n" +
                "    long taula_id = taulaService.getIDbynomTaula(\"SS4\");\n" +
                "    int blocs = taulaService.nBlocs(taula_id);\n" +
                "    for(int i = 1; i <= blocs; ++i) {\n" +
                "        Bloc b = taulaService.getNBloc(taula_id, i);\n" +
                "        System.out.println(b.getId());\n" +
                "    }\n" +
                "}";
        String res = algorismeService.llegirContent(content);
        Assertions.assertEquals(expected, res);

    }
    @Test
    public void createIt() throws IOException {
        String outputDirectory = System.getProperty("user.dir") + File.separator +"src"+ File.separator +"main"+ File.separator +"java"+ File.separator+"com"+ File.separator+"example"+ File.separator+"demo"+ File.separator;
        String classname = "TestCreateIt.java";
        String content = "package com.example.demo;\n"+
                "public class TestCreateIt {\n" +
                "    public void execute() {\n" +
                "        System.out.println(\"Se ha creado la clase test!\");\n" +
                "    }\n" +
                "}";
        algorismeService.createIt(classname, content);
        File clase = new File(outputDirectory + classname);
        assert(clase.exists());
    }
    @Test
    public void compilar() throws IOException {
        String classname = "TestCreateIt";
        String content = "package com.example.demo;\n"+
                "public class TestCreateIt {\n" +
                "    public void execute() {\n" +
                "        System.out.println(\"Se ha creado la clase test!\");\n" +
                "    }\n" +
                "}";
        boolean compilat = algorismeService.compilar(classname, content);
        assert (compilat);
    }
}
