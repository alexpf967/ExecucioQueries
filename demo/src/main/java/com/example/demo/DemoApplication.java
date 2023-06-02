package com.example.demo;

import com.example.demo.classes.Bloc;
import com.example.demo.classes.IndexB;
import com.example.demo.classes.Taula;
import com.example.demo.repositories.*;
import com.example.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import javax.tools.*;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Scanner;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
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
	@Autowired
	public AlgorismeService algorismeService;
	public static int cost;


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//String filePath = "C:\\Users\\Usuario\\OneDrive\\Escritorio\\TFG\\script1.txt";
		//algorismeService.executarAlgorismePath(filePath);

		/*String clase = algorismeService.llegirClase(filePath);
		algorismeService.createIt("Script1.java",clase);
		boolean compilat = algorismeService.compilar("Script1",clase);
		if (compilat) {
			algorismeService.runIt("com.example.demo.Script1");
		}
		else System.out.println("Error de copilacio");

		 */
		/*StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line).append("\n");
		}
		br.close();
		String fileContent = sb.toString();
		System.out.println(fileContent);
		createIt("Script1.java", fileContent);
		System.out.println(compilar("Script1", fileContent));
		String outputDirectory = System.getProperty("user.dir") + File.separator + "demo" + File.separator + "target" + File.separator + "classes" + File.separator;
		runIt("com.example.demo.Script1", tuplaService, blocService, taulaService, indexBService, indexHashService);


		 */
	}
	public static void sum_cost(int n) {
		cost = cost+n;
	}

	/*public void createIt(String classname, String content) throws IOException {

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

	public void runIt(String classname, TuplaService tuplaService, BlocService blocService, TaulaService taulaService, IndexBService indexBService, IndexHashService indexHashService) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, MalformedURLException {
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

	 */

}
