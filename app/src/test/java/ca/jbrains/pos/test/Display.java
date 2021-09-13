package ca.jbrains.pos.test;

public interface Display {
    void displayProductFoundMessage(Price price);

    void displayProductNotFoundMessage(String missingBarcode);

    void displayEmptyBarcodeMessage();
}
