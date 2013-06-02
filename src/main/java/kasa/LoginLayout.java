package kasa;

import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class LoginLayout extends VerticalLayout {

	private static final long serialVersionUID = 6473455514509708020L;

	private TextField username;
	private PasswordField password;
	private Button login;

	private LoggedInListener listener;
	
	private UserService userService;
	private User user;

	public LoginLayout(UserService userService) {
		this.userService = userService;

		init();
	}

	private void init() {
		username = new TextField("Korisničko ime");
		password = new PasswordField("Lozinka");
		
		login = new Button("Uloguj se");
		login.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				login();
				listener.fire();
			}
		});
		
		addComponent(username);
		addComponent(password);
		addComponent(login);
	}
	
	public void login() {
		this.user = userService.login(username.getValue(), password.getValue());
	}
	
	public User getUser() {
		return user;
	}

	public void setLoggedInListener(LoggedInListener listener) {
		this.listener = listener;
		
	}

}
