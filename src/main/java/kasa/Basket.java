package kasa;

import java.util.ArrayList;
import java.util.List;

public class Basket {
	//Kreiramo listu proizvoda
	private List<Product> products = new ArrayList<Product>();

	//dodaje proizvod u korpu
	public void addProduct(Product p) {
		products.add(p);
	}

	//uzima proizvod iz korpe
	public List<Product> getProducts() {
		return products;
	}
}