package com.example.demo;

import com.example.demo.algorithms.Script;
import com.example.demo.classes.Taula;
import com.example.demo.repositories.*;
import com.example.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
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
	private TuplaRepository tuplaRepository;
	@Autowired
	private BlocRepository blocRepository;
	@Autowired
	private TaulaRepository taulaRepository;
	@Autowired
	private IndexBRepository indexBRepository;
	@Autowired
	private IndexHashRepository indexHashRepository;



	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//EXECUCIÓ ITERATIVA
		/*Scanner scanner = new Scanner(System.in);
		System.out.print("Ingrese el nombre de la tabla a crear: ");
		String nombre = scanner.nextLine();
		Taula taula = new Taula(nombre);
		taula=taulaService.saveTaula(taula);
		taulaService.populate(nombre, 2, 10);

		 */

		System.out.println("INI");
		Script s = new Script(tuplaService, blocService, taulaService, indexBService, indexHashService, tuplaRepository, blocRepository, taulaRepository, indexBRepository, indexHashRepository);
		s.execute();
		System.out.println("FIN");
		/*
		try {
			String command = "cmd /c start cmd.exe /k \"cd C:\\Users\\Usuario\\OneDrive\\Escritorio\\TFG\\ExecucioQueries\\demo && mvn exec:java -Dexec.mainClass=Script";
			Process p = Runtime.getRuntime().exec(command);
			p.waitFor();
			/*
			// Crear una instancia de ProcessBuilder
			ProcessBuilder pb = new ProcessBuilder("java", "C:\\Users\\Usuario\\OneDrive\\Escritorio\\TFG\\ExecucioQueries\\demo\\src\\main\\java\\com\\example\\MiScript");

			// Redirigir la salida estándar y el error estándar del proceso
			pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
			pb.redirectError(ProcessBuilder.Redirect.INHERIT);

			// Ejecutar el proceso
			Process process = pb.start();



			// Esperar a que el proceso termine
			int exitCode = process.waitFor();


			//System.out.println("El proceso ha terminado con código de salida " + exitCode);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		 */



		/*String scriptPath = "C:\\Users\\Usuario\\OneDrive\\Escritorio\\try.sh";

		// Crear una lista con los comandos a ejecutar
		List<String> comandos = new ArrayList<String>();
		comandos.add("cmd.exe"); // Indicar que se usará el intérprete de comandos de Windows
		comandos.add("/c"); // Indicar que el script se ejecutará con el intérprete de comandos de Unix/Linux
		comandos.add(scriptPath); // Agregar la ruta del script como argumento

		// Crear un objeto ProcessBuilder con la lista de comandos
		ProcessBuilder pb = new ProcessBuilder(comandos);

		// Ejecutar el proceso y obtener su resultado
		try {
			Process proceso = pb.start();
			int exitCode = proceso.waitFor(); // Esperar a que el proceso termine y obtener su código de salida
			System.out.println("El script ha terminado con código de salida " + exitCode);
		} catch (IOException e) {
			System.out.println("Error al ejecutar el script: " + e.getMessage());
		} catch (InterruptedException e) {
			System.out.println("El proceso ha sido interrumpido: " + e.getMessage());
		}*/
/*
		Taula taula = new Taula("TAULAINDEX32");
		taula=taulaService.saveTaula(taula);
		taulaService.populate("TAULAINDEX32", 2, 10);
		taula=taulaRepository.findById(taula.getId()).orElse(null);


		IndexB ib = new IndexB("indexB32", 0.75, 3, taula);
		ibs.saveIndexB(ib);
		ibs.update_indexB(ib.getId());
		IndexHash ih = new IndexHash("indexH32", 0.75, 3, 4,taula);
		ihs.saveIndexHash(ih);
		ihs.update_indexHash(ih.getId());

		for(int i = 0; i < 5; ++i) {
			taulaService.addTupla_BlocN(taula.getId(), 1, "UPDATE"+i);
		}
		ibs.update_indexB(ib.getId());
		System.out.println("Done indexB +");
		ihs.update_indexHash(ih.getId());
		System.out.println("Done indexHash");






		System.out.println(ihs.getnBuckets(ih.getId()));
		List<Entrada> le = ihs.getPrimerBucket(ih.getId());
		for (Entrada e : le) {
			System.out.println(e.getTupla_id());
		}
		le = ihs.getBucketN(ih.getId(), 2);
		for (Entrada e : le) {
			System.out.println(e.getTupla_id());
		}


		System.out.println(ihs.cercaBucket(1002, 15221));
		System.out.println(ihs.ultimBucket(1002, 4));

 */






        /*
		ibs.updateEntradas(ib.getId());
		List<Entrada> se = ibs.getEntradas(ib.getId());
		for(Entrada e : se) {
			System.out.println(e.getTupla_id()+" "+e.getnBloc()+" "+e.getnTupla());
		}
		Entrada e = ibs.getNEntrada(ib.getId(), 0);
		System.out.println(e.getTupla_id()+" "+e.getnBloc()+" "+e.getnTupla());
		taulaService.addTupla_BlocN(taula.getId(), 0, "holahola");
		ibs.updateEntradas(ib.getId());
		se = ibs.getEntradas(ib.getId());
		for(Entrada e1 : se) {
			System.out.println(e1.getTupla_id()+" "+e1.getnBloc()+" "+e1.getnTupla());
		}


		System.out.println(ibs.ultima_fulla(1152, 911));

		 */








        /*long id = 552;
		//bs.remove_tupla(552, 152);
		Tupla t = tuplaRepository.findById(id).orElse(null);
		if (t != null)tuplaRepository.delete_tupla(t.getId());*/
		//Bloc b = taulaService.getNBloc(4002,0);
		//System.out.println(b.getId());
        /*Bloc b = bs.getBlocById(7003);
		bs.printBloc(b);
		List<Tupla> lt = tuplaRepository.findByBlocID(b.getId());

		bs.remove_tupla(b.getId(), 5810);

		b = bs.getBlocById(7003);
		bs.printBloc(b);*/

/*
		Bloc b = new Bloc();
		for (int i = 0; i < 5; ++i) {
			Tupla t = new Tupla("Swap"+i, b);
			b.addTupla(t);
		}
		taulaService.escriureBloc(6002, 9152, b);
		b = bs.getBlocById(9152);
		bs.printBloc(b);
		taulaService.showTaula(6002);

 */


		//List<Tupla> bloc = tuplaRepository.findByBlocID(152);
		//Tupla t = bs.getNTupla(b.getId(), 2);
		//System.out.println(t.getId());
/*
		Taula taula = new Taula("ALEX1");
		taula=taulaService.saveTaula(taula);


		for(int i = 0; i < 5; ++i) {
			taulaService.add_bloc(taula.getId());
		}
		taula=taulaRepository.findById(taula.getId()).orElse(null);

		if (taula != null) {
			Set<Bloc> sb = taula.getTaula();
			for (Bloc b : sb) {
				Bloc bb = bs.getBlocById(b.getId());
				for (int i = 0; i < 5; ++i) bs.add_tupla(bb.getId(), "0000000");
			}
			Taula taula1 = taulaRepository.findByNomTaula("ALEX1");
			taulaService.showTaula(taula1.getId());
			taula=taulaRepository.findById(taula1.getId()).orElse(null);
			if (taula != null) {
				Set<Bloc> sb2 = taulaService.getAllBlocs(taula.getId());
				for (Bloc b : sb2) {
					Bloc bb = bs.getBlocById(b.getId());
					taulaService.remove_bloc(taula.getId(), bb.getId());
				}
			}
			taula=taulaRepository.findById(taula.getId()).orElse(null);
			taulaService.showTaula(taula.getId());

		}



 */





        /*Bloc b = new Bloc();
		b = bs.saveBloc(b);
		long id= b.getId();
		Tupla t = new Tupla();

		for(int i = 0; i < 10; ++i) {
			String s = "provaREFACTOR"+i;

			bs.add_tupla(id, s);


		}
		List<Tupla> l = ts.getTuplasByBlocID(b.getId());
		l.forEach(res->{System.out.println(res.getId()+" " + res.getAtribut());});
		Tupla t2 = l.get(0);



		//bs.remove_tupla(b,t);
		//l = ts.getTuplasByBlocID(b.getId());
		//l.forEach(res->{System.out.println(res.getId()+" " + res.getAtribut());});
		//Tupla a = ts.getTuplaById(t.getId());
		//if (a == null)	System.out.println("s'ha borrat correctament");
		/*
		Tupla t = new Tupla("prova", b);
		bs.add_tupla(b, t);
		List<Tupla> l = ts.getTuplasByBlocID(b.getId());
		l.forEach(res->{System.out.println(res.getId()+" " + res.getAtribut());});
		bs.remove_tupla(b,t);
		l = ts.getTuplasByBlocID(b.getId());
		if (l.isEmpty())System.out.println("s'ha borrat correctament");
		Tupla a = ts.getTuplaById(t.getId());
		if (a == null)	System.out.println("s'ha borrat correctament");
*/

        /*for(int i = 0; i < 10; ++i) {
			Tupla t = new Tupla();
			t.setAtribut("alexP"+i);
			ts.saveTupla(t);
		}*/

        /*List<Tupla> l = ts.getTuplasByBlocID(b.getId());
		l.forEach(res->{System.out.println(res.getId()+" " + res.getAtribut());});
		System.out.println(bs.Ntuplas(b));*/

	}
}
