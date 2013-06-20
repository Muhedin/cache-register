package kasa;

import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class LoginLayout extends VerticalLayout {

	private static final long serialVersionUID = 6473455514509708020L;

	private TextField txtUsername;
	private PasswordField txtPassword;
	private Button login;

	private LoggedInListener listener;
	
	private UserService userService;
	private User user;

	public LoginLayout(UserService userService) {
		this.userService = userService;

		init();
	}

	private void init() {
		txtUsername = new TextField("Korisničko ime");
		txtPassword = new PasswordField("Lozinka");
		
		login = new Button("Uloguj se");
		login.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				login();
				listener.fire();
			}
		});
		
		addComponent(txtUsername);
		addComponent(txtPassword);
		addComponent(login);
	}
	
	public void login() {
		this.user = userService.login(txtUsername.getValue(), txtPassword.getValue());
	}
	
	public User getUser() {
		return user;
	}

	public void setLoggedInListener(LoggedInListener listener) {
		this.listener = listener;
		
	}

}
