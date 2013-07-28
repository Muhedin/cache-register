package kasa;

import java.util.List;

import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
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

	private static final String BARCODE = "Barkod";

	private static final String NAME = "Naziv";
	
	private static final String PRICE = "Cijena";

	private TabSheet tabs;

	/*
	 * Adding a new product
	 */
	private TextField txtBarcode;
	private TextField txtName;
	private TextField txtPrice;
	private Button btnSaveProduct;
	
	/*
	 * Editing an existing product
	 */
	private TextField txtEditBarcode;
	private TextField txtEditName;
	private TextField txtEditPrice;
	private Button btnUpdateProduct;
	private Button btnDeleteProduct;
	
	private Table productsTable;
	
	// TODO Possible duplication?
	private int currentProductId;
	private int currentlySelectedBarcode;

	/**
	 * Deklaracija komponenti za novog kasira
	 */
	private Button btnAddCashier;
	private TextField txtCashierName;
	private PasswordField txtCashierPassword;
	private int currentUserId;
	private Table usersTable;

	
	public AdministratorLayout() {
		createComponents();

		arrangeComponents();
	}

	/** 
	 * Kreiramo komponente koje ćemo prikazati na layoutu, (glavni layout)
	 */
	private void createComponents() {
		tabs = new TabSheet();
		
		VerticalLayout productsTab = createProductsLayout();
		tabs.addTab(productsTab, "Proizvodi");

		VerticalLayout cashierLayout = createUsersLayout();
		tabs.addTab(cashierLayout, "Kasiri");

		tabs.setStyleName(Reindeer.TABSHEET_MINIMAL);
		
		tabs.setSizeFull();
		tabs.setWidth("100%");
		tabs.setHeight("100%");
	}

	
	/**
	 *  Kreiramo VerticalLayout na kojem prikazujemo komponente za unos novog korisnika i tabelu sa svim korinicima
	 * @return
	 */
	private VerticalLayout createUsersLayout() {
		VerticalLayout usersLayout = new VerticalLayout();
		
		usersTable = createUsersTable();
		displayUsersFromDatabase();

		VerticalLayout addNewUserComponent = createAddNewUserComponent();

		usersLayout.addComponent(usersTable);
		usersLayout.addComponent(addNewUserComponent);

		usersLayout.setSpacing(true);
		usersLayout.setMargin(true);

		return usersLayout;
	}

	private VerticalLayout createAddNewUserComponent() {

		VerticalLayout cashierLayout = new VerticalLayout();
		
		/**
		 *  Komponente za kasira
		 */
		txtCashierName = new TextField("Korisničko ime");
		txtCashierPassword = new PasswordField("Lozinka");
		btnAddCashier = new Button("Sačuvaj");
		
		
		ClickListener dodajKasiraListener = new ClickListener() {

			private static final long serialVersionUID = 7577030264993664896L;

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

	/**
	 *  Metoda kojom prikazujemo sve korisnike iz baze u tabelu
	 */
	private void displayUsersFromDatabase() {
		usersTable.removeAllItems();

		List<User> users = Database.findAllUsers();
		currentUserId = 0;

		/**
		 *  iteriramo kroz listu
		 */
		for (User user : users) {
			addToTable(user);
		}

		usersTable.refreshRowCache();
	}

	/**
	 * Dodajemo korisnike u tabelu
	 * @param user
	 */
	private void addToTable(User user) {
		Object[] newItem = null;
		newItem = new Object[] { user.getIme(), user.jeLiAdmin};
		usersTable.addItem(newItem, currentUserId++);

	}

	/**
	 * Kreiramo layout za proizvode
	 * @return
	 */
	private VerticalLayout createProductsLayout() {
		
		VerticalLayout productsLayout = new VerticalLayout();

		productsTable = createProductsTable();
		displayProductsFromDatabase();

		productsLayout.addComponent(productsTable);
		
		productsLayout.addComponent(createUpdateDeleteProductComponent());
		
		productsLayout.addComponent(createAddNewProductComponent());

		productsLayout.setSpacing(true);
		productsLayout.setMargin(true);
		
		return productsLayout;
	}

	private Component createUpdateDeleteProductComponent() {
		VerticalLayout updateDelete = new VerticalLayout();
		
		updateDelete.addComponent(createUpdateDeleteComponent());
		
		return updateDelete;
	}

	private Component createUpdateDeleteComponent() {
		HorizontalLayout update = new HorizontalLayout();
		
		txtEditBarcode = new TextField("Barkod");
		txtEditName = new TextField("Naziv");
		txtEditPrice = new TextField("Cijena");
		btnUpdateProduct = new Button("Ažuriraj proizvod");
		btnDeleteProduct = new Button("Obriši proizvod");
		
		btnUpdateProduct.addClickListener(new ClickListener() {
			
			private static final long serialVersionUID = -1667346468248459578L;

			@Override
			public void buttonClick(ClickEvent event) {
				Database.updateProduct(currentlySelectedBarcode, getUpdatedProduct());
				displayProductsFromDatabase();
				
			}
		});
		
		btnDeleteProduct.addClickListener(new ClickListener() {
			
			private static final long serialVersionUID = -993282455122723438L;

			@Override
			public void buttonClick(ClickEvent event) {
				Database.deleteProduct(currentlySelectedBarcode);
				displayProductsFromDatabase();
			}
		});
		
		update.addComponent(txtEditBarcode);
		update.addComponent(txtEditName);
		update.addComponent(txtEditPrice);
		update.addComponent(btnUpdateProduct);
		update.addComponent(btnDeleteProduct);
		
		update.setSpacing(true);
		update.setMargin(true);
		
		return update;
	}

	protected Product getUpdatedProduct() {
		return Product.newProduct(
				txtEditBarcode.getValue(),
				txtEditName.getValue(),
				txtEditPrice.getValue()
				);
	}

	/**
	 *  Horizontalni layout na kojem prikazujemo komponente za unos novog proizvoda
	 * @return
	 */
	private HorizontalLayout createAddNewProductComponent() {

		HorizontalLayout addNewProductComponent = new HorizontalLayout();

		txtBarcode = new TextField(BARCODE);
		txtName = new TextField(NAME);
		txtPrice = new TextField(PRICE);
		btnSaveProduct = new Button("Dodaj proizvod");

		ClickListener potvrdiUnoslistener = new ClickListener() {

			private static final long serialVersionUID = 7577030264993664896L;

			@Override
			public void buttonClick(ClickEvent event) {
				System.out.println("Dodajem proizvod u bazu");

				Product proizvod = getProduct();

				if (proizvod != null) {
					saveProduct(proizvod);
					txtBarcode.setValue("");
					txtName.setValue("");
					txtPrice.setValue("");
					displayProductsFromDatabase();
				} else {
					Notification.show("Podaci nisu validni!");
				}

			}
		};

		btnSaveProduct.addClickListener(potvrdiUnoslistener);

		addNewProductComponent.addComponent(txtBarcode);
		addNewProductComponent.addComponent(txtName);
		addNewProductComponent.addComponent(txtPrice);
		addNewProductComponent.addComponent(btnSaveProduct);

		addNewProductComponent.setSpacing(true);
		addNewProductComponent.setMargin(true);

		return addNewProductComponent;

	}

	/**
	 *  Metoda kojom prikazujemo sve proizvode iz baze
	 */
	private void displayProductsFromDatabase() {
		productsTable.removeAllItems();

		List<Product> products = Database.findAllProducts();
		currentProductId = 0;

		for (Product proizvod : products) {
			addToTable(proizvod);
		}

		productsTable.refreshRowCache();
	}

	/**
	 * Metoda kojom dodajemo proizvod u tablu
	 * @param proizvod
	 */
	private void addToTable(Product proizvod) {
		Object[] newItem = null;
		newItem = new Object[] { proizvod.getBarcode(), proizvod.getName(), proizvod.getPrice() };
		productsTable.addItem(newItem, currentProductId++);

	}

	/**
	 *  Metoda kojom dodajemo komponente na layoutus
	 */
	private void arrangeComponents() {
		addComponent(tabs);

		setSpacing(true);
		setMargin(true);
	}

	/**
	 *  Kreiramo tabelu za prikaz korisnika iz baze
	 * @return
	 */
	private Table createUsersTable() {
		Table table = new Table("");
		table.setWidth("45%");

		table.addContainerProperty("KORISNIK", String.class, null);
		table.addContainerProperty("JE LI ADMIN", Boolean.class, null);
		
		table.setSelectable(true);
		
		return table;
	}

	/**
	 *  Kreiramo tabelu za prikaz proizvoda iz baze
	 * @return
	 */
	private Table createProductsTable() {
		Table table = new Table("");
		table.setWidth("43%");

		table.addContainerProperty(BARCODE, Integer.class, null);
		table.addContainerProperty(NAME, String.class, null);
		table.addContainerProperty(PRICE, Double.class, null);

		table.setSelectable(true);
		table.addItemClickListener(new ItemClickListener() {
			
			private static final long serialVersionUID = 1404317452366005133L;

			@Override
			public void itemClick(ItemClickEvent event) {
				currentlySelectedBarcode = (Integer) event.getItem().getItemProperty(BARCODE).getValue();
				fillEditFields(event.getItem());
			}
		});
		
		return table;
	}

	protected void fillEditFields(Item item) {
		txtEditBarcode.setValue(String.valueOf(item.getItemProperty(BARCODE).getValue()));
		txtEditName.setValue(String.valueOf(item.getItemProperty(NAME).getValue()));
		txtEditPrice.setValue(String.valueOf(item.getItemProperty(PRICE).getValue()));
	}

	protected void saveProduct(Product product) {
		Database.saveProduct(product);
	}

	protected void saveCashier(User cashier) {
		Database.saveUser(cashier);
	}

	/**
	 *  Uzmi kasira iz polja sa layouta
	 * @return
	 */
	protected User getCashier() {

		String cashierName = null;
		String password = null;
		try {
			cashierName = txtCashierName.getValue();
			password = txtCashierPassword.getValue();
			if (cashierName == "" && password == "") {
				return null;
			}

		} catch (NumberFormatException e) {
			return null;
		}

		User cashier = new User(cashierName, password);
		return cashier;

	}

	/**
	 * Medota kojom uzimao vrijednosti iz polja sa forme prilikom dodavanja novog proizvoda
	 * @return
	 */
	protected Product getProduct() {
		return Product.newProduct(txtBarcode.getValue(), txtName.getValue(), txtPrice.getValue());
	}

}