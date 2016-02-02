

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
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
 * Servlet implementation class Locations_spot_info
 */
@WebServlet("/Locations_spot_info")
public class Locations_spot_info extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	String lcode_string;
	int lcode;
	public Locations_spot_info()
	{ super();
		
	}
  /*  public Locations_spot_info(String l) {
        super();
        lcode=l;
        // TODO Auto-generated constructor stub
    }*/

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url="jdbc:mysql://localhost:3306/";
		String dbname="parking_system_db";
		String uname="root";
		String pwd="root";
		String driver="com.mysql.jdbc.Driver";
		String username;
		lcode_string= request.getParameter("action");
		HttpSession session=request.getSession(false);
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
		if(session!=null)
		{
			username=(String) session.getAttribute("username");
		

			out.print("<!DOCTYPE html><html><head><title>that's my spot.com</title>"
					+ "<link href=\"css/style3.css\" rel=\"stylesheet\" type=\"text/css\" media=\"all\" />"
					+ "<link rel=\"stylesheet\" href=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css\"><link rel=\"css/bootstrap.min.css\"><link href=\"css/bootstrap.css\" rel='stylesheet' type='text/css' /><link rel=\"stylesheet\" type=\"text/css\" href=\"css/animate.css\"/>"
					+ "<script src=\"js/jquery.min.js\"></script>"
					+ " <script src=\"js/bootstrap.min.js\"></script>"
					+ "<script type=\"text/javascript\" src=\"js/wow.js\"></script>"
					+ "<script src=\"js/wow.min.js\"></script>"
					+ "<script>new WOW().init();</script>"
					+ "</head><body>");
			
			
		try{
		Connection con=DriverManager.getConnection(url+dbname,uname,pwd);
		lcode=Integer.parseInt(lcode_string);
		
		Statement stmt=con.createStatement();
		
		
		out.print("<header><div class=\"container\"><div class=\"logo pull-left animated wow fadeInLeft\"><img src=\"images/logo.jpg\" height=\"80px\"  width=\"65px\" >That's my spot</div></div></header>");
		out.println("<div id=\"text\">");
		out.println("Logged in as "+username);

		out.println("<a style=\"color:white;float: right;background-color:lightgrey\"    href=\"LogoutServlet\" >Logout</a>");
		//request.getRequestDispatcher("link.html").include(request, response);
		

		
		ResultSet r=stmt.executeQuery("select location_name,number_of_parking_lots from locations where code="+lcode);
		r.next();
		out.println("<br> number of parking lots in "+r.getString(1)+":"+r.getString(2));
		Statement s3=con.createStatement();
		ResultSet r3=s3.executeQuery("select parking_lot_name,park_number from parking_lot_info where location_code="+lcode);
		String pname,pnum;
		out.println("<form action=\"Parking_spot_info\" method=\"get\">");
		while(r3.next())
		{
			pname=r3.getString(1);
			pnum=r3.getString(2);
			out.println(" <div class=\"container\"> <div class=\"col-md-6 images wow fadeInRight\" data-wow-delay=\"0.5s\">");
			out.println("<input type=\"image\" src=\"images/lot"+((Integer.parseInt(pnum)+1)%6+2)+".jpg\" height=\"200px\" width=\"200px\"  value=\""+pnum+"\" name=\"action\"><br></div>");
			out.println("<div class=\"col-md-6 images wow fadeInRight\" data-wow-delay=\"0.5s\">");
			Statement s=con.createStatement();
			ResultSet r2=s.executeQuery("select * from parking_lot_info where park_number="+Integer.parseInt(pnum));
			String[] psi=new String[10];
			session.setAttribute("park_num", pnum);
			java.sql.ResultSetMetaData rsmd=r2.getMetaData();
			if(r2.next())
			{
				for(int i=1;i<=9;i++)
				{
					psi[i]=r2.getString(i);
					out.println(rsmd.getColumnName(i)+" : "+psi[i]+"<br>");
				}
				
			}
			
			out.print("</div>");
			
		}
		out.print("</div>");
		out.println("</form");
		//out.println("</div>");
		out.println("</body></html>");
		con.close();
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
		else
		{
			//out.println("\nPlease login first!<br>");
			//out.print("<button type=\"submit\" action=\"Sign_in_customer.html\">Sign in</button>");
			response.sendRedirect("Sign_in_customer.html");
			//request.getRequestDispatcher("Sign_in_customer.html").include(request, response);
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
