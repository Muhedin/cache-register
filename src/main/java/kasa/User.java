package kasa;

public class User {

	private String username;
	private boolean isAdmin;

	// ------------------------------------------------------------
	private String txtCashierName;
	private String txtPassword;

	// Konstruktor koji koristimo za dodavanje novog kasira
	public User(String txtCashierName, String txtPassword) {

		this.txtCashierName = txtCashierName;
		this.txtPassword = txtPassword;

	}
	
	
	//konstruktor koji koristimo za ucavanje korisnika iz baze
	private String ime;
	boolean jeLiAdmin;
	
	public User(String i, boolean a) {
		this.ime = i;
		this.jeLiAdmin = a;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getTxtCashierName() {
		return txtCashierName;
	}

	public void setTxtCashierName(String txtCashierName) {
		this.txtCashierName = txtCashierName;
	}

	// ------------------------------------------------------------

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