package main;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import util.SessionFactoryUtil;

public class ConsultasAsociacionesHQL {

	public static void main(String[] args) {
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();

		Session session = sessionFactory.openSession();

		{
			System.out.println(
					"----------- Q1: Los nombres de los departamentos que "
					+ "no tengan empleados ordenados por nombre -----------");
			List<String> deptList = session
					.createQuery(" select d.dname FROM Departamento d where size (d.emps) =0 order by d.dname  ")
//					.createQuery(" select d.dname FROM Departamento d where "
//							+ "d.emps is empty order by d.dname  ")
					// .createQuery(" select d.dname FROM Departamento d left join d.emps e "
							// + "where e =null "
							// + "order by d.dname  ")
					.list();

			for (String deptNombre : deptList) {
				System.out.println("Nombre: " + deptNombre);
			}

		}

		{

			System.out.println(
					"----------- Q2: Los nombres de los departamentos y de los empleados que tienen al menos 2 empleados ordenados por nombre de depto -----------");
			List<Object[]> deptList = session.
					createQuery(
					" select d.dname, e.ename"
					+ " FROM Departamento d join d.emps e "
					+ "where size (d.emps) >=2  order by d.dname")
					.list();

			for (Object[] filas : deptList) {
				System.out.println("Nombre depto: " + filas[0] + " Nombre emp: " + filas[1]);
			}

		}

		{

			System.out.println("----------- Q3: Los ids de los empleados y el nº de cuentas por empleado -----------");
			List<Object[]> deptList = session
					.createQuery("  select e.empno,  count(a) FROM Emp e left join e.accounts a group by e.empno").list();
					//.createQuery("  select e.empno,  size(e.accounts) FROM Emp e").list();
			for (Object[] filas : deptList) {
				System.out.println("Id emp: " + filas[0] + " Nº de cuentas: " + filas[1]);
			}

		}

		{

			System.out.println("----------- Q4: Los ids de los empleados y el saldo de sus cuentas -----------");
			List<Object[]> deptList = session
					.createQuery("  select e.empno,  sum(a.amount) FROM Emp e join e.accounts a group by e.empno")
					.list();

			for (Object[] filas : deptList) {
				System.out.println("Id emp: " + filas[0] + " Saldo cuenta(s): " + filas[1]);
			}

		}
		
		
		
		{

			System.out.println("----------- Q5: El identificador de cada cuenta con el identificador del movimiento donde la cuenta es la cuenta origen -----------");
			List<Object[]> cuentasIds = session
					.createQuery("  select c.accountOrigen.accountno,"
							+ " c.accountMovId from AccMovement c")
					.list();

			for (Object[] filas : cuentasIds) {
				System.out.println("Id account: " + filas[0] + " Id mov: " + filas[1]);
			}

		}
		

		{

			System.out.println("----------- Q6: El nº de movimientos por cada cuenta origen -----------");
			List<Object[]> datos = session
					.createQuery("select c.accountno, size(c.accMovementsOrigen) from Account c")
					// Otra posibilidad: .createQuery("select c.accountno, count(o) from "
					//+" Account c left join c.accMovementsOrigen o group by c.accountno")
					
					//La siguiente consulta no se corresponde con el enunciado porque las
					//cuentas que no tienen movimientos no aparecen: 
					//.createQuery("select c.accountOrigen.accountno, count(c.accountMovId) "
//							+ "from AccMovement c group by c.accountOrigen.accountno")
				
					.list();

			for (Object[] filas : datos) {
				System.out.println("Id account: " + filas[0] + " Nº.mov: " + filas[1]);
			}

		}
		
			
		
		{

			System.out.println("----------- Q7. El nombre de cada empleado con el de su jefe. Ha de aparecer el nombre del empleado aunque no tenga jefe\r\n"
					+ "		 -----------");
			List<Object[]> datos = session
					.createQuery("select e.ename, j.ename from Emp e left join e.emp j ")
					
					.list();

			for (Object[] filas : datos) {
				System.out.println("Emp name: " + filas[0] + " Jefe name: " + filas[1]);
			}

		}
		session.close();
		sessionFactory.close();
	}
}
