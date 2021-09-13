package ca.jbrains.pos.test;

class ConsoleDisplay implements Display {
    @Override
    public void displayProductFoundMessage(Price price) {
        System.out.println(price.centsValue());
    }

    @Override
    public void displayProductNotFoundMessage(String missingBarcode) {
        System.out.println("Product not found: " + missingBarcode);
    }

    @Override
    public void displayEmptyBarcodeMessage() {
        System.out.println("Scanning error: empty barcode");
    }
}
