package main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import modelo.Departamento;
import modelo.Emp;
import util.SessionFactoryUtil;

public class Main {

	public static void main(String[] args) {

		// createDepartamento();
		//deleteDepartamento(46);
		
		
//		updateDepartamento(45);
//		findDepartamento(45);
		
		createEmpleado(0);

	}

	private static void createDepartamento() {

		Transaction tx = null;
		SessionFactory factoria = SessionFactoryUtil.getSessionFactory();

		try (Session sesion = factoria.openSession();) {
			tx = sesion.beginTransaction();
			Departamento departamento = new Departamento();
			departamento.setDname("RR.HH.");
			departamento.setLoc("Lugo");
			Integer deptId = (Integer) sesion.save(departamento);
			System.out.println("Se ha generado un departamento con id: " + deptId);
			System.out.println("deptno: " + departamento.getDeptno());

			tx.commit();

		} catch (Exception ex) {
			System.err.println("Ha habido una exception " + ex);
			if (tx != null) {
				tx.rollback();
			}
			throw ex;
		}
		// No necesario con try with resources: sesion.close();

	}

	private static void findDepartamento(int id) {

		SessionFactory factoria = SessionFactoryUtil.getSessionFactory();

		Transaction tx = null;
		try (Session sesion = factoria.openSession();) {

			// Departamento dept = sesion.load(Departamento.class, id);

			tx = sesion.beginTransaction();
			Departamento dept = sesion.get(Departamento.class, id);

			if (dept == null) {
				System.out.println("No existe id: " + id);
			} else {

				System.out.println("Name: " + dept.getDname());
				System.out.println("Deptno: " + dept.getDeptno());
				System.out.println("Location: " + dept.getLoc());

				// Apartado g:
				// Con lazy=true, el select sobre tabla EMP se hace con el get de
				// dept.getEmps();
				// Con lazy=false, el select sobre tabla EMP se hace siempre que se recupera un
				// Departamento
//				Set<Emp> empleados = dept.getEmps();
//				for (Emp emp : empleados) {
//					System.out.println("Nombre empleado: " + emp.getEname());
//				}

			}
			tx.commit();

		} catch (Exception ex) {
			System.err.println("Ha habido una exception " + ex);
			if (tx != null) {
				tx.rollback();
			}
			throw ex;
		}

		// No necesario con try with resources: sesion.close();

	}

	private static void deleteDepartamento(int id) {

		SessionFactory factoria = SessionFactoryUtil.getSessionFactory();

		Transaction tx = null;
		try (Session sesion = factoria.openSession();) {

			tx = sesion.beginTransaction();
			Departamento dept = sesion.load(Departamento.class, id);

			sesion.delete(dept);
			tx.commit();
		} catch (Exception ex) {
			System.err.println("Ha habido una exception " + ex);
			if (tx != null) {
				tx.rollback();
			}
			throw ex;
		}

	}
	
	private static void updateDepartamento(int id) {

		SessionFactory factoria = SessionFactoryUtil.getSessionFactory();

		Transaction tx = null;
		try (Session sesion = factoria.openSession();) {

			tx = sesion.beginTransaction();
			Departamento dept = sesion.load(Departamento.class, id);

			dept.setDname("Recursos Humanos 2");
			dept.setLoc("Oviedo");
			
			sesion.saveOrUpdate(dept);
			
			tx.commit();
		} catch (Exception ex) {
			System.err.println("Ha habido una exception " + ex);
			if (tx != null) {
				tx.rollback();
			}
			throw ex;
		}

	}
	
	private static void createEmpleado(int deptid)
	{
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = format.parse("1985-10-26");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
