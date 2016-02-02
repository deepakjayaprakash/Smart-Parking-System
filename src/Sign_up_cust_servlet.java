

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Sign_up_cust_servlet
 */
@WebServlet("/Sign_up_cust_servlet")
public class Sign_up_cust_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sign_up_cust_servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url="jdbc:mysql://localhost:3306/";
		String dbname="parking_system_db";
		String uname="root";
		String pwd="root";
		String driver="com.mysql.jdbc.Driver";
		 String name=request.getParameter("cust_name");
	     String V_number=request.getParameter("V_number");
	     String V_type=request.getParameter("V_type");
	     String cust_pwd=request.getParameter("cust_pwd");
	     int vtype;
	     HttpSession session=request.getSession(false);
	 	PrintWriter out=response.getWriter();
	     out.print("<!DOCTYPE html><html><head><title>that's my spot.com</title>"
					+ "<link href=\"css/style3.css\" rel=\"stylesheet\" type=\"text/css\" media=\"all\" />"
					+ "<link rel=\"stylesheet\" href=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css\"><link rel=\"css/bootstrap.min.css\"><link href=\"css/bootstrap.css\" rel='stylesheet' type='text/css' /><link rel=\"stylesheet\" type=\"text/css\" href=\"css/animate.css\"/>"
					+ "<script src=\"js/jquery.min.js\"></script>"
					+ " <script src=\"js/bootstrap.min.js\"></script>"
					+ "<script type=\"text/javascript\" src=\"js/wow.js\"></script>"
					+ "<script src=\"js/wow.min.js\"></script>"
					+ "<script>new WOW().init();</script>"
					+ "</head><body>");
			
			out.print("<header><div class=\"container\"><div class=\"logo pull-left animated wow fadeInLeft\"><img src=\"images/logo.jpg\" height=\"80px\"  width=\"65px\" >That's my spot</div></div></header>");
			
			
			

		try
		{
		Class.forName(driver).newInstance();
		Connection con=DriverManager.getConnection(url+dbname,uname,pwd);
	
		response.setContentType("text/html");
		vtype=Integer.parseInt(V_type);
		if(vtype!=0 & vtype!=1)
		{
			out.println( name + " not added! Enter either 0 or 1 in vehichle type. Enter valid credentials and try again!<br><a href=\"Sign_up_customer.html\">Sign up</a></body></html>");
			response.sendRedirect("Sign_up_customer.html");
		}
			else
		{
		PreparedStatement ps=con.prepareStatement("insert into customer_info values(null,?,?,?,?)");
		ps.setString(1, name);
		ps.setString(2, V_number);
		ps.setInt(3, vtype);
		ps.setString(4, cust_pwd);
		int i=ps.executeUpdate();
		Statement stmt=con.createStatement();
		ResultSet r=stmt.executeQuery("select max(customer_id) from customer_info");
		r.next();
		String x=r.getString(1);
		int cust_id=Integer.parseInt(x);
		out.println("<div id=\"text\">");
		out.println( name + " added! Your customer ID is <span style=\"color:blue;font-size:30px;font-weight:bold\">"+ cust_id +" </span>.Sign in with this customer ID as username and the entered password to login !<br><br><a style=\"color: orange;\" href=\"Sign_in_customer.html\">Login</a></body></html>");
				out.println("</div>");
				session.invalidate();
		}
		con.close();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
