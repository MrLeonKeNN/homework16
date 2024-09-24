package epam.ilyankov.repository;

import epam.ilyankov.domain.Product;

import java.util.List;

public class PriceList {

    private final List<Product> productList;

    public PriceList() {
        this.productList = List.of(
                Product.builder().name("Book").cell(12.5).build(),
                Product.builder().name("Cup").cell(13.5).build(),
                Product.builder().name("Bicycle").cell(300.0).build(),
                Product.builder().name("Soldering iron").cell(17.7).build()
        );
    }

    public List<Product> getProductList() {
        return productList;
    }
}
