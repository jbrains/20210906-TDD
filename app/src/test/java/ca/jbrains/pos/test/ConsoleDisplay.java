package ca.jbrains.pos.test;

class ConsoleDisplay implements Display {
    @Override
    public void displayProductFoundMessage(Price price) {
        printMessage(Integer.valueOf(price.centsValue()).toString());
    }

    private void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void displayProductNotFoundMessage(String missingBarcode) {
        printMessage("Product not found: " + missingBarcode);
    }

    @Override
    public void displayEmptyBarcodeMessage() {
        printMessage("Scanning error: empty barcode");
    }
}
