package com.example.demo;

import com.example.demo.bloc.Bloc;
import com.example.demo.tupla.Tupla;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
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


		//SpringApplication.run(DemoApplication.class, args);
	}

}
