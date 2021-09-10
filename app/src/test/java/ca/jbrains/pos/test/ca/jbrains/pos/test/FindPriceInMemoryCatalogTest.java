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

    @Test
    void productNotFound() {
        InMemoryCatalog catalog = new InMemoryCatalog(new HashMap<>());

        Assertions.assertEquals(null, catalog.findPrice("::missing barcode::"));
    }

    public static class InMemoryCatalog {
        private Map<String, Price> pricesByBarcode;

        public InMemoryCatalog(Map<String, Price> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public Price findPrice(String barcode) {
            return pricesByBarcode.get(barcode);
        }
    }
}
