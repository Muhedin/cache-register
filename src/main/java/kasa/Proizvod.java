package kasa;

public class Proizvod {

	private int barKod;
	private double kolicina;
	private double ukupnaCijena;
	private String naziv;
	private double cijena;

	// Konstruktor
	public Proizvod(int bk, double k) {
		barKod = bk;
		kolicina = k;
	}
	
	public Proizvod(int bk, String n, double c) {
		barKod = bk;
		naziv = n;
		setCijena(c);
		
	}

	public int getBarKod() {
		return barKod;
	}

	public void setBarKod(int barKod) {
		this.barKod = barKod;
	}

	public double getKolicina() {
		return kolicina;
	}

	public void setKolicina(double kolicina) {
		this.kolicina = kolicina;
	}

	public void postaviUkupnuCijenu(double ukupnaCijena) {
		this.ukupnaCijena = ukupnaCijena;
	}

	public double dajUkupnuCijenu() {
		return this.ukupnaCijena;
	}

	public void postaviNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String dajNaziv() {
		return naziv;
	}

	public double getCijena() {
		return cijena;
	}

	public void setCijena(double cijena) {
		this.cijena = cijena;
	}
}
