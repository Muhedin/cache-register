package kasa;

import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

public class User {

	private String username;
	private boolean isAdmin;
	
	//------------------------------------------------------------
	private String txtCashierName;
	private String txtPassword;
	
	//Konstruktor 
	public User(String txtCashierName, String txtPassword){
		
		this.txtCashierName = txtCashierName;
		this.txtPassword = txtPassword;
		
	} 
	
	public String getTxtCashierName() {
		return txtCashierName;
	}

	public void setTxtCashierName(String txtCashierName) {
		this.txtCashierName = txtCashierName;
	}
	//------------------------------------------------------------

	public String getTxtPassword() {
		return txtPassword;
	}

	public void setTxtPassword(String txtPassword) {
		this.txtPassword = txtPassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

}
