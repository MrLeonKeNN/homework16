package epam.ilyankov.repository;

import epam.ilyankov.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private static List<Product> productList;

    static {
        productList = new ArrayList<>();
    }

    public static List<Product> getProductList() {
        return productList;
    }


}
