package com.example.demo;

import com.example.demo.tupla.Tupla;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		System.out.println("Hola mundo!");
		long id = 5;
		String s = "alex";
		Tupla t = new Tupla(id, s);
		System.out.println("Creacio tupla: id=" + t.getId()+", attribut="+t.getAtribut());
		t.setId((long) 10);
		t.setAtribut("alex paris");
		System.out.println("Modificació tupla: id=" + t.getId()+", attribut="+t.getAtribut());


		//SpringApplication.run(DemoApplication.class, args);
	}

}
