package kasa;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

/**
 * <center> <b>O aplikaciji</b></center>
 * \skip
 * 
 * 
 * Aplikacija „Kasa“ koristi se u prodavnicama, supermarketima i raznim firmama
 * da olakša poslodavcu promet robe koji izađe iz radnje i obračuna ukupnu
 * cijenu svih proizvoda koji su „provučeni“ kroz kasu i saopći kupcu
 * informacije koje ga zanimaju, ukupnu cijenu i cijenu svakog proizvoda
 * posebno.
 * 
 * \skip
 * 
 * <center> <b>Tehnologije za implementaciju</b></center>
 * Projekat „Kasa“ je zamišljen i implementiran kao web aplikacija.
 * Za implementaciju ovog projekta korišten je programski jezik Java, a zbog zahtjeva web aplikacije korišten Javin framework Vaadin. 

 * Vaadin Framework je Javino okruženje za razvoj i upravljanje web aplikacijama na veoma visokom nivou. 
 * On podržava dva različita programska modela: serversku stranu i klijentsku stranu. 
 * Serverska strana Vaadina pažljivo upravlja korisničkim interfejsom u pretraživačima i koristi AJAX tehnologiju za komunikaciju između servera i browsera (pretraživača).
 * 
 *  \skip
 *  
 *  <center> <b>Ukratko o kodu</b></center>
 *  Source code aplikacije je, shodno okruženju u kojem je implementirana, objektno orjentiran. Iskodirano je više od 1100 linija koda koji je podijeljen u 12 klasa.
 *
 */

/**
 * ! \mainpage
 * 
 */

@SuppressWarnings("serial")
public class MyVaadinUI<kasa> extends UI implements LoggedInListener {
	private LoginLayout login;

	@Override
	protected void init(VaadinRequest request) {
		// Konstrukcija interfejsa
		login = new LoginLayout(new UserService());
		login.setLoggedInListener((LoggedInListener) this);

		setContent(login);
		
	}

	@Override
	public void fire() {
		User user = login.getUser();

		if (user == null) {
			Notification.show("Korisničko ime i/ili lozinka nije ispravno!");
		} else if (user.isAdmin()) {
			Notification.show("Ulogovali ste se kao admin!");
			AdministratorLayout administratorLayout = new AdministratorLayout();
			setContent(administratorLayout);
			
		} else {
			Notification.show("Ulogovali ste se kao kasir!");
			CashierLayout cashierLayout = new CashierLayout();
			setContent(cashierLayout);
		}

	}

}