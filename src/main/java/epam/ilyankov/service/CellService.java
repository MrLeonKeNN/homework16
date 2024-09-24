package epam.ilyankov.service;

import epam.ilyankov.domain.Product;
import epam.ilyankov.repository.PriceList;
import epam.ilyankov.repository.ProductRepository;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CellService {

    private final PriceList priceList;

    public CellService(PriceList priceList) {
        this.priceList = priceList;
    }

    public String getProductStringHtmlBasket() {
        String html = "";
        List<Product> productList = ProductRepository.getProductList();
        if (productList == null) {
            return "";
        }
        int count = 1;
        for (Product product : productList) {
            html += String.format("<p>%d) %s %s $</p>\n", count, product.getName(), product.getCell());
            count++;
        }
        return html;
    }

    public String getPriceStringHtml() {
        String html = "<select name=\"product\" id=\"\">\n";
        List<Product> productList = priceList.getProductList();
        for (Product product : productList) {
            html += String.format("<option value = \"%s\">%s (%s $)</option>\n", product.getName(), product.getName(), product.getCell());
        }
        html += "</select>";
        return html;
    }

    public void addProduct(HttpServletRequest req) {
        List<Product> productList = priceList.getProductList();
        String nameProduct = req.getParameter("product");
        if (nameProduct == null || productList == null) {
            return;
        }

        Product productBasket = null;
        for (Product product : productList) {
            if (product.getName().equals(nameProduct)) {
                productBasket = Product.builder().name(nameProduct).cell(product.getCell()).build();
            }
        }
        List<Product> productCell = ProductRepository.getProductList();
        productCell.add(productBasket);
    }

    public String getNameUser(HttpServletRequest req, HttpServletResponse resp){
        String name = null;
        if (req.getParameter("name") != null) {
            name = req.getParameter("name");
            Cookie cookie = new Cookie("name", name);
            resp.addCookie(cookie);
        } else {
            Cookie[] cookies = req.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("name")) {
                    name = cookie.getValue();
                }
            }
        }
        return name;
    }
}
