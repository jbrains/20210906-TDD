package ca.jbrains.pos.test;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SellOneItemControllerTest {
    @Test
    void productFound() {
        Catalog catalog = Mockito.mock(Catalog.class);
        Display display = Mockito.mock(Display.class);

        String scannedBarcode = "::barcode that matches a product::";
        Price matchingPrice = Price.euroCents(795);

        Mockito.when(catalog.findPrice(scannedBarcode)).thenReturn(matchingPrice);

        new SellOneItemController(catalog, display).onBarcodeScanned(scannedBarcode);

        Mockito.verify(display).displayProductFoundMessage(matchingPrice);
    }

    @Test
    void productNotFound() {
        Catalog catalog = Mockito.mock(Catalog.class);
        Display display = Mockito.mock(Display.class);

        String scannedBarcode = "::missing barcode::";
        Mockito.when(catalog.findPrice(scannedBarcode)).thenReturn(null);

        new SellOneItemController(catalog, display).onBarcodeScanned(scannedBarcode);

        Mockito.verify(display).displayProductNotFoundMessage(scannedBarcode);
    }

    @Test
    void emptyBarcode() {
        Display display = Mockito.mock(Display.class);

        new SellOneItemController(null, display).onBarcodeScanned("");

        Mockito.verify(display).displayEmptyBarcodeMessage();
    }

}
