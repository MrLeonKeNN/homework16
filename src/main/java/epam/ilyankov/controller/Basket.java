package epam.ilyankov.controller;

import epam.ilyankov.repository.PriceList;
import epam.ilyankov.service.BasketService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Basket")
public class Basket extends HttpServlet {

    private BasketService basketService;

    @Override
    public void init() throws ServletException {
        basketService = new BasketService(new PriceList());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Double sumPrice = basketService.getAllSum();

        String order = basketService.getBasketProduct();

        String name = basketService.getNameUser(req,resp);

        resp.getWriter().write(
                " <div style=\"display: flex; flex-direction:column; align-items: center;\">" +
                            "<p> Dear " + name + ", your order: </p>" +
                            order+
                            "<p> Total $ " +
                             sumPrice +
                             "$</p>"+
                        "</div>");
    }
}
