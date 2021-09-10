package ca.jbrains.pos.test.ca.jbrains.pos.test;

import ca.jbrains.pos.test.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class FindPriceInMemoryCatalogTest {
    @Test
    void productFound() {
        InMemoryCatalog catalog = new InMemoryCatalog(new HashMap<>() {{
            put("::barcode with matching price::", Price.euroCents(1250));
        }});

        Assertions.assertEquals(Price.euroCents(1250), catalog.findPrice("::barcode with matching price::"));
    }

    public static class InMemoryCatalog {
        public InMemoryCatalog(Map<String, Price> pricesByBarcode) {
        }

        public Price findPrice(String barcode) {
            return Price.euroCents(1250);
        }
    }
}
