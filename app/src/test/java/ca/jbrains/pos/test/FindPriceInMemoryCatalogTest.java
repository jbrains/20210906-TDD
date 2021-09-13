package ca.jbrains.pos.test;

import java.util.HashMap;

public class FindPriceInMemoryCatalogTest extends FindPriceInCatalogContract {
    @Override
    protected Catalog catalogWith(final String barcode, final Price matchingPrice) {
        return new InMemoryCatalog(new HashMap<>() {{
            put(String.format("not %s", barcode), Price.euroCents(-66798));
            put(barcode, matchingPrice);
            put(String.format("definitely not %s", barcode), Price.euroCents(-66799));
        }});
    }

    @Override
    protected Catalog catalogWithout(String barcodeToAvoid) {
        return new InMemoryCatalog(new HashMap<>() {{
            put(String.format("not %s", barcodeToAvoid), Price.euroCents(-66798));
            put(String.format("definitely not %s", barcodeToAvoid), Price.euroCents(-66799));
            put(String.format("not %s, I thought I told you!", barcodeToAvoid), Price.euroCents(-66800));
        }});
    }

}
