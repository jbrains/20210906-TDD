package ca.jbrains.pos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class SellOneItemTest {
    @Test
    void productFound() {
        Display display = new Display();
        Sale sale = new Sale(display, new Catalog(new HashMap<>() {{
            put("12345", "EUR 7.95");
        }}));

        sale.onBarcodeScanned("12345");

        Assertions.assertEquals("EUR 7.95", display.getText());
    }

    @Test
    void anotherProductFound() {
        Display display = new Display();
        Sale sale = new Sale(display, new Catalog(new HashMap<>() {{
            put("23456", "EUR 12.50");
        }}));

        sale.onBarcodeScanned("23456");

        Assertions.assertEquals("EUR 12.50", display.getText());
    }

    @Test
    void productNotFound() {
        Display display = new Display();
        Sale sale = new Sale(display, new Catalog(new HashMap<>()));

        sale.onBarcodeScanned("99999");

        Assertions.assertEquals("Product not found: 99999", display.getText());
    }

    @Test
    void emptyBarcode() {
        Display display = new Display();
        Sale sale = new Sale(display, null);

        sale.onBarcodeScanned("");

        Assertions.assertEquals("Scanning error: empty barcode", display.getText());
    }

    public static class Sale {
        private final Catalog catalog;
        private final Display display;

        public Sale(Display display, Catalog catalog) {
            this.display = display;
            this.catalog = catalog;
        }

        // CONTRACT barcode is never null
        public void onBarcodeScanned(String barcode) {
            if ("".equals(barcode)) {
                display.displayEmptyBarcodeMessage();
            } else {
                String formattedPrice = catalog.findPrice(barcode);
                if (formattedPrice == null)
                    display.displayProductNotFoundMessage(barcode);
                else
                    display.displayProductFoundMessage(formattedPrice);
            }
        }
    }

    public static class Display {
        private String text;

        public String getText() {
            return text;
        }

        public void displayProductFoundMessage(String formattedPrice) {
            this.text = formattedPrice;
        }

        public void displayEmptyBarcodeMessage() {
            this.text = "Scanning error: empty barcode";
        }

        public void displayProductNotFoundMessage(String barcode) {
            this.text = String.format("Product not found: %s", barcode);
        }
    }

    public static class Catalog {
        private final Map<String, String> pricesByBarcode;

        public Catalog(Map<String, String> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        private String findPrice(String barcode) {
            return this.pricesByBarcode.get(barcode);
        }
    }
}
