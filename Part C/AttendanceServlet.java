import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/AttendanceServlet")
public class AttendanceServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/studentdb";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "your_password"; // 🔹 Replace with your MySQL password

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Read form data from JSP
        String studentId = request.getParameter("studentId");
        String date = request.getParameter("date");
        String status = request.getParameter("status");

        out.println("<html><body style='font-family: Arial; text-align: center;'>");
        out.println("<h2>Attendance Submission Result</h2>");

        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to DB
            Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            // Prepare SQL Insert
            String query = "INSERT INTO Attendance (StudentID, Date, Status) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(studentId));
            ps.setString(2, date);
            ps.setString(3, status);

            int result = ps.executeUpdate();

            if (result > 0) {
                out.println("<p style='color:green;'>Attendance recorded successfully!</p>");
                out.println("<p><b>Student ID:</b> " + studentId + "</p>");
                out.println("<p><b>Date:</b> " + date + "</p>");
                out.println("<p><b>Status:</b> " + status + "</p>");
            } else {
                out.println("<p style='color:red;'>Failed to record attendance.</p>");
            }

            // Close connections
            ps.close();
            con.close();

        } catch (Exception e) {
            out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
        }

        out.println("<br><a href='attendance.jsp'>Back to Attendance Form</a>");
        out.println("</body></html>");
    }
}
