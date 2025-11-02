import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {

    // Database credentials
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/companydb";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "your_password"; // 🔹 Replace with your MySQL password

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String empIdParam = request.getParameter("empid");
        String viewAll = request.getParameter("viewAll");

        out.println("<html><body style='font-family: Arial;'>");
        out.println("<h2 style='text-align:center;'>Employee Records</h2>");

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            PreparedStatement ps;
            ResultSet rs;

            if (viewAll != null) {
                // Fetch all employees
                ps = con.prepareStatement("SELECT * FROM Employee");
                rs = ps.executeQuery();
            } else if (empIdParam != null && !empIdParam.isEmpty()) {
                // Fetch employee by ID
                ps = con.prepareStatement("SELECT * FROM Employee WHERE EmpID = ?");
                ps.setInt(1, Integer.parseInt(empIdParam));
                rs = ps.executeQuery();
            } else {
                out.println("<p style='color:red; text-align:center;'>Please enter an Employee ID or click 'View All'.</p>");
                out.println("</body></html>");
                return;
            }

            // Display results
            out.println("<table border='1' align='center' cellpadding='8'>");
            out.println("<tr><th>EmpID</th><th>Name</th><th>Salary</th></tr>");

            boolean found = false;
            while (rs.next()) {
                found = true;
                out.println("<tr>");
                out.println("<td>" + rs.getInt("EmpID") + "</td>");
                out.println("<td>" + rs.getString("Name") + "</td>");
                out.println("<td>" + rs.getDouble("Salary") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");

            if (!found) {
                out.println("<p style='text-align:center; color:red;'>No employee found with the given ID.</p>");
            }

            // Close resources
            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            out.println("<p style='color:red; text-align:center;'>Error: " + e.getMessage() + "</p>");
        }

        out.println("<br><div style='text-align:center;'><a href='employee.html'>Back to Search</a></div>");
        out.println("</body></html>");
    }
}
