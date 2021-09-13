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

    public static class SellOneItemController {
        private Catalog catalog;
        private Display display;

        public SellOneItemController(Catalog catalog, Display display) {
            this.catalog = catalog;
            this.display = display;
        }

        // CONTRACT barcode is a single line of text with only significant whitespace
        public void onBarcodeScanned(String barcode) {
            if ("".equals(barcode))
                display.displayEmptyBarcodeMessage();
            else {
                Price price = catalog.findPrice(barcode);
                if (price == null)
                    display.displayProductNotFoundMessage(barcode);
                else
                    display.displayProductFoundMessage(price);
            }
        }
    }

    public interface Display {
        void displayProductFoundMessage(Price price);

        void displayProductNotFoundMessage(String missingBarcode);

        void displayEmptyBarcodeMessage();
    }

}
