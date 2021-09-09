package ca.jbrains.pos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class SellOneItemTest {
    @Test
    void productFound() {
        Display display = new Display();
        Sale sale = new Sale(display, new HashMap<>() {{
            put("12345", "EUR 7.95");
        }});

        sale.onBarcodeScanned("12345");

        Assertions.assertEquals("EUR 7.95", display.getText());
    }

    @Test
    void anotherProductFound() {
        Display display = new Display();
        Sale sale = new Sale(display, new HashMap<>() {{
            put("23456", "EUR 12.50");
        }});

        sale.onBarcodeScanned("23456");

        Assertions.assertEquals("EUR 12.50", display.getText());
    }

    @Test
    void productNotFound() {
        Display display = new Display();
        Sale sale = new Sale(display, new HashMap<>());

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
        private Display display;
        private Map<String, String> pricesByBarcode;

        public Sale(Display display, Map<String, String> pricesByBarcode) {
            this.display = display;
            this.pricesByBarcode = pricesByBarcode;
        }

        // CONTRACT barcode is never null
        public void onBarcodeScanned(String barcode) {
            if ("".equals(barcode)) {
                display.displayEmptyBarcodeMessage();
            } else {
                String formattedPrice = findPrice(barcode);
                if (formattedPrice == null)
                    display.displayProductNotFoundMessage(barcode);
                else
                    display.displayProductFoundMessage(formattedPrice);
            }
        }

        private String findPrice(String barcode) {
            return this.pricesByBarcode.get(barcode);
        }
    }

    public static class Display {
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        private void displayProductFoundMessage(String formattedPrice) {
            setText(formattedPrice);
        }

        private void displayEmptyBarcodeMessage() {
            setText("Scanning error: empty barcode");
        }

        private void displayProductNotFoundMessage(String barcode) {
            setText(String.format("Product not found: %s", barcode));
        }
    }
}
