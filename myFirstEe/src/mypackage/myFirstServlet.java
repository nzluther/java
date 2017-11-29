package mypackage;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ListSelectionModel;

/**
 * Servlet implementation class myFirstServlet
 */
@WebServlet("/myFirstServlet")
public class myFirstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public myFirstServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("null")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html");
		
		int a = Integer.parseInt(request.getParameter("f1"));
		int b = Integer.parseInt(request.getParameter("f2"));
		int c = a+b;
		ArrayList<String> list;		
		list = db(c);
		
		myPOJO[] arrayofPojo=new myPOJO[list.size()];
		
		for(int i=0; i<list.size();i++) {
			arrayofPojo[i]=new myPOJO(list.get(i));
		}
		
		/* if we want to show result from servlet
		//PrintWriter out = response.getWriter();		
		//out.println("Result is ");
		//out.print (a+b);
		//out.println(db(a+b));

		*/
		
		
		//Calling a servlet to show value in browser
		request.setAttribute("sumResult", c);
		request.setAttribute("arrayofPojo", arrayofPojo);
		request.getRequestDispatcher("myFirstServlet_output.jsp").forward(request, response);
		
		//response.getWriter().append("Served at: ").append(request.getContextPath()).append("\n Happy Learning");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public ArrayList<String> db (int n) {
		
		ArrayList<String> resultList = new ArrayList<String>();
		
		try {
			
		    Class.forName("oracle.jdbc.driver.OracleDriver");
		    //Connection con= DriverManager.getConnection("jdbc:oracle:thin:@202.65.11.56:1521:mghgroup","sales","sales");
		    Connection con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sales","sales");
		    
		    Statement statement=null;
		    statement=con.createStatement();
		    //String sql= ("insert into test_luther "+"(ID)"+" values"+" (200)");
		    
		    String sql= ("insert into luther_test "+"(ID)"+" values"+" ("+n+")");
		    statement.executeUpdate(sql);
		    
		    ResultSet rs = null;
		    rs = statement.executeQuery("select ID from luther_test");
		    
		    try {
				while (rs.next()) {
					resultList.add(rs.getString("ID"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		    rs.close();
		    statement.close();		   
		    con.close();
		    
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			//System.out.println("Faltu Java");
		}
		
		return resultList;

	}
		

}


