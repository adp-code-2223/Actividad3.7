package main;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import modelo.Departamento;
import modelo.Emp;
import util.SessionFactoryUtil;

public class ConsultasHQL {
	public static void main(String[] args) {
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();

		Session session = sessionFactory.openSession();

		{
			System.out.println(
					"----------- Q1: El nombre, el puesto de trabajo y el salario de todos los empleados-----------");
			List<Object[]> listaDatos = session.createQuery(" select e.ename, e.job, e.sal FROM Emp e ").list();

			for (Object[] fila : listaDatos) {
				System.out.println("Nombre: " + fila[0] + " Job: " + fila[1] + " Sal: " + fila[2]);
			}
		}
		
		
		{
			System.out.println(
					"----------- Q2: La media del salario de todos los empleados-----------");
			Double media = (Double)session.createQuery(" select avg(e.sal) FROM Emp e ").uniqueResult();

			
				System.out.println("Media: " + media);
			
		}
		
		{
			System.out.println(
					"----------- Q3: Los empleados que tengan un salario mayor que la media de todos los empleados-----------");
			List<Emp> empleados = session.createQuery(" select e from Emp e where e.sal > (select avg(e2.sal) FROM Emp e2 )").list();

			
			for (Emp empleado : empleados) {
				System.out.println("Empleado: " + empleado);
			}
			
		}
		
		
		{
			System.out.println(
					"----------- Q3: Los empleados que tengan un salario mayor que la media de todos los empleados-----------");
			List<Emp> empleados = session.createQuery(" select e from Emp e where e.sal > (select avg(e2.sal) FROM Emp e2 )").list();

			
			for (Emp empleado : empleados) {
				System.out.println("Empleado: " + empleado.getEname() + " sal:" + empleado.getSal());
			}
			
		}

		
		{
			int idDepartamento=10;
			System.out.println(
					"----------- Q4A: Los departamentos con un determinado id con parámetros nominales -----------");
			List<Departamento> depts = session.createQuery(" select d from Departamento d where d.deptno =:identificadorDeptno")
					.setParameter("identificadorDeptno", idDepartamento).list();
				

			
			for (Departamento dept : depts) {
				System.out.println(dept);
			}
			
		}
		
		
		{
			int idDepartamento=10;
			System.out.println(
					"----------- Q4B: Los departamentos con un determinado id con 2 parámetros  posicionales -----------");
			List<Departamento> depts = session.createQuery(" select d from Departamento d where deptno =?0 and d.dname=?1")
					.setParameter(0, idDepartamento).setParameter(1, "ACCOUNTING").list();
				

			
			for (Departamento dept : depts) {
				System.out.println(dept);
			}
			
		}
		
		
		{
			int idDepartamento=10;
			List<Integer> deptList = new ArrayList<Integer>();
			deptList.add(10);
			deptList.add(20);
			System.out.println(
					"----------- Q5:  Los nombres de los departamentos cuyos ids sean el 10 o el 20. Usa una lista parametrizada.-----------");
			List<String> depts = session.createQuery(" select d.dname from Departamento d where d.deptno in :lista")
					.setParameterList("lista",deptList)   .list();
				

			
			for (String dept : depts) {
				System.out.println(dept);
			}
			
		}
		
		{
			System.out.println(
					"----------- Q6: El número de empleados por departamento-----------");
			List<Object[]> datos = session.createQuery(" select count(e.empno), "
					+ "e.dept.deptno FROM Emp e group by e.dept.deptno").list();

			
			for (Object[] fila : datos) {
				System.out.println("Nº emps: " + fila[0] + " deptno: " + fila[1] );
			}
	
			
		}
		
		session.close();

		sessionFactory.close();
	}

}
