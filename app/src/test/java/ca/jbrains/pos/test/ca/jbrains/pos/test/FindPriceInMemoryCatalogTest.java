package ca.jbrains.pos.test.ca.jbrains.pos.test;

import ca.jbrains.pos.test.Catalog;
import ca.jbrains.pos.test.Price;

import java.util.HashMap;
import java.util.Map;

public class FindPriceInMemoryCatalogTest extends FindPriceInCatalogContract {
    @Override
    protected Catalog catalogWith(final String barcode, final Price matchingPrice) {
        return new InMemoryCatalog(new HashMap<>() {{
            put(barcode, matchingPrice);
        }});
    }

    @Override
    protected Catalog emptyCatalog() {
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
