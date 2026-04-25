package agricore.projet.model.zone.position;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Position {
	
	@Column(nullable = true) // Est-ce que ca peut etre null ? (batiment non placé ? ou alors on le gère différement)
	private Integer posX;
	@Column(nullable = true)
	private Integer posY;
	@Column(nullable = true)
	private Integer tailleX;
	@Column(nullable = true)
	private Integer tailleY;
	
	public Position() {
	}
	
	
	
	public Position(Integer posX, Integer posY, Integer tailleX, Integer tailleY) {
		//Verifier pas d'overlap avec un batiment existant
		this.posX = posX;
		this.posY = posY;
		this.tailleX = tailleX;
		this.tailleY = tailleY;
	}


	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public int getTailleX() {
		return tailleX;
	}
	public void setTailleX(int tailleX) {
		this.tailleX = tailleX;
	}
	public int getTailleY() {
		return tailleY;
	}
	public void setTailleY(int tailleY) {
		this.tailleY = tailleY;
	}
	
	public static boolean isPosAvailable(int posX, int posY, int tailleX, int tailleY) {
		// TODO 
		// En static car n'a pas besoin qu'un objet soit instancié pour faire la verif
		// Doit checker (en BDD) si il est possible de placer un batiment à cette position
		return true;
	}

	@Override
	public String toString() {
		return "Position{" +
				"posX=" + posX +
				", posY=" + posY +
				", tailleX=" + tailleX +
				", tailleY=" + tailleY +
				'}';
	}
}
