

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.org.apache.xml.internal.security.utils.RFC2253Parser;

/**
 * Servlet implementation class Sign_in_Customer
 */
@WebServlet("/Sign_in_Customer")
public class Sign_in_Customer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sign_in_Customer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String url="jdbc:mysql://localhost:3306/";
		String dbname="parking_system_db";
		String uname="root";
		String pwd="root";
		String driver="com.mysql.jdbc.Driver";
		 String cust_uname=request.getParameter("uname");
	     String cust_pwd=request.getParameter("pwd");
		try
		{
		Class.forName(driver).newInstance();
		Connection con=DriverManager.getConnection(url+dbname,uname,pwd);
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
		Statement stmt=con.createStatement();
		ResultSet r=stmt.executeQuery("select * from customer_info");
		int found=0;
		HttpSession session = null;
		
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
		
		while(r.next())
		{
			String a=r.getString(1);
			String b=r.getString(5);
			if(cust_uname.equals(a) && cust_pwd.equals(b))
			{
				found=1;
				session=request.getSession();
				String c=r.getString(2);
				String d=r.getString(3);
				String e=r.getString(4);
				String vtype;
				if(e.equals("0"))
					vtype="Bike";
				else
					vtype="Car";
				
				Statement stmt2=con.createStatement();
				Statement stmt3=con.createStatement();
				session.setAttribute("username", a);
				out.println("<div id=\"text\">");
		out.println("Logged in as "+ cust_uname);
		out.println("<a style=\"color:white;float: right;background-color:lightgrey\"    href=\"LogoutServlet\" >Logout</a>");
		//request.getRequestDispatcher("link.html").include(request, response);
	
		out.println(" <br><hr> Customer Name : "+c+"<br>Vechicle Number : "+d+"<br>Vehicle type : "+vtype+"<br><hr>");
		int status=0;
		String psi[]= new String[8];
		ResultSet r2=stmt.executeQuery("select * from parking_spot_info where cid='"+a+"'");
		if(r2.next())	
		{
			
			for(int j=1;j<=7;j++)
				psi[j]=r2.getString(j);
			status=1;
		}
			if(status==0)
				{
					Statement s3=con.createStatement();
					ResultSet r3=s3.executeQuery("select location_name,Code from locations");
					String lname,lcode = null;
					out.println("You've not booked or parked in a parking lot yet.Select from the parking lots listed below : <br><hr>");
					out.println("<form action=\"Locations_spot_info\" method=\"get\">");
					
					while(r3.next())
					{
						lname=r3.getString(1);
						lcode=r3.getString(2);
						out.println(" <div class=\"container\"> <div class=\"col-md-6 images wow fadeInRight\" data-wow-delay=\"0.5s\">");
						out.println("<input type=\"image\" src=\"images/lot"+(Integer.parseInt(lcode)+1)+".jpg\" height=\"200px\" width=\"200px\"  value=\""+lcode+"\" name=\"action\"><br></div>");
						
						out.println("<div class=\"col-md-6 images wow fadeInRight\" data-wow-delay=\"0.5s\">");
					
						ResultSet r1=stmt3.executeQuery("select location_name,number_of_parking_lots from locations where code="+lcode);
						r1.next();
						out.println(lname+"<br>"+"</div>");
						out.println("Number of parking lots in "+r1.getString(1)+"is: " +r1.getString(2));
						
						Statement s4=con.createStatement();
						ResultSet r4=s4.executeQuery("select parking_lot_name,park_number from parking_lot_info where location_code="+lcode);
						String pname;
						
						while(r4.next())
						{
							pname=r4.getString(1);
							
							out.println("<br>"+pname);
						}
						
						out.print("");
						
					
						
						out.println("</div>");
					}
					out.println("</form");
					
				}
				else
				{
					String booked="parked";
					if(Integer.parseInt(psi[7])==0)
					{
						booked="booked";
					}
					if(psi[3].equals("0"))
						vtype="Bike";
					else
						vtype="Car";
						out.println("You've "+booked+" at<br><hr> Spot_number : "+ psi[1]+"<br>Park number : "+psi[2]+"<br>Book date : "+psi[5]+"<br>Book in time : "+psi[6]+"<br>Vehicle Type : "+vtype);
						if(Integer.parseInt(psi[7])==0)
						out.println("<form action=\"Leave_Customer\"><input type=\"submit\" name=\"park\" value=\"Cancel Booking\"></form>");
						else
							out.println("<form action=\"Leave_Customer\"><input type=\"submit\" name=\"park\" value=\"Leave now!\"></form>");
							
				}
			out.print("</div>");
				out.println("</body></html>");
				con.close();
				break;
			}
		}
			if(found==0)
			{
		
				out.print("Sorry,Enter valid username or password!");
				response.sendRedirect("Sign_in_customer.html");
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
