package kasa;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

/**
 * The Application's "main" class
 * 
 * @param <kasa>
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
			Notification.show("Korisničko ime i/ili lozinka nije ispravno.");
		} else if (user.isAdmin()) {
			Notification.show("Ulogovali ste se kao admin.");
			AdministratorLayout administratorLayout = new AdministratorLayout();
			setContent(administratorLayout);
			
		} else {
			Notification.show("Ulogovali ste se kao kasir.");
			CashierLayout cashierLayout = new CashierLayout();
			setContent(cashierLayout);
		}
	}

}
