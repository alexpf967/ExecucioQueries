package com.example.demo;

import com.example.demo.classes.*;
import com.example.demo.repositories.*;
import com.example.demo.services.*;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
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
	@Autowired
	private EntradaRepository entradaRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) {
		int accion = 0;
		String nom_taula;
		String nom_index;
		int blocs;
		int tuplas;

		Scanner scanner = new Scanner(System.in);
		while (accion != -1) {
			System.out.println("Indiqui el numero de l'acció que vol realitzar:");
			System.out.println("1. Crear una taula");
			System.out.println("2. Consultar una taula");
			System.out.println("3. Populate una taula");
			System.out.println("4. Afegir bloc a una taula");
			System.out.println("5. Afegir tupla al Nbloc d'una taula");
			System.out.println("6. Crear un IndexB+ a una taula");
			System.out.println("7. Crear un IndexHash a una taula");
			System.out.println("8. Consultar un IndexB+");
			System.out.println("9. Consultar un IndexHash");


			accion = Integer.parseInt(scanner.nextLine());

			if (accion == 1) {
				try {
					System.out.println("Indiqui el nom de la taula que vol crear: ");
					nom_taula = scanner.nextLine();
					Taula taula = new Taula(nom_taula);
					Taula t = taulaService.saveTaula(taula);
					System.out.println("TAULA " + nom_taula + " AMB ID = " + t.getId() + " CREADA AMB EXIT!");

				} catch (DataIntegrityViolationException | ConstraintViolationException e) {
					System.out.println("ERROR: Ja existeix una taula amb aquest nom (duplicate key)");
				}
			}
			if (accion == 2) {
				try {
					System.out.println("Indiqui el nom de la taula que vol consultar: ");
					nom_taula = scanner.nextLine();
					long id = taulaService.getIDbynomTaula(nom_taula);
					taulaService.showTaula(id);
				} catch (NullPointerException e) {
					System.out.println("ERROR: No existeix cap taula amb aquest nom");
				}
			}
			if (accion == 3) {
				try {
					System.out.println("Indiqui el nom de la taula que vol fer populate: ");
					nom_taula = scanner.nextLine();
					System.out.println("Indiqui el numero de blocs de la taula: ");
					blocs = Integer.parseInt(scanner.nextLine());
					System.out.println("Indiqui el numero de tuplas per cada bloc: ");
					tuplas = Integer.parseInt(scanner.nextLine());
					taulaService.populate(nom_taula, blocs, tuplas);
					System.out.println("POPULATE TAULA " + nom_taula + " AMB EXIT!");

				} catch (NullPointerException e) {
					System.out.println("ERROR: No existeix cap taula amb aquest nom");
				}
			}
			if (accion == 4) {
				try {
					System.out.println("Indiqui el nom de la taula que vol afegir un bloc: ");
					nom_taula = scanner.nextLine();
					long id = taulaService.getIDbynomTaula(nom_taula);
					taulaService.add_bloc(id);
					System.out.println("S'HA AFEGIT UN BLOC A LA TAULA " + nom_taula);

				} catch (NullPointerException e) {
					System.out.println("ERROR: No existeix cap taula amb aquest nom");
				}
			}
			if (accion == 5) {
				try {
					System.out.println("Indiqui el nom de la taula que vols afegir la tupla:");
					nom_taula = scanner.nextLine();
					long id = taulaService.getIDbynomTaula(nom_taula);
					int nBlocs = taulaService.nBlocs(id);
					System.out.println("La taula " + nom_taula + " té " + nBlocs + " blocs, indiqui a quin bloc vol afegir la tupla: ");
					blocs = Integer.parseInt(scanner.nextLine());
					System.out.println("Indiqui l'atribut de la tupla que vol afegir: ");
					String atribut = scanner.nextLine();
					taulaService.addTupla_BlocN(id, blocs, atribut);
					System.out.println("S'HA AFEGIT LA TUPLA AMB ATRIBUT " + atribut + " AL BLOC " + blocs + " A LA TAULA " + nom_taula);

				} catch (NullPointerException e) {
					System.out.println("ERROR: No existeix cap taula amb aquest nom o el numero de bloc es incorrecte");
				}
			}
			if (accion == 6) {
				try {
					System.out.println("Indiqui el nom de la taula que vols crear l'index B+:");
					nom_taula = scanner.nextLine();
					Taula t = taulaRepository.findByNomTaula(nom_taula);
					nom_index = indexBRepository.findIndexBbyTaulaID(t.getId());
					if (nom_index != null) throw new RuntimeException("Ja existeix l'index B+ amb nom " + nom_index + " a la taula " + nom_taula);
					else {
						System.out.println("Indiqui el nom de l'index B+:");
						nom_index = scanner.nextLine();
						System.out.println("Indiqui el factor de carrega de l'index B+:");
						double f_carrega = Double.parseDouble(scanner.nextLine());
						System.out.println("Indiqui el tree order de l'index B+:");
						int tree_order = Integer.parseInt(scanner.nextLine());
						IndexB ib = new IndexB(nom_index, f_carrega, tree_order, t);
						indexBService.saveIndexB(ib);
						System.out.println("S'HA CREAT CORRECTAMENT L'INDEX B+ " + nom_index + " A LA TAULA " + nom_taula);
					}
				} catch (NullPointerException e) {
					System.out.println("ERROR: No existeix cap taula amb aquest nom");
				} catch (RuntimeException r) {
					System.out.println(r.getMessage());
				}
			}
			if (accion == 7) {
				try {
					System.out.println("Indiqui el nom de la taula que vols crear l'index hash:");
					nom_taula = scanner.nextLine();
					Taula t = taulaRepository.findByNomTaula(nom_taula);
					nom_index = indexHashRepository.findIndexHashbyTaulaID(t.getId());
					if (nom_index != null) throw new RuntimeException("Ja existeix l'index hash amb nom " + nom_index + " a la taula " + nom_taula);
					else {
						System.out.println("Indiqui el nom de l'index hash:");
						nom_index = scanner.nextLine();
						System.out.println("Indiqui el factor de carrega de l'index hash:");
						double f_carrega = Double.parseDouble(scanner.nextLine());
						System.out.println("Indiqui el tree order de l'index hash:");
						int tree_order = Integer.parseInt(scanner.nextLine());
						System.out.println("Indiqui el numero de buckets de l'index hash:");
						int nBuckets = Integer.parseInt(scanner.nextLine());
						IndexHash ih = new IndexHash(nom_index, f_carrega, tree_order, nBuckets, t);
						indexHashService.saveIndexHash(ih);
						System.out.println("S'HA CREAT CORRECTAMENT L'INDEX HASH " + nom_index + " A LA TAULA " + nom_taula);
					}
				} catch (NullPointerException e) {
					System.out.println("ERROR: No existeix cap taula amb aquest nom");
				} catch (RuntimeException r) {
					System.out.println(r.getMessage());
				}
			}
			if (accion == 8) {
				try {
					System.out.println("Indiqui el nom de l'index B+:");
					nom_index = scanner.nextLine();
					long id = indexBRepository.findIDByNomIndexB(nom_index);
					indexBService.update_indexB(id);
					int nFulles = indexBService.getNumFulles(id);
					System.out.println("L'index B+ " + nom_index + "té " + nFulles + "fulles: ");
					for (int i = 0; i < nFulles; ++i) {
						List<Entrada> le = indexBService.getFullaN(id, i+1);
						System.out.println("La fulla " + (i+1) + " té " + le.size() + " entrades: ");
						for (Entrada e : le) {
							System.out.println("{ tupla_id: " + e.getTupla_id() + ", nBloc: " + e.getnBloc() + ", nTupla: " + e.getnTupla() + " }");
						}
					}
				} catch (NullPointerException e) {
					System.out.println("ERROR: No existeix cap taula amb aquest nom");
				}
			}
			if (accion == 9) {
				try {
					System.out.println("Indiqui el nom de l'index hash:");
					nom_index = scanner.nextLine();
					long id = indexHashRepository.findIDByNomIndexHash(nom_index);
					indexHashService.update_indexHash(id);
					int nBuckets = indexHashService.getnBuckets(id);
					System.out.println("L'index Hash " + nom_index + " té " + nBuckets + " buckets: ");
					for (int i = 0; i < nBuckets; ++i) {
						List<Entrada> le = indexHashService.getBucketN(id, i+1);
						System.out.println("El bucket " + (i+1) + " té " + le.size() + " entrades: ");
						for (Entrada e : le) {
							System.out.println("{ tupla_id: " + e.getTupla_id() + ", nBloc: " + e.getnBloc() + ", nTupla: " + e.getnTupla() + " }");
						}
					}
				} catch (NullPointerException e) {
					System.out.println("ERROR: No existeix cap taula amb aquest nom");
				}
			}

		}
		System.exit(0);
	}
}
