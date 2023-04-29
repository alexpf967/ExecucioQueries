package com.example.demo;

import com.example.demo.classes.*;
import com.example.demo.repositories.TaulaRepository;
import com.example.demo.repositories.TuplaRepository;
import com.example.demo.services.BlocService;
import com.example.demo.services.IndexBService;
import com.example.demo.services.TaulaService;
import com.example.demo.services.TuplaService;
import com.fasterxml.jackson.databind.ser.impl.IndexedListSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired BlocService bs;
	@Autowired IndexBService ibs;
	@Autowired
	TaulaService taulaService;
	@Autowired TuplaService ts;
	@Autowired
	private TuplaRepository tuplaRepository;
	@Autowired
	private TaulaRepository taulaRepository;

	public static void main(String[] args) {
        /*
		System.out.println("Hola mundo!");
		long id = 10;
		Bloc b = new Bloc(id, new ArrayList<Tupla>());
		for(int i = 0; i < 10; ++i) {
			String s = "alex"+i;
			long idt = i;
			Tupla t = new Tupla(idt, s);
			b.afegirTupla(t);
		}
		b.mostrar_bloc();
		Tupla t2 = b.getTupla(Long.valueOf(4));
		System.out.println(t2.getId()+" "+t2.getAtribut());

		 */


		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Taula taula = new Taula("TAULA9");
		taula=taulaService.saveTaula(taula);
		taulaService.populate("TAULA9", 2, 10);
		taula=taulaRepository.findById(taula.getId()).orElse(null);
		taulaService.showTaula(taula.getId());
		taulaService.removeTaula(taula.getId());
		taulaService.showTaula(taula.getId());
		System.out.println("hola");

/*
		IndexB ib = new IndexB("indexProva2", 0.75, 3, taula);
		ibs.saveIndexB(ib);
		ibs.setfulles(ib.getId());
		List<Entrada> se = ibs.getfulles(ib.getId());
		for(Entrada e : se) {
			System.out.println(e.getTupla_id()+" "+e.getnBloc()+" "+e.getnTupla());
		}


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
		b = bs.saveBloc(b);
		for (int i = 0; i < 5; ++i) bs.add_tupla(b.getId(), "swapALEX");
		b = bs.getBlocById(b.getId());
		taulaService.swapBloc(4002, 6403, b);
		b = bs.getBlocById(6403);
		bs.printBloc(b);
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
