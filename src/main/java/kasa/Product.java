package kasa;

public class Product {

	private int barcode;
	private double quantity;
	private double totalPrice;
	private String name;
	
	// cijena jednog proizvoda
	private double price;
	private boolean isLowPrice;

	// Konstruktor koji koristimo kada kasir provude proizvod kroz kasu
	public Product(int barcode, double quantity) {
		this.barcode = barcode;
		this.quantity = quantity;
	}
	
	public Product(int barcode, String name, double price, boolean isLowPrice) {
		this.barcode = barcode;
		this.name = name;
		this.price = price;
		this.isLowPrice = isLowPrice;
	}

	public int getBarcode() {
		return barcode;
	}

	public void setBarcode(int barcode) {
		this.barcode = barcode;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isLowPrice() {
		return isLowPrice;
	}

	public void setLowPrice(boolean isLowPrice) {
		this.isLowPrice = isLowPrice;
	}

	public static Product newProduct(String barcodeString, String name, 
			String priceString, boolean isLowPrice) {
		int barcode;
		double price;

		try {
			barcode = Integer.valueOf(barcodeString);
			price = Double.valueOf(priceString);

			if (barcode <= 0 || price <= 0) {
				return null;
			}

		} catch (NumberFormatException e) {
			return null;
		}

		Product proizvod = new Product(barcode, name, price, isLowPrice);

		return proizvod;
	}

}