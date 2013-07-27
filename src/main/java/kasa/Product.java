package kasa;

public class Product {

	private int barcode;
	private double quantity;
	private double totalPrice;
	private String name;
	
	// cijena jednog proizvoda
	private double price;

	// Konstruktor koji koristimo kada kasir provude proizvod kroz kasu
	public Product(int barcode, double quantity) {
		this.barcode = barcode;
		this.quantity = quantity;
	}
	
	//Konstuktor koji koristimo za unos podataka u bazu
	public Product(int barcode, String name, double price) {
		this.barcode = barcode;
		this.name = name;
		this.price = price;
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

}