package ca.jbrains.pos.test;

import ca.jbrains.pos.test.Catalog;
import ca.jbrains.pos.test.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class FindPriceInCatalogContract {
    @Test
    void productFound() {
        String barcode = "::barcode with matching price::";
        Price matchingPrice = Price.euroCents(1250);

        Catalog catalog = catalogWith(barcode, matchingPrice);
        Assertions.assertEquals(matchingPrice, catalog.findPrice(barcode));
    }

    protected abstract Catalog catalogWith(String barcode, Price matchingPrice);

    @Test
    void productNotFound() {
        Catalog catalog = catalogWithout("::missing barcode::");
        Assertions.assertEquals(null, catalog.findPrice("::missing barcode::"));
    }

    protected abstract Catalog catalogWithout(String barcodeToAvoid);
}
