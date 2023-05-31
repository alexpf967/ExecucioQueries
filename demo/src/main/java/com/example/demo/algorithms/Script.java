package com.example.demo.algorithms;


import com.example.demo.classes.*;
import com.example.demo.repositories.*;
import com.example.demo.services.*;
import javassist.CtClass;
import javassist.CtMethod;
import org.springframework.beans.factory.annotation.Autowired;
import javassist.ClassPool;
import org.springframework.context.annotation.Bean;
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
    @Autowired
    public TuplaService tuplaService;

    @Autowired
    public BlocService blocService;
    @Autowired
    public TaulaService taulaService;
    @Autowired
    public IndexBService indexBService;
    @Autowired
    public IndexHashService indexHashService;

    @Autowired
    public TuplaRepository tuplaRepository;
    @Autowired
    public BlocRepository blocRepository;
    @Autowired
    public TaulaRepository taulaRepository;
    @Autowired
    public IndexBRepository indexBRepository;
    @Autowired
    public IndexHashRepository indexHashRepository;
    public static int cost;

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

    public void execute() {
        System.out.println("execute");

    }

    public static void sum_cost(int n) {
        cost = cost+n;
    }
    public TuplaRepository tuplaRepository() {
        return this.tuplaRepository;
    }
    public BlocRepository blocRepository() {
        return this.blocRepository;
    }
    public IndexBRepository indexBRepository() {
        return this.indexBRepository;
    }
    public TaulaRepository taulaRepository() {
        return this.taulaRepository;
    }
    public IndexHashRepository indexHashRepository() {
        return this.indexHashRepository;
    }

    public BlocService blocService() {
        return this.blocService;
    }
    public IndexBService indexBService() {
        return this.indexBService;
    }
    public IndexHashService indexHashService() {
        return this.indexHashService;
    }
    public TaulaService taulaService() {
        return this.taulaService;
    }
    public TuplaService tuplaService() {
        return this.tuplaService;
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


// Obtener el método que deseas modificar
            CtMethod targetMethod = targetClass.getDeclaredMethod("execute", new CtClass[0]);

// Modificar el contenido del método
            String modifiedMethodContent = "{\n" + fileContent + "\n}";

            targetMethod.setBody(modifiedMethodContent);
            targetClass.setName("alg");
// Guardar la clase modificada en el mismo archivo o en un nuevo archivo
            targetClass.writeFile("demo.src.main.java.com.example.demo.algorithms");

            Class<?> modifiedClass = targetClass.toClass();

            // Obtener el método modificado en la instancia actual de la clase Script
            Method modifiedMethod = modifiedClass.getDeclaredMethod("execute");

            // Invocar el nuevo método modificado en el objeto correspondiente
            modifiedMethod.invoke(this);

// Recargar la clase modificada en tiempo de ejecución
            /*Class<?> modifiedClass = targetClass.toClass();
            Object modifiedInstance = modifiedClass.getDeclaredConstructor().newInstance();
*/


// Invocar el nuevo método modificado en el objeto correspondiente
           /* Method modifiedMethod = this.getClass().getDeclaredMethod("execute");
            Object result = modifiedMethod.invoke(this);

            */





        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
