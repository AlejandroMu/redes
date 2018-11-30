package cliente;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class Hipodromo extends JPanel{
	String ruta="C:/Users/alejandro/eclipse-workspace/examen/img/hourse.gif";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int hourse;
	double apuesta;
	Hourse hourses[];
	public Hipodromo() {
		setPreferredSize(new Dimension(1150, 400));
		setBorder(new TitledBorder("Hipodromo"));
		setBackground(Color.WHITE);
		setFocusable(true);
		setOpaque(false);
		
	}
	public void changeHourse(Hourse[] hourse,int h,double a) {
		hourses=hourse;
		this.hourse=h;
		apuesta=a;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.WHITE);
		if(hourses!=null) {
			
			
			Graphics2D gr=(Graphics2D)g;
			gr.setColor(Color.WHITE);
			gr.fillRect(0, 0, this.getWidth(),this.getHeight());
			gr.setColor(Color.BLACK);

			gr.drawLine(1010, 20, 1010, 400);
			gr.drawString("Apostó a C"+(hourse+1)+" un monto de $"+apuesta,450, 30);
			for (int i = 0; i < hourses.length; i++) {
				gr.setColor(Color.BLACK);
				Point tmp=hourses[i].getPos();
				Image image=hourses[i].getImage();
				int w=(int) (image.getWidth(this)*0.2);
				int h=(int) (image.getHeight(this)*0.2);
				if(i==hourse) {
					gr.setColor(Color.RED);
				}
//				gr.fillRect(20, tmp.y, tmp.x-w+20,h);
				gr.drawString("C #"+(i+1), tmp.x-w+10, (int) (tmp.y+(h*0.75)));
//				gr.drawLine(20, tmp.y, tmp.x, tmp.y);
				gr.drawImage(image, tmp.x, tmp.y,w,h,this);
//				gr.drawLine(20, tmp.y+h, tmp.x, tmp.y+h);

			}
		}
		setBackground(Color.WHITE);

	}

}
