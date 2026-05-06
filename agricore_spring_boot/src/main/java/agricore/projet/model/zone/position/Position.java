package agricore.projet.model.zone.position;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class Position {

	//TAILLE DE LA CARTE:
	public static final MapSize mapSize = new MapSize(50, 25);

	@Column(nullable = false)
	private Integer anchorX;
	@Column(nullable = false)
	private Integer anchorY;


	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = "varchar(15)")
	private Rotation rotation = Rotation.DEG_0;
	
	public Position() {
	}

	public Position(Integer anchorX, Integer anchorY, Rotation rotation) {
		this.anchorX = anchorX;
		this.anchorY = anchorY;
		this.rotation = rotation;
	}

	public Integer getAnchorX() {
		return anchorX;
	}

	public void setAnchorX(Integer anchorX) {
		this.anchorX = anchorX;
	}

	public Integer getAnchorY() {
		return anchorY;
	}

	public void setAnchorY(Integer anchorY) {
		this.anchorY = anchorY;
	}

	public Rotation getRotation() {
		return rotation;
	}

	public void setRotation(Rotation rotation) {
		this.rotation = rotation;
	}

	@Override
	public String toString() {
		return "Position{" +
				"anchorX=" + anchorX +
				", anchorY=" + anchorY +
				", rotation=" + rotation +
				'}';
	}
}
