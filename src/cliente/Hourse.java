package cliente;

import java.awt.*;

import javax.swing.ImageIcon;

public class Hourse {
	private String ruta="C:/Users/alejandro/eclipse-workspace/examen/img/hourse.gif";

	private Point pos;
	private Image image;
	
	public Hourse(Point pos) {
		super();
		this.pos = pos;
		image = new ImageIcon(ruta).getImage();
	}
	public void move(int d) {
		pos.x=10+d;
	}
	
	public Point getPos() {
		return pos;
	}
	public void setPos(Point pos) {
		this.pos = pos;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	
	
}
