package ca.jbrains.pos.test;

public class SellOneItemController implements CommandInterpreter {
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

    @Override
    public void handleCommand(String commandText) {
        onBarcodeScanned(commandText);
    }
}
