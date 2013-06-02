package kasa;

import java.awt.font.NumericShaper;

import javax.xml.crypto.Data;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

public class AdministratorLayout extends HorizontalLayout {

	private TextField txtBarkod;
	private TextField txtNaziv;
	private TextField txtCijena;
	private Button btnPotvrdiUnos;

	public AdministratorLayout() {

		txtBarkod = new TextField("Barkod");
		txtNaziv = new TextField("Naziv");
		txtCijena = new TextField("Cijena");
		btnPotvrdiUnos = new Button("Dodaj proizvod");

		addComponent(txtBarkod);
		addComponent(txtNaziv);
		addComponent(txtCijena);
		addComponent(btnPotvrdiUnos);

		ClickListener potvrdiUnoslistener = new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				System.out.println("Dodajem proizvod u bazu");

				Proizvod proizvod = uzmiProizvod();
				

				if (proizvod != null) {
					save(proizvod);
				} else {
					Notification.show("Podaci nisu validni!");
				}

			}
		};

		btnPotvrdiUnos.addClickListener(potvrdiUnoslistener);

	}


	protected void save(Proizvod proizvod) {
		Database.save(proizvod);
	}

	protected Proizvod uzmiProizvod() {

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
		
		Proizvod proizvod = new Proizvod(barkod, naziv, cijena);
		
		return proizvod;
	}

}
