package kasa;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

public class AdministratorLayout extends HorizontalLayout {

	private static final long serialVersionUID = -8222256930467387941L;

	private TextField txtBarkod;
	private TextField txtNaziv;
	private TextField txtCijena;
	private Button btnPotvrdiUnos;

	private Table proizvodiUTabli;

	public AdministratorLayout() {

		createComponents();

		arrangeComponents();

	}

	private void createComponents() {
		proizvodiUTabli = createProductsTable();
		displayProductsFromDatabase();

		txtBarkod = new TextField("Barkod");
		txtNaziv = new TextField("Naziv");
		txtCijena = new TextField("Cijena");
		btnPotvrdiUnos = new Button("Dodaj proizvod");

		ClickListener potvrdiUnoslistener = new ClickListener() {

			private static final long serialVersionUID = 7577030264993664896L;

			@Override
			public void buttonClick(ClickEvent event) {
				System.out.println("Dodajem proizvod u bazu");

				Product proizvod = uzmiProizvod();

				if (proizvod != null) {
					save(proizvod);
					displayProductsFromDatabase();
				} else {
					Notification.show("Podaci nisu validni!");
				}

			}
		};

		btnPotvrdiUnos.addClickListener(potvrdiUnoslistener);
	}

	private void displayProductsFromDatabase() {
		// TODO 1. Ucitati sve proizvode iz baze u jednu java.lang.List
		List<Product> products = Database.findAllProducts();

		// 1. Iteriraj kroz listu
		// 2. za svaki proizvod u iteracijama, dodati u tabelu. Koristiti metodu dodajUTabelu u klasi CashierLayout
	}

	private void arrangeComponents() {
		addComponent(proizvodiUTabli);
		addComponent(txtBarkod);
		addComponent(txtNaziv);
		addComponent(txtCijena);
		addComponent(btnPotvrdiUnos);

		setSpacing(true);
		setMargin(true);
	}

	private Table createProductsTable() {
		Table table = new Table("");
		table.setWidth("33%");

		table.addContainerProperty("Barkod", Integer.class, null);
		table.addContainerProperty("Naziv", String.class, null);
		table.addContainerProperty("Cijena", Double.class, null);

		return table;
	}

	protected void save(Product proizvod) {
		Database.save(proizvod);
	}

	protected Product uzmiProizvod() {

		int barkod;
		String naziv;
		double cijena;

		naziv = txtNaziv.getValue();

		try {
			barkod = Integer.valueOf(txtBarkod.getValue());
			cijena = Double.valueOf(txtCijena.getValue());

			if (barkod <= 0 || cijena <= 0) {
				return null;
			}

		} catch (NumberFormatException e) {
			return null;
		}

		Product proizvod = new Product(barkod, naziv, cijena);

		return proizvod;
	}

}
