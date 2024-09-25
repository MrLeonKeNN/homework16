package epam.ilyankov.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class Welcome extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Online shop12312312312 3123</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <form method=\"POST\" action = \"Cell\">\n" +
                "        <div style=\"display: flex; flex-direction: column;width: 15%; margin: auto;\">\n" +
                "            <p>Welcome to Online sho3d12312312p</p>\n" +
                "            <input type=\"text\" placeholder=\"enter you name\" name=\"name\" id=\"\">\n" +
                "<p><input type=\"checkbox\" name=\"option\" >I agree with the terms of service</p>" +
                "            <button>Enter</button>\n" +
                "        </div>\n" +
                "    </form>\n" +
                "</body>\n" +
                "</html>");
    }
}
