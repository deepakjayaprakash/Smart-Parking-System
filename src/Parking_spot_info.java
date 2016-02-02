

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

import com.mysql.jdbc.ResultSetMetaData;

/**
 * Servlet implementation class Parking_spot_info
 */
@WebServlet("/Parking_spot_info")
public class Parking_spot_info extends HttpServlet {
	String pnum;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Parking_spot_info() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String url="jdbc:mysql://localhost:3306/";
		String dbname="parking_system_db";
		String uname="root";
		String pwd="root";
		String driver="com.mysql.jdbc.Driver";
		String username;
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
		
		
		if(session!=null)
		{
			username=(String) session.getAttribute("username");
			out.print("<header><div class=\"container\"><div class=\"logo pull-left animated wow fadeInLeft\"><img src=\"images/logo.jpg\" height=\"80px\"  width=\"65px\" >That's my spot</div></div></header>");
			out.println("<div id=\"text\">");
			out.println("Logged in as "+username);
			out.println("<a style=\"color:white;float: right;background-color:lightgrey\"    href=\"LogoutServlet\" >Logout</a>");
		//	request.getRequestDispatcher("link.html").include(request, response);
			out.println("<br><hr>");
		pnum= request.getParameter("action");
		response.setContentType("text/html");
		out.println("<html><head><style>.booked{ background-color:orange; }.parked{ background-color:red; }.notbooked{ background-color:green; }</style></head><body>");
		try{
			Connection con=DriverManager.getConnection(url+dbname,uname,pwd);
			Statement s=con.createStatement();
			ResultSet r=s.executeQuery("select * from parking_lot_info where park_number="+Integer.parseInt(pnum));
			String[] psi=new String[10];
			session.setAttribute("park_num", pnum);
			java.sql.ResultSetMetaData rsmd=r.getMetaData();
			
			out.println("<div class=\"images\"> ");
			
			if(r.next())
			{
				for(int i=1;i<=9;i++)
				{
					psi[i]=r.getString(i);
					out.println(rsmd.getColumnName(i)+" : "+psi[i]+"<br>");
				}
				out.println("</div>");
			}
			
			else
			{
				System.out.println("Parking lot info not available!");
			}
			
			out.println("<div class=\"images\"> ");
			out.println("green------>available<br>orange----->booked<br>red----->parked<br>");
			
			out.println("</div>");
			ResultSet r2=s.executeQuery("select spot_number,status from parking_spot_info where park_num="+Integer.parseInt(pnum)+" and vehicle_type=1 order by spot_number");
			out.println("<div class=\"images\"> Car Parkings : ");
			out.println("<form action=\"Select_spot_car\">");
			System.out.println(psi[4]+" : : "+psi[5]);
			int norows=0;
			if(!r2.first())
			{
				norows=1;
			}
			for(int i=1;i<=Integer.parseInt(psi[4]);i++)
			{
				if(!r2.isAfterLast() && norows==0)
				{
				if(i==Integer.parseInt(r2.getString(1)))
				{
					if(r2.getString(2).equals("0"))
					{
						out.println("<button class=\"booked\" type=\"submit\" name=\"park\" value="+i+" disabled>"+i+"</button>");
					}
					else
					{
						out.println("<button class=\"parked\" type=\"submit\" name=\"park\" value="+i+" disabled>"+i+"</button>");
					}
					
					r2.next();
				}
				else
				{
					out.println("<button class=\"notbooked\" type=\"submit\" name=\"park\" value="+i+">"+i+"</button>");
				}
				}
				else
				{
					out.println("<button class=\"notbooked\" type=\"submit\" name=\"park\" value="+i+">"+i+"</button>");	
				}
			}
			out.println("</div>");
			out.println("</form>");
			out.println("<div class=\"images\"> Bike Parkings : ");
			out.println("<form action=\"Select_spot_bike\">");
			r2=s.executeQuery("select spot_number,status from parking_spot_info where park_num="+Integer.parseInt(pnum)+" and vehicle_type=0 order by spot_number");
			norows=0;
			if(!r2.first())
			{
				norows=1;
			}
			for(int i=1;i<=Integer.parseInt(psi[5]);i++)
			{
				if(!r2.isAfterLast() && norows==0)
				{
				if(i==Integer.parseInt(r2.getString(1)))
				{
					if(r2.getString(2).equals("0"))
					{
						out.println("<button class=\"booked\" type=\"submit\" name=\"park\" value="+i+" disabled>"+i+"</button>");
					}
					else
					{
						out.println("<button class=\"parked\" type=\"submit\" name=\"park\" value="+i+" disabled>"+i+"</button>");
					}
					r2.next();
				}
				else
					out.println("<button class=\"notbooked\" type=\"submit\" name=\"park\" value="+i+">"+i+"</button>");
				}
				else
				out.println("<button class=\"notbooked\" type=\"submit\" name=\"park\" value="+i+">"+i+"</button>");
			}
			out.println("</div>");
			out.println("</form>");
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
		out.println("</div>");
		out.println("</body></html>");
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
