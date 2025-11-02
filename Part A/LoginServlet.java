import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Set content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieve form data
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Simple validation (can be replaced with DB validation)
        if ("admin".equals(username) && "12345".equals(password)) {
            out.println("<html><body style='text-align:center;'>");
            out.println("<h2>Welcome, " + username + "!</h2>");
            out.println("<p>Login successful.</p>");
            out.println("</body></html>");
        } else {
            out.println("<html><body style='text-align:center; color:red;'>");
            out.println("<h3>Invalid username or password!</h3>");
            out.println("<a href='login.html'>Try Again</a>");
            out.println("</body></html>");
        }
    }
}
