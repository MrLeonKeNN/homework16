package epam.ilyankov.controller;

import epam.ilyankov.repository.PriceList;
import epam.ilyankov.service.CellService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Cell")
public class Cell extends HttpServlet {

    private CellService cellService;

    @Override
    public void init() throws ServletException {
        cellService = new CellService(new PriceList());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cookie cookie = new Cookie("option", "on");
        cookie.setMaxAge(10);
        resp.addCookie(cookie);

        cellService.addProduct(req);

        String name = cellService.getNameUser(req, resp);

        String price = cellService.getPriceStringHtml();

        String option = cellService.getProductStringHtmlBasket();

        resp.getWriter().write(
                "<style>\n" +
                        "    p{\n" +
                        "        margin: 0;\n" +
                        "    }\n" +
                        "</style>\n" +
                        "<body>\n" +
                        "    <div style=\"display: flex; flex-direction: column;width: 10%; margin: auto;\">" +
                        "       <form action = \"Cell\" method=\"POST\">\n" +
                        "         <p style = \" margin:10px 0 10px 0;\">Hello " + name + "</p>\n" +
                        "         <p>Make you order</p>\n"
                                    + price
                                    + option +
                        "         <button type=\"submit\"> Add item </button>" +
                        "       </form>" +
                        "   </div>\n" +
                        "     <form style = \"text-align: center;margin-left:100px;margin-top:-37px\"  method = \"POST\" action = \"Basket\">" +
                        "         <button>Submit</button>" +
                        "       </form>" +
                        "</body>");
    }
}
