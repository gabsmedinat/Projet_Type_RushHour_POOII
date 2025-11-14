package application;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class Chronometre implements Runnable {
	String instant = "00:00";
	Label chronometre = new Label(instant);
	BorderStroke bdStroke;
	
	int secondes = 0;
	int minutes = 0;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			secondes ++;
			if(secondes == 60) {
				minutes ++;
				secondes = 0;
			}
			instant = String.format("%02d:%02d",minutes,secondes);
			Platform.runLater(() -> chronometre.setText(instant));
			System.out.println(instant);
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
	}
	
	public void start() {
		bdStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,new CornerRadii(5), new BorderWidths(2.0) );
		chronometre.setBorder(new Border(bdStroke));
		chronometre.setPadding(new Insets(5,15,5,15));
	

		Thread t = new Thread(this);
		t.start();
	}
	
	
	  public Label getLabel() {
	        return chronometre;
	    }
}
