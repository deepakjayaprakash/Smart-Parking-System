

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Final_Bike_Book
 */
@WebServlet("/Final_Bike_Book")
public class Final_Bike_Book extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Final_Bike_Book() {
        super();
        // TODO Auto-generated constructor stub
    }

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
		String pnum,snum;
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
			String park=request.getParameter("parking");
			if(park.equals("park"))
			{
				snum= (String) session.getAttribute("park");
				pnum=(String) session.getAttribute("park_num");
				response.setContentType("text/html");
				
				out.print("<header><div class=\"container\"><div class=\"logo pull-left animated wow fadeInLeft\"><img src=\"images/logo.jpg\" height=\"80px\"  width=\"65px\" >That's my spot</div></div></header>");
				out.println("<div id=\"text\">");
				out.println("Logged in as "+username);

				out.println("<a style=\"color: orange; float: right;background-color:lightgrey\"    href=\"LogoutServlet\" >Logout</a>");
				
				out.println("\nBooking for "+username+" is : <br><hr>");
			
				try{
					Connection con=DriverManager.getConnection(url+dbname,uname,pwd);
					java.sql.PreparedStatement s=con.prepareStatement("insert into parking_spot_info values(?,?,?,?,now(),curtime(),1)");
					s.setInt(1, Integer.parseInt(snum));
					s.setInt(2, Integer.parseInt(pnum));
					s.setInt(3,0);
					s.setInt(4, Integer.parseInt(username));
					int i=s.executeUpdate();
					if(i==0)
					{
						System.out.println("Book unsuccesful");
					}
					String booked="parked",vtype;
					String[] psi=new String[8];
					Statement stmt=con.createStatement();
					ResultSet r2=stmt.executeQuery("select * from parking_spot_info where cid='"+username+"'");
					if(r2.next())
					{
						
						for(int j=1;j<=7;j++)
							psi[j]=r2.getString(j);
					}
					if(Integer.parseInt(psi[7])==0)
					{
						booked="booked";
					}
					if(psi[3].equals("0"))
						vtype="Bike";
					else
						vtype="Car";
						out.println("You've "+booked+" at<br><hr> Spot_number : "+ psi[1]+"<br>Park number : "+psi[2]+"<br>Book date : "+psi[5]+"<br>Book in time : "+psi[6]+"<br>Vehicle Type : "+vtype);
					out.println("<br><hr>Login again when you want to leave the parking spot!");
					session.invalidate();
					
					out.println("<a style=\"color:orange; href=\"Sign_in_customer.html\">Sign in</a>");
					//request.getRequestDispatcher("Sign_in_customer.html").include(request, response);
				//	out.println("</body></html>");
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
				out.print("</div>");
				out.println("</body></html>");
			}
			else
			{
				String date=request.getParameter("date");
				String hour=request.getParameter("hour");
				snum= (String) session.getAttribute("park");
				pnum=(String) session.getAttribute("park_num");
				response.setContentType("text/html");
				
				out.print("<header><div class=\"container\"><div class=\"logo pull-left animated wow fadeInLeft\"><img src=\"images/logo.jpg\" height=\"80px\"  width=\"65px\" >That's my spot</div></div></header>");
				out.println("<div id=\"text\">");
				out.println("Logged in as "+username);

				//out.println("<a style=\"color:orange; float: right;background-color:lightgrey\"    href=\"LogoutServlet\" >Logout</a>");
				
				out.println("\nBooking for "+username+" is : <br><hr>");
			
				try{
					Date date1=new Date();
				    String date_inp=date1.toInstant().toString().substring(0, 10);
				    StringBuffer temp=new StringBuffer(date_inp);
				    temp.replace(8, 10, date);
				    System.out.println("In final bike book : "+date1.toInstant());
				    System.out.println("In final bike book : "+date1);
				    System.out.println("In final bike book : "+temp.toString());
				    System.out.println("In final bike book : "+hour);
				    
					Connection con=DriverManager.getConnection(url+dbname,uname,pwd);
					java.sql.PreparedStatement s=con.prepareStatement("insert into parking_spot_info values(?,?,?,?,'"+temp.toString()+"','"+hour+":00:00',0)");
					s.setInt(1, Integer.parseInt(snum));
					s.setInt(2, Integer.parseInt(pnum));
					s.setInt(3, 0);
					s.setInt(4, Integer.parseInt(username));
					int i=s.executeUpdate();
					if(i==0)
					{
						System.out.println("Book unsuccesful");
					}
					String booked="parked",vtype;
					String[] psi=new String[8];
					Statement stmt=con.createStatement();
					ResultSet r2=stmt.executeQuery("select * from parking_spot_info where cid='"+username+"'");
					if(r2.next())
					{
						
						for(int j=1;j<=7;j++)
							psi[j]=r2.getString(j);
					}
					if(Integer.parseInt(psi[7])==0)
					{
						booked="booked";
					}
					if(psi[3].equals("0"))
						vtype="Bike";
					else
						vtype="Car";
						out.println("You've "+booked+" at<br><hr> Spot_number : "+ psi[1]+"<br>Park number : "+psi[2]+"<br>Book date : "+psi[5]+"<br>Book in time : "+psi[6]+"<br>Vehicle Type : "+vtype);
					out.println("<br><hr>Login again when you want to leave the parking spot!");
					session.invalidate();
					
					out.println("<a href=\"Sign_in_customer.html\">Sign in</a>");
					//request.getRequestDispatcher("Sign_in_customer.html").include(request, response);
				//	out.println("</body></html>");
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
				out.print("</div>");
				out.println("</body></html>");
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
