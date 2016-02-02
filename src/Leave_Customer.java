

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class Leave_Customer
 */
@WebServlet("/Leave_Customer")
public class Leave_Customer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Leave_Customer() {
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
		String book_in_time;
		String vtype;
		HttpSession session=request.getSession(false);
		PrintWriter out=response.getWriter();
		if(session!=null)
		{
			username=(String) session.getAttribute("username");
			/*out.println("Logged in as "+username);
			request.getRequestDispatcher("link.html").include(request, response);
			out.println("<br><hr>");*/
			response.setContentType("text/html");
			out.print("<!DOCTYPE html><html><head><title>that's my spot.com</title>"
					+ "<link href=\"css/style3.css\" rel=\"stylesheet\" type=\"text/css\" media=\"all\" />"
					+ "<link rel=\"stylesheet\" href=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css\"><link rel=\"css/bootstrap.min.css\"><link href=\"css/bootstrap.css\" rel='stylesheet' type='text/css' /><link rel=\"stylesheet\" type=\"text/css\" href=\"css/animate.css\"/>"
					+ "<script src=\"js/jquery.min.js\"></script>"
					+ " <script src=\"js/bootstrap.min.js\"></script>"
					+ "<script type=\"text/javascript\" src=\"js/wow.js\"></script>"
					+ "<script src=\"js/wow.min.js\"></script>"
					+ "<script>new WOW().init();</script>"
					+ "</head><body>");
			
			
			String park=request.getParameter("park");
			if(park.equals("Cancel Booking"))
			{
				try{
					Connection con=DriverManager.getConnection(url+dbname,uname,pwd);
					java.sql.Statement s=con.createStatement();
					java.sql.Statement s2=con.createStatement();
					out.print("<header><div class=\"container\"><div class=\"logo pull-left animated wow fadeInLeft\"><img src=\"images/logo.jpg\" height=\"80px\"  width=\"65px\" >That's my spot</div></div></header>");
					//	out.println("<div id=\"text\">");

					ResultSet r=s2.executeQuery("select park_num,spot_number,book_in_time,vehicle_type from parking_spot_info where cid="+Integer.parseInt(username));
					int i=s.executeUpdate("delete from parking_spot_info where cid="+Integer.parseInt(username));
					r.next();
					pnum=r.getString(1);
					snum=r.getString(2);
					
				    out.print("<div class=\"banner\"><div class=\"container\"><div class=\"banner-info\">");
					
					
				 //   out.println("You've to now pay "+bill+"<br><hr>");
					if(i==0)
					{
						System.out.println("Delete unsuccesful");
					}
					out.println("<h3>"+snum+" is now empty for park number "+pnum+". Your booking has been successfully Cancelled!<br><hr></h3>");
					
					out.println("<h3><a style=\"color:white;background-color:lightgrey\"    href=\"Sign_in_customer.html\" >Sign In</a><h3></div></div></div>");
					//request.getRequestDispatcher("Sign_in_customer.html").include(request, response);
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
				try{
					Connection con=DriverManager.getConnection(url+dbname,uname,pwd);
					java.sql.Statement s=con.createStatement();
					java.sql.Statement s2=con.createStatement();
					out.print("<header><div class=\"container\"><div class=\"logo pull-left animated wow fadeInLeft\"><img src=\"images/logo.jpg\" height=\"80px\"  width=\"65px\" >That's my spot</div></div></header>");
					//	out.println("<div id=\"text\">");

					ResultSet r=s2.executeQuery("select park_num,spot_number,book_in_time,vehicle_type from parking_spot_info where cid="+Integer.parseInt(username));
					int i=s.executeUpdate("delete from parking_spot_info where cid="+Integer.parseInt(username));
					r.next();
					pnum=r.getString(1);
					snum=r.getString(2);
					book_in_time=r.getString(3);
					vtype=r.getString(4);
					Float cost;
					if(vtype.equals("1"))
					{
					ResultSet r2=s2.executeQuery("select cost_of_car_parkings from parking_lot_info where park_number="+Integer.parseInt(pnum));
					r2.next();
					cost=Float.parseFloat(r2.getString(1));
					}
					else
					{
						ResultSet r2=s2.executeQuery("select cost_of_bike_parkings from parking_lot_info where park_number="+Integer.parseInt(pnum));
						r2.next();
						cost=Float.parseFloat(r2.getString(1));
					}
					Date date=new Date();
				    System.out.println(date.toInstant());
				    String time=date.toString().substring(11, 20);
				    System.out.println(time);
				    int h=Integer.parseInt(time.substring(0,2).toString());
				    System.out.println(h+1);
				    h=h+1;
				    int time_booked=Integer.parseInt(book_in_time.substring(0, 2).toString());
				    float bill=(h-time_booked)*cost;
				    
				    out.print("<div class=\"banner\"><div class=\"container\"><div class=\"banner-info\">");
					
					out.println("<h3>You've to now pay "+bill+"<br><hr>  </h3>");
					
					
				 //   out.println("You've to now pay "+bill+"<br><hr>");
					if(i==0)
					{
						System.out.println("Delete unsuccesful");
					}
					out.println("<h3>"+snum+" is now empty for park number "+pnum+"<br><hr></h3>");
					
					out.println("<h3><a style=\"color:white;background-color:lightgrey\"    href=\"Sign_in_customer.html\" >Sign In</a><h3></div></div></div>");
					//request.getRequestDispatcher("Sign_in_customer.html").include(request, response);
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
