package epam.ilyankov.service;

import epam.ilyankov.domain.Product;
import epam.ilyankov.repository.PriceList;
import epam.ilyankov.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class CellServiceTest {

    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private PriceList priceList;

    private CellService cellService;

    @BeforeEach
    public void init() {
        cellService = new CellService(priceList);
    }


    @Test
    void getProductStringBasket_Test_Positive() {
        List<Product> productList = List.of(
                Product.builder().name("Book").cell(12.5).build(),
                Product.builder().name("Cup").cell(13.5).build(),
                Product.builder().name("Bicycle").cell(300.0).build(),
                Product.builder().name("Soldering iron").cell(17.7).build()
        );

        String actual;

        try (MockedStatic<ProductRepository> mockedStatic = Mockito.mockStatic(ProductRepository.class);) {
            mockedStatic.when(ProductRepository::getProductList).thenReturn(productList);
            actual = cellService.getProductStringHtmlBasket();
        }

        String expected = "<p>1) Book 12.5 $</p>\n" +
                "<p>2) Cup 13.5 $</p>\n" +
                "<p>3) Bicycle 300.0 $</p>\n" +
                "<p>4) Soldering iron 17.7 $</p>\n";

        assertEquals(expected, actual);
    }

    @Test
    void getProductStringBasket_Test_Positive_Negative() {
        String actual;
        try (MockedStatic<ProductRepository> mockedStatic = Mockito.mockStatic(ProductRepository.class);) {
            mockedStatic.when(ProductRepository::getProductList).thenReturn(null);
            actual = cellService.getProductStringHtmlBasket();
        }
        String expected = "";

        assertEquals(expected, actual);
    }


    @Test
    void getPriceString_Test_Positive() {
        List<Product> productList = List.of(
                Product.builder().name("Book").cell(12.5).build(),
                Product.builder().name("Cup").cell(13.5).build(),
                Product.builder().name("Bicycle").cell(300.0).build(),
                Product.builder().name("Soldering iron").cell(17.7).build()
        );

        when(priceList.getProductList()).thenReturn(productList);

        String expected = "<select name=\"product\" id=\"\">\n" +
                "<option value = \"Book\">Book (12.5 $)</option>\n" +
                "<option value = \"Cup\">Cup (13.5 $)</option>\n" +
                "<option value = \"Bicycle\">Bicycle (300.0 $)</option>\n" +
                "<option value = \"Soldering iron\">Soldering iron (17.7 $)</option>\n" +
                "</select>";

        String actual = cellService.getPriceStringHtml();

        assertEquals(expected, actual);
    }

    @Test
    void getPriceString_Test_Negative() {

        when(priceList.getProductList()).thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            cellService.getPriceStringHtml();
        });
    }

    @Test
    void addProduct_Test_Positive() {
        List<Product> productList = List.of(
                Product.builder().name("Book").cell(12.5).build(),
                Product.builder().name("Cup").cell(13.5).build(),
                Product.builder().name("Bicycle").cell(300.0).build(),
                Product.builder().name("Soldering iron").cell(17.7).build()
        );

        List<Product> expected = List.of(
                Product.builder().name("Cup").cell(13.5).build());

        when(priceList.getProductList()).thenReturn(productList);

        when(req.getParameter(any())).thenReturn("Cup");

        cellService.addProduct(req);

        List<Product> actual = ProductRepository.getProductList();

        verify(priceList, times(1)).getProductList();

        assertEquals(expected, actual);
    }

    @Test
    void addProduct_Test_Positive_Negative() {
        when(priceList.getProductList()).thenReturn(null);

        when(req.getParameter(any())).thenReturn("Cup");

        cellService.addProduct(req);
    }

    @Test
    void getNameUser_Test_Positive_Req_GetName() {
        when(req.getParameter(any())).thenReturn("Alisa");

        String expected = "Alisa";

        String actual = cellService.getNameUser(req, resp);

        assertEquals(expected, actual);
    }

    @Test
    void getNameUser_Test_Positive_Cookie() {
        Cookie cookie = new Cookie("name", "Alisa");

        Cookie[] cookies = {cookie};

        when(req.getParameter(any())).thenReturn(null);

        when(req.getCookies()).thenReturn(cookies);

        String expected = "Alisa";

        String actual = cellService.getNameUser(req, resp);

        assertEquals(expected, actual);
    }
}