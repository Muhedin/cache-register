package kasa;

import java.util.List;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class AdministratorLayout extends HorizontalLayout {

	private static final long serialVersionUID = -8222256930467387941L;

	private TabSheet tabs;

	private TextField txtBarkod;
	private TextField txtNaziv;
	private TextField txtCijena;
	private Button btnPotvrdiUnos;
	private Table productsTable;
	private int currentProductId;
	
	private Button btnDelete;

	// Deklaracija komponenti za novog kasira
	private Button btnAddCashier;
	private TextField txtCashierName;
	private PasswordField txtCashierPassword;

	public AdministratorLayout() {
		createComponents();

		arrangeComponents();
	}

	// Kreiramo komponente koje ćemo prikazati na layoutu
	private void createComponents() {
		tabs = new TabSheet();

		VerticalLayout productsTab = createProductsLayout();
		tabs.addTab(productsTab, "Proizvodi");

		VerticalLayout cashierLayout = createCashierLayout();
		tabs.addTab(cashierLayout, "Kasiri");

		tabs.setStyleName(Reindeer.TABSHEET_MINIMAL);
		
		tabs.setSizeFull();
		tabs.setWidth("100%");
		tabs.setHeight("100%");
	}

	private VerticalLayout createCashierLayout() {
		VerticalLayout cashierLayout = new VerticalLayout();

		// Komponente za kasira
		txtCashierName = new TextField("Ime i prezime");
		txtCashierPassword = new PasswordField("Lozinka");
		btnAddCashier = new Button("Sačuvaj");

		ClickListener dodajKasiraListener = new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				User cashier = getCashier();
				if (cashier != null) {
					saveCashier(cashier);
					txtCashierName.setValue("");
					txtCashierPassword.setValue("");
				} else {
					Notification.show("Podaci nisu validni!");
				}

			}
		};

		btnAddCashier.addClickListener(dodajKasiraListener);

		cashierLayout.addComponent(txtCashierName);
		cashierLayout.addComponent(txtCashierPassword);
		cashierLayout.addComponent(btnAddCashier);

		cashierLayout.setSpacing(true);
		cashierLayout.setMargin(true);
		
		return cashierLayout;
	}

	private VerticalLayout createProductsLayout() {
		VerticalLayout productsLayout = new VerticalLayout();

		productsTable = createProductsTable();
		displayProductsFromDatabase();
		

		HorizontalLayout addNewProductComponent = createAddNewProductComponent();

		productsLayout.addComponent(productsTable);
		productsLayout.addComponent(addNewProductComponent);

		productsLayout.setSpacing(true);
		productsLayout.setMargin(true);
		
		return productsLayout;
	}

	private HorizontalLayout createAddNewProductComponent() {
		HorizontalLayout addNewProductComponent = new HorizontalLayout();

		txtBarkod = new TextField("Barkod");
		txtNaziv = new TextField("Naziv");
		txtCijena = new TextField("Cijena");
		btnPotvrdiUnos = new Button("Dodaj proizvod");
		btnDelete = new Button("Izbriši");

		ClickListener potvrdiUnoslistener = new ClickListener() {

			private static final long serialVersionUID = 7577030264993664896L;

			@Override
			public void buttonClick(ClickEvent event) {
				System.out.println("Dodajem proizvod u bazu");

				Product proizvod = uzmiProizvod();

				if (proizvod != null) {
					saveProduct(proizvod);
					txtBarkod.setValue("");
					txtNaziv.setValue("");
					txtCijena.setValue("");
					displayProductsFromDatabase();
				} else {
					Notification.show("Podaci nisu validni!");
				}

			}
		};

		btnPotvrdiUnos.addClickListener(potvrdiUnoslistener);

		addNewProductComponent.addComponent(txtBarkod);
		addNewProductComponent.addComponent(txtNaziv);
		addNewProductComponent.addComponent(txtCijena);
		addNewProductComponent.addComponent(btnPotvrdiUnos);
		addNewProductComponent.addComponent(btnDelete);

		addNewProductComponent.setSpacing(true);
		addNewProductComponent.setMargin(true);
		
		return addNewProductComponent;
	}

	private void displayProductsFromDatabase() {
		productsTable.removeAllItems();
		
		List<Product> products = Database.findAllProducts();
		currentProductId = 0;
		
		for (Product proizvod : products) {
			addToTable(proizvod);
		}

		productsTable.refreshRowCache();
	}

	private void addToTable(Product proizvod) {
		Object[] newItem = null;
		newItem = new Object[] { proizvod.getBarcode(), proizvod.getName(), proizvod.getPrice() };
		productsTable.addItem(newItem, currentProductId++);
		
	}

	// Metoda u kojoj poredamo komponente na layoutu
	private void arrangeComponents() {
		addComponent(tabs);
		
		setSpacing(true);
		setMargin(true);
	}

	// Kreiramo tabelu za prikaz proizvoda iz baze
	private Table createProductsTable() {
		Table table = new Table("");
		table.setWidth("43%");

		table.addContainerProperty("Barkod", Integer.class, null);
		table.addContainerProperty("Naziv", String.class, null);
		table.addContainerProperty("Cijena", Double.class, null);

		return table;
	}

	protected void saveProduct(Product proizvod) {
		Database.saveProduct(proizvod);
	}

	protected void saveCashier(User kasir) {
		Database.saveUser(kasir);
	}

	// Uzmi kasira
	protected User getCashier() {

		String txtImeKasira = null;
		String txtLozinka = null;
		try {
			txtImeKasira = txtCashierName.getValue();
			txtLozinka = txtCashierPassword.getValue();
			if (txtImeKasira == "" && txtLozinka == "") {
				return null;
			}

		} catch (NumberFormatException e) {
			return null;
		}

		User kasir = new User(txtImeKasira, txtLozinka);
		return kasir;

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
