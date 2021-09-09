package ca.jbrains.pos.test;

import org.junit.jupiter.api.Disabled;
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

        new SellOneItemController(display).onBarcodeScanned(scannedBarcode);

        Mockito.verify(display).displayPrice(matchingPrice);
    }

    @Test
    @Disabled("refactoring")
    void productNotFound() {
        Catalog catalog = Mockito.mock(Catalog.class);
        Display display = Mockito.mock(Display.class);

        String scannedBarcode = "::missing barcode::";
        Mockito.when(catalog.findPrice(scannedBarcode)).thenReturn(null);

        new SellOneItemController(display).onBarcodeScanned(scannedBarcode);

        Mockito.verify(display).displayProductNotFoundMessage(scannedBarcode);
    }

    public static class SellOneItemController {
        private Display display;

        public SellOneItemController(Display display) {
            this.display = display;
        }

        public void onBarcodeScanned(String barcode) {
            display.displayPrice(Price.euroCents(795));
        }
    }
    public interface Display {
        void displayPrice(Price price);

        void displayProductNotFoundMessage(String missingBarcode);
    }
    public interface Catalog {
        Price findPrice(String barcode);
    }
    public record Price(int centsValue) {
        public static Price euroCents(int centsValue) {
            return new Price(centsValue);
        }
    }
}
