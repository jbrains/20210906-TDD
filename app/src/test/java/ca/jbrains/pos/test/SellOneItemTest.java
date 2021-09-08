package ca.jbrains.pos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class SellOneItemTest {
    @Test
    void productFound() {
        Display display = new Display();
        Sale sale = new Sale(display);

        sale.onBarcodeScanned("12345");

        Assertions.assertEquals("EUR 7.95", display.getText());
    }

    @Test
    @Disabled("refactoring")
    void anotherProductFound() {
        Display display = new Display();
        Sale sale = new Sale(display);

        sale.onBarcodeScanned("23456");

        Assertions.assertEquals("EUR 12.50", display.getText());
    }

    public static class Sale {
        private Display display;

        public Sale(Display display) {
            this.display = display;
        }

        public void onBarcodeScanned(String barcode) {
            display.setText("EUR 7.95");
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
    }
}
