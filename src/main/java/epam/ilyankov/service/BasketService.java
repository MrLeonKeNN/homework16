package epam.ilyankov.service;

import epam.ilyankov.domain.Product;
import epam.ilyankov.repository.PriceList;
import epam.ilyankov.repository.ProductRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class BasketService {

    private final PriceList priceList;

    public BasketService(PriceList priceList) {
        this.priceList = priceList;
    }

    public Double getAllSum(){
        Double d = 0.0;
        for (Product product: ProductRepository.getProductList()){
            d += product.getCell();
        }
        return d;
    }

    public String getBasketProduct(){
        String html = "";
        List<Product> productList = ProductRepository.getProductList();
        for (Product product : productList) {
            html += String.format("<p>%s %s $</p>\n", product.getName(), product.getCell());

        }
        return html;
    }

    public String getNameUser(HttpServletRequest req, HttpServletResponse resp){
        CellService cellService = new CellService(new PriceList());
        return cellService.getNameUser(req,resp);

    }

}
