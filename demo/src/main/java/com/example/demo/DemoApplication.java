package com.example.demo;

import com.example.demo.classes.Bloc;
import com.example.demo.services.BlocService;
import com.example.demo.services.TuplaService;
import com.example.demo.classes.Tupla;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired BlocService bs;
	@Autowired TuplaService ts;

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
		Bloc b = new Bloc();
		bs.saveBloc(b);
		long id2=0;
		for(int i = 0; i < 10; ++i) {
			String s = "alex00";
			Tupla t = new Tupla(s, b);

			bs.add_tupla(b, t);
			if (i == 3) id2 = t.getId();
		}
		Tupla t2 = ts.getTuplaById(id2);
		System.out.println(id2);
		bs.remove_tupla(b,t2);
		Tupla a = ts.getTuplaById(id2);
		System.out.println(a);

		/*for(int i = 0; i < 10; ++i) {
			Tupla t = new Tupla();
			t.setAtribut("alexP"+i);
			ts.saveTupla(t);
		}*/

		List<Tupla> l = ts.getTuplasByBlocID(b.getId());
		l.forEach(res->{System.out.println(res.getId()+" " + res.getAtribut());});

	}
}
