package com.example.demo;

import com.example.demo.classes.Bloc;
import com.example.demo.classes.IndexB;
import com.example.demo.classes.IndexHash;
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
public class DemoApplication {
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
	public static void sum_cost(int n) {
		cost = cost+n;
	}

}
