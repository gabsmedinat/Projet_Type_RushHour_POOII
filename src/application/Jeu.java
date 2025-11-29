package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.application.Platform;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Classe qui représente le Jeu. Elle place les objets Voiture au bon endroit de la grille selon le niveau 
 * de difficulté choisi. 
 * @author Gabme
 */

public class Jeu {
	private Integer nbrCoups;
	private String JEUX = "Assets/donnees/";
	private enum difficulté {facile, moyen, difficile};
	Voiture voitureSelectionnee = null;
	ArrayList<Voiture> lstVoitures = new ArrayList<Voiture>();
	private boolean setSystemeCollisions = false;
	private Point2D pointBut = new  Point2D(450,250);
	
	
	/** 
	 * Constructeur de classe Jeu. Une fois que la difficulté de Jeu est choisie, le constructeur s'occupe de chercher les fichiers 
	 * correspondants, il initialise le nombre de placements faits à zero, et il créé des instances d'objet Voiture qui sont ensuite 
	 * ajoutées à une collection de voitures.
	 * @param Difficulté La difficulté du jeu selon trois types  */
	public Jeu(String difficulte) {
		nbrCoups = 0;
		
		/* Lecture du fichier */
		try {
			String ligne;
			FileReader fReader = new FileReader(this.JEUX+difficulte+".txt");
			BufferedReader br = new BufferedReader(fReader);
			
			try {
				/* Chaque ligne représente un objet Voiture. Séparation des paramètres pour ensuite creer instance de Voiture. */
				while((ligne = br.readLine()) != null) {
					String elements[] = ligne.split(",");
					String couleurVTR = elements[0];
					int tailleVTR = Integer.parseInt(elements[1]);
					int colonneVTR = Integer.parseInt(elements[2]);
					int linVTR = Integer.parseInt(elements[3]);
					String orientationVTR = elements[4];
					lstVoitures.add(new Voiture(couleurVTR,tailleVTR,colonneVTR,linVTR,orientationVTR));
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Retour du nombre de deplacements faits dans le jeu.
	 * @return nbrCoups
	 */
	public String getCoups() {
		return this.nbrCoups.toString();
	}
	
	/**
	 * 
	 * @param coup le numéro de deplacements
	 */
	public void setCoups(Integer coup) {
		nbrCoups = coup;
	}
	
	/**
	 * Méthode qui arme la partie visuelle de la voiture pour la placer dans la grille.
	 * @param panneau Panneau (Pane) qui sera affecté
	 */
	public void afficheVoitures(Pane panneau) {
		Iterator<Voiture> it = lstVoitures.iterator();
		while(it.hasNext()) {
			Voiture vtr = it.next();     					// Itération en cours
			Image img = vtr.getImgVoiture();               	
			ImageView imgV = new ImageView(img);
			
			// Faire que le coin supérieur gauche de l'image (origine locale de l'image) soit placé dans les cordonnées 
			// obtenues du fichier 
			imgV.setLayoutX(vtr.getX());					
			imgV.setLayoutY(vtr.getY());					
			
			
			/* Préparation des conditions nécessaires pour le système de collisions. Chaque Voiture recoit un ID
			 * en fonction de sa place dans l'Array du Jeu. */
			
			Integer ID = lstVoitures.indexOf(vtr);
			vtr.setID(ID.toString());
			
			/* Utilisation des dimensions de l'ImageView de l'objet Voiture. */
//			vtr.setBounds(imgV.getBoundsInParent());   
			panneau.getChildren().add(imgV);
			vtr.setBounds(imgV);
			System.out.println("Voiture : "+vtr.getID()+" - Bounds: "+ vtr.getBounds());
						
			
			/* RequestFocus pour l'objet Voiture lorsque cliqué + animation courte */
			imgV.setOnMouseClicked(event ->{
				voitureSelectionnee = vtr;
				imgV.requestFocus();
				System.out.println("Vous avez selectionné "+vtr.getID()+" - Bounds: "+ vtr.getBounds());   // TODO Indicateur pour debug
				
				/*  Animation   */
				Thread t = new Thread(()->{
					Platform.runLater(()->{
						imgV.setScaleX(1.1);
						imgV.setScaleY(1.1);
						imgV.setScaleZ(1.1);	
					});
					
					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					Platform.runLater(()->{
						imgV.setScaleX(1.0);
						imgV.setScaleY(1.0);
						imgV.setScaleZ(1.0);
					});
										
				});
				t.start();
				
				
				/* Saisie de FocusTraversable à l'objet Voiture choisi et seulement à lui. Cela est fait 
				 * avec le but d'empecher le reste des instances Voiture de se déplacer lorsque je clique à une nouvelle Voiture
				 * Le résultat de ne pas faire cela serait donner la possibilité à plusieurs Voitures de se déplacer en même temps
				 * avec la commande du clavier (condition de course ou "racing confition").*/
				imgV.setFocusTraversable(true);   
				imgV.requestFocus();
				
								
				/*
				 * Lorsque touche appuyée, si la touche est une flèche directionnelle, la voiture se déplacera.
				 * Aussi, si la voiture rouge atteint la sortie, affichage du gagnant. 
				 */
				imgV.setOnKeyPressed(e->{
					String direction = e.getCode().toString();
					vtr.seDeplacer(direction, imgV, lstVoitures);
					if(vtr.getBounds().intersects(pointBut.getX(),pointBut.getY(),1,1)&&vtr.getCouleur().equals("rouge")&&direction.equals("RIGHT")) {
						System.out.println("Voilà");
						Alert joueurGagne = new Alert(AlertType.INFORMATION);
						joueurGagne.setHeaderText("Vous avez gagné ");
						joueurGagne.setTitle("Félicitations !");
						joueurGagne.setGraphic(new ImageView(new Image(getClass().getResource("/images/award.gif").toString())));
						joueurGagne.showAndWait();
					}					
				}); 
				
				/* Une fois l'action accomplie, FocusTraversable devient False pour limiter la mouvement à un seul Objet Voiture à la fois */
				imgV.setFocusTraversable(false);
				
			});
			
		}
	}
	
	
}
