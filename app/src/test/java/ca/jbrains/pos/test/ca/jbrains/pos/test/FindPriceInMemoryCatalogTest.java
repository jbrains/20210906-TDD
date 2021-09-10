package ca.jbrains.pos.test.ca.jbrains.pos.test;

import ca.jbrains.pos.test.Catalog;
import ca.jbrains.pos.test.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class FindPriceInMemoryCatalogTest {
    @Test
    void productFound() {
        String barcode = "::barcode with matching price::";
        Price matchingPrice = Price.euroCents(1250);

        Catalog catalog = catalogWith(barcode, matchingPrice);
        Assertions.assertEquals(matchingPrice, catalog.findPrice(barcode));
    }

    private Catalog catalogWith(final String barcode, final Price matchingPrice) {
        return new InMemoryCatalog(new HashMap<>() {{
            put(barcode, matchingPrice);
        }});
    }

    @Test
    void productNotFound() {
        Catalog catalog = emptyCatalog();
        Assertions.assertEquals(null, catalog.findPrice("::missing barcode::"));
    }

    private Catalog emptyCatalog() {
        return new InMemoryCatalog(new HashMap<>());
    }

    public static class InMemoryCatalog implements Catalog {
        private Map<String, Price> pricesByBarcode;

        public InMemoryCatalog(Map<String, Price> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public Price findPrice(String barcode) {
            return pricesByBarcode.get(barcode);
        }
    }
}
