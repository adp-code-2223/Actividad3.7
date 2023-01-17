package main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import modelo.Departamento;
import util.SessionFactoryUtil;

public class Main {

	public static void main(String[] args) {

		// createDepartamento();
		findDepartamento(10);

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
}
