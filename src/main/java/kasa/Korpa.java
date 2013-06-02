package kasa;

import java.util.ArrayList;
import java.util.List;

public class Korpa {
	
	private List<Proizvod> proizvodi = new ArrayList<Proizvod>();

	//dodaje proizvod u korpu
	public void dodajProizvod(Proizvod p) {
		proizvodi.add(p);
	}

	//uzima proizvod iz korpe
	public List<Proizvod> uzmiProizvode() {
		return proizvodi;
	}

	
	/*
	private int barkodProizvoda1;
	private int barkodProizvoda2;
	private double kolicina1;
	private double kolicina2;

	public Korpa(int barkod1, int barkod2, double kol1, double kol2) {
		super();
		this.barkodProizvoda1 = barkod1;
		this.barkodProizvoda2 = barkod2;
		this.kolicina1 = kol1;
		this.kolicina2 = kol2;
	}

	public int getBarkodProizvoda1() {
		return barkodProizvoda1;
	}

	public void setBarkodProizvoda1(int barkodProizvoda1) {
		this.barkodProizvoda1 = barkodProizvoda1;
	}

	public int getBarkodProizvoda2() {
		return barkodProizvoda2;
	}

	public void setBarkodProizvoda2(int barkodProizvoda2) {
		this.barkodProizvoda2 = barkodProizvoda2;
	}

	public double getKolicina1() {
		return kolicina1;
	}

	public void setKolicina1(double kolicina1) {
		this.kolicina1 = kolicina1;
	}

	public double getKolicina2() {
		return kolicina2;
	}

	public void setKolicina2(double kolicina2) {
		this.kolicina2 = kolicina2;
	}


	*/
}
