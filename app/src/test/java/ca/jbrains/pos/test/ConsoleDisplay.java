package ca.jbrains.pos.test;

class ConsoleDisplay implements Display {
    private final ConsoleGateway consoleGateway = new ConsoleGateway();

    @Override
    public void displayProductFoundMessage(Price price) {
        consoleGateway.printMessage(Integer.valueOf(price.centsValue()).toString());
    }

    private void printMessage(String message) {
        consoleGateway.printMessage(message);
    }

    @Override
    public void displayProductNotFoundMessage(String missingBarcode) {
        consoleGateway.printMessage("Product not found: " + missingBarcode);
    }

    @Override
    public void displayEmptyBarcodeMessage() {
        consoleGateway.printMessage("Scanning error: empty barcode");
    }
}
