package codswork.easycard;

import java.util.ArrayList;

import android.media.Image;

public class Card {
	
	public static ArrayList<Card> Cards = new ArrayList<Card>();
	
	private Image bandeira;
	private String name;
	private boolean user_made;
	
	public Card(String name){
		this.name = name;
	}

	public Image getBandeira() {
		return bandeira;
	}

	public void setBandeira(Image bandeira) {
		this.bandeira = bandeira;
	}

	public String getNome() {
		return name;
	}

	public void setNome(String nome) {
		this.name = nome;
	}

	public boolean isUser_made() {
		return user_made;
	}

	public void setUser_made(boolean user_made) {
		this.user_made = user_made;
	}

}
