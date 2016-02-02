

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
 * Servlet implementation class Select_spot_bike
 */
@WebServlet("/Select_spot_bike")
public class Select_spot_bike extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Select_spot_bike() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
				+ "<script> $(document).ready(function(){    $(\"#flip\").click(function(){"
				+ "$(\"#panel\").slideToggle(\"slow\");    });});$(document).ready(function(){"
				+ "    $(\"#flip2\").click(function(){        $(\"#panel2\").slideToggle(\"slow\");    });});</script>"
		
				+ "</head><body>");
		
		
		if(session!=null)
		{
			username=(String) session.getAttribute("username");
		snum= request.getParameter("park");
		pnum=(String) session.getAttribute("park_num");
		response.setContentType("text/html");
		out.print("<header><div class=\"container\"><div class=\"logo pull-left animated wow fadeInLeft\"><img src=\"images/logo.jpg\" height=\"80px\"  width=\"65px\" >That's my spot</div></div></header>");
		out.println("<div id=\"text\">");
		out.println("Logged in as "+username);
		out.println("<a style=\"color:white;float: right;background-color:lightgrey\"    href=\"LogoutServlet\" >Logout</a><br>");
		out.println("<br> spot number selected in park number:"+pnum+": "+snum);
		session.setAttribute("park_num",pnum);
		session.setAttribute("park",snum);
		 
		out.println("<div id=\"flip\" >Park Now</div><div id=\"panel\">");
		out.println("<form action=\"Final_Bike_Book\">"
				+ "<input type=\"submit\" name=\"parking\"  value=\"park\"></form>");
		out.println("</div></div>");
		
		
		out.println("<div id=\"flip2\" >Advanced Booking</div><div id=\"panel2\">");
		out.println("<form action=\"Final_Bike_Book\">");
		Date date=new Date();
	    System.out.println(date.toInstant());
	    String day=date.toString().substring(8, 10);
	    int dat_int=Integer.parseInt(day);
	    String time=date.toString().substring(11, 20);
	    System.out.println(time);
	    int h=Integer.parseInt(time.substring(0,2).toString());
	    System.out.println(h+1);
	    h=h+1;
	    out.println("Date : <select name=\"date\">");
	    out.println("<option value=\""+dat_int+"\">"+dat_int+"</option>");
	    out.println("<option value=\""+(dat_int+1)+"\">"+(dat_int+1)+"</option>");
	    out.println("</select><br>");
	    out.println("Hour : <select name=\"hour\">");
	    for(int i=0;i<=23;i++)
	    out.println("<option value=\""+i+"\">"+i+"</option>");
	    out.println("</select><br>");
		out.println("<input type=\"submit\" name=\"parking\" value=\"book\">");
		out.println("</form></div></div>");
		
		
		out.print("</body></html>");

		
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
