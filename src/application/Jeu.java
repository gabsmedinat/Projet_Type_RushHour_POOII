package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Jeu {
	private String JEUX = "Assets/donnees/";
	private enum difficulté {facile, moyen, difficile};
	ArrayList<Voiture> lstVoitures = new ArrayList<Voiture>();
	
	public Jeu(String difficulte) {
		String informationsJeu = "";
		try {
			String ligne;
			FileReader fReader = new FileReader(this.JEUX+difficulte+".txt");
			BufferedReader br = new BufferedReader(fReader);
			
			try {
				while((ligne = br.readLine()) != null) {
					String elements[] = ligne.split(",");
					String couleur = elements[0];
					int taille = Integer.parseInt(elements[1]);
					int colonne = Integer.parseInt(elements[2]);
					int lin = Integer.parseInt(elements[3]);
					String orientation = elements[4];
					lstVoitures.add(new Voiture(couleur,taille,colonne,lin,orientation));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	// Méthodes
	public void afficheVoitures(Pane panneau) {
		Iterator<Voiture> it = lstVoitures.iterator();
		while(it.hasNext()) {
			Voiture vtr = it.next();
			Image img = vtr.getImgVoiture();
			ImageView imgV = new ImageView(img);
			imgV.setLayoutX(vtr.getX());
			imgV.setLayoutY(vtr.getY());
			panneau.getChildren().add(imgV);
			//System.out.println(vtr);
			
			imgV.setOnMouseClicked(event ->{
				
				Thread t = new Thread(()->{
					Platform.runLater(()->{
						imgV.setScaleX(1.1);
						imgV.setScaleY(1.1);
						imgV.setScaleZ(1.1);	
					});
					
					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Platform.runLater(()->{
						imgV.setScaleX(1.0);
						imgV.setScaleY(1.0);
						imgV.setScaleZ(1.0);
					});
										
				});
				t.start();
				imgV.setFocusTraversable(true);
				imgV.requestFocus();
				
				imgV.setOnKeyPressed(e->{
					String direction = e.getCode().toString();
					//System.out.println(e.getCode());
					vtr.seDeplacer(direction, imgV);
				});
				
			});
			
		}
	}
	

}
