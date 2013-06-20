package kasa;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class CashierLayout extends VerticalLayout {

	private static final long serialVersionUID = 3154074468832448512L;

	private final CacheRegister kasa = new CacheRegister();

	private double ukupnaCijena = 0;

	private Table spisakProizvodaTable;
	private Integer idProizvodaUTabeli = 1;

	private HorizontalLayout layoutSljedeciProizvod;

	private TextField txtBarkod;

	private TextField txtKolicina;

	private Label lblTotal;

	private Button btnSljedeciProizvod;
	private Button btnReset;
	
	public CashierLayout() {
		init();
	}
	
	protected void init() {
		final VerticalLayout layoutSadrzaj = new VerticalLayout();

		kreirajSpisakProizvoda();

		kreirajLayoutSljedeciProizvod();

		// layoutSadrzaj.addComponent(spisakProizvoda);
		layoutSadrzaj.addComponent(spisakProizvodaTable);
		layoutSadrzaj.addComponent(lblTotal);
		layoutSadrzaj.addComponent(layoutSljedeciProizvod);

		layoutSadrzaj.setSpacing(true);
		layoutSadrzaj.setMargin(true);

		addComponent(layoutSadrzaj);

	}

	private void kreirajLayoutSljedeciProizvod() {
		txtBarkod = new TextField("Barkod:");
		txtKolicina = new TextField("Količina:", "1");
		lblTotal = new Label("TOTAL");

		ClickListener sljedeci_Listener = new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				if (txtBarkod.getValue() == "" || txtKolicina.getValue() == "") {
					Notification.show("Niste nista unijeli!");
				} else if (!kasa.postojiBarkod(txtBarkod.getValue())) {
					Notification.show("Proizvod sa datim barkodom ne postoji!");
				} else {
					int barkod = Integer.valueOf(txtBarkod.getValue());
					double kolicina = Double.valueOf(txtKolicina.getValue());

					Product proizvod = new Product(barkod, kolicina);

					proizvod = kasa.izracunajCijenu(proizvod);

					ukupnaCijena += proizvod.getTotalPrice();

					dodajUTabelu(proizvod);

					prikaziCijenu(ukupnaCijena);
					
					txtBarkod.setValue("");
					txtKolicina.setValue("1");
				}

			}
		};

		ClickListener reset_Listener = new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				spisakProizvodaTable.removeAllItems();
				ukupnaCijena = 0;
				prikaziCijenu(ukupnaCijena);

			}
		};

		btnSljedeciProizvod = new Button("SLJEDEĆI");
		btnSljedeciProizvod.addClickListener(sljedeci_Listener);

		btnReset = new Button("Reset");
		btnReset.addClickListener(reset_Listener);
		btnReset.setIcon(new ThemeResource("../res/ok.png"));

		layoutSljedeciProizvod = new HorizontalLayout();

		layoutSljedeciProizvod.addComponent(txtBarkod);
		layoutSljedeciProizvod.addComponent(txtKolicina);

		layoutSljedeciProizvod.addComponent(btnSljedeciProizvod);
		layoutSljedeciProizvod.addComponent(btnReset);

	}

	protected void prikaziCijenu(double ukupnaCijena) {
		lblTotal.setValue("Cijena je: " + ukupnaCijena + " KM");
		
	}

	private void dodajUTabelu(Product proizvod) {

		spisakProizvodaTable.addItem(
				new Object[] { proizvod.getBarcode(), proizvod.getName(),
						proizvod.getQuantity(), proizvod.getTotalPrice() },
				idProizvodaUTabeli++);

		spisakProizvodaTable.refreshRowCache();
	}

	private void kreirajSpisakProizvoda() {

		spisakProizvodaTable = new Table("");
		spisakProizvodaTable.setWidth("33%");
		spisakProizvodaTable
				.addContainerProperty("Barkod", Integer.class, null);
		spisakProizvodaTable.addContainerProperty("Naziv", String.class, null);
		spisakProizvodaTable.addContainerProperty("Količina", Double.class,
				null);
		spisakProizvodaTable.addContainerProperty("Cijena", Double.class, null);

	}
}
