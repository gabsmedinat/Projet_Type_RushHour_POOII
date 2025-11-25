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

/**
 * Classe qui représente le temps écoulé entre le début du jeu et le moment actuel. Il implémente un Runnable afin de jouer le
 * rôle de chronomètre sans ralentir ni interrompre le déroulement du jeu.
 * @author Gabme
 */
public class Chronometre implements Runnable {
	boolean chronometreActif = false;
	String instant = "00:00";
	Label chronometre = new Label(instant);
	
	int secondes = 0;
	int minutes = 0;

	BorderStroke bdStroke;

	/**
	 * Exécution du programme en continu. Arrêt sécuritaire du thread à l'aide d'un booléen de contrôle et une bouble while.
	 */
	@Override
	public void run() {
		while(chronometreActif) {
			secondes ++;
			if(secondes == 60) {
				minutes ++;
				secondes = 0;
			}
			instant = String.format("%02d:%02d",minutes,secondes); 
			Platform.runLater(() -> chronometre.setText(instant));
			//System.out.println(instant);
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Mise en forme du chronomètre. Ensuite, démarrage d'un thread portant l'objet Chronomètre comme argument.
	 */
	public void start() {
		bdStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,new CornerRadii(5), new BorderWidths(2.0) );
		chronometre.setBorder(new Border(bdStroke));
		chronometre.setPadding(new Insets(5,15,5,15));
	
		chronometreActif = true;
		Thread t = new Thread(this);
		t.start();
		
	}
	
	/**
	 * Méthode destinée à changer l'état du booléen de contrôle pour le thread du chronomètre. 
	 */
	public void arreterChronometre() {
		chronometreActif = false;
		instant = "00:00";
	}
	
	/**
	 * Getter d'étiquette d'affichage du chronomètre.
	 * @return
	 */
	public Label getLabel() {
	      return chronometre;
	  }
}
