package com.example.demo.algorithms;


import com.example.demo.classes.*;
import com.example.demo.repositories.*;
import com.example.demo.services.*;
import javassist.CtClass;
import javassist.CtMethod;
import org.springframework.beans.factory.annotation.Autowired;
import javassist.ClassPool;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.instrument.Instrumentation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;



@Component
@Scope("singleton")
public class Script {
    private final TuplaService tuplaService;
    private final BlocService blocService;
    private final TaulaService taulaService;
    private final IndexBService indexBService;
    private final IndexHashService indexHashService;
    private final TuplaRepository tuplaRepository;
    private final BlocRepository blocRepository;
    private final TaulaRepository taulaRepository;
    private final IndexBRepository indexBRepository;
    private final IndexHashRepository indexHashRepository;
    private static int cost;

    @Autowired
    public Script(TuplaService tuplaService, BlocService blocService, TaulaService taulaService, IndexBService indexBService, IndexHashService indexHashService, TuplaRepository tuplaRepository, BlocRepository blocRepository, TaulaRepository taulaRepository, IndexBRepository indexBRepository, IndexHashRepository indexHashRepository) {
        this.tuplaService = tuplaService;
        this.blocService = blocService;
        this.taulaService = taulaService;
        this.indexBService = indexBService;
        this.indexHashService = indexHashService;
        this.tuplaRepository = tuplaRepository;
        this.blocRepository = blocRepository;
        this.taulaRepository = taulaRepository;
        this.indexBRepository = indexBRepository;
        this.indexHashRepository = indexHashRepository;
        cost = 0;
    }
    public void selectAlgorithm(String filePath) {
        try {
            // Leer el contenido del archivo de texto
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();
            String fileContent = sb.toString();

            // Obtener una instancia de la clase que contiene el método

            ClassPool classPool = ClassPool.getDefault();
            CtClass targetClass = classPool.get("com.example.demo.algorithms.Script");
            String classpath = System.getProperty("java.class.path");
            System.out.println(classpath);

// Obtener el método que deseas modificar
            CtMethod targetMethod = targetClass.getDeclaredMethod("execute");

            System.out.println(targetMethod.toString());
// Modificar el contenido del método
            String modifiedMethodContent = "{" + fileContent + "}";
            System.out.println(modifiedMethodContent);

            targetMethod.setBody(modifiedMethodContent);

// Guardar la clase modificada en el mismo archivo o en un nuevo archivo
            targetClass.writeFile();

// Recargar la clase modificada en tiempo de ejecución
            Class<?> modifiedClass = targetClass.toClass();
            Object modifiedInstance = modifiedClass.getDeclaredConstructor().newInstance();

// Invocar el nuevo método modificado en el objeto correspondiente
            Method modifiedMethod = modifiedClass.getDeclaredMethod("execute");
            Object result = modifiedMethod.invoke(modifiedInstance);




        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void execute() {
        Tupla t;
        Bloc b;
        Taula taula;
        IndexB ib;
        IndexHash ih;
        Entrada e;
    }
    public static void sum_cost(int n) {
        cost = cost+n;
    }

}
