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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Classe qui représente le Jeu. Elle place les objets Voiture au bon endroit de la grille selon le niveau 
 * de difficulté choisi. 
 * @author Gabme
 */

public class Jeu {
	/**Nombre de déplacements effectués dans le jeu.*/
	private Integer nbrCoups;
	/**Constante contenant le chemin aux fichiers du jeu.*/
	private String JEUX = "Assets/donnees/";
	/**Liste des objets Voitures instanciés au début d'un jeu.*/
	private ArrayList<Voiture> lstVoitures = new ArrayList<Voiture>();
	/**Coordonnées du point de sortie. */
	private Point2D pointBut = new  Point2D(450,250);
	/**Étiquette, encore vide, qui recoit les valeurs de l'étiquette "chronometre" de la classe Chronometre.*/
	private Label lblCoups;
	
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
	 * Méthode qui augmente le nombre de coups effectués dans le jeu.
	 */
	public void ajouterCoup() {
		Platform.runLater(()->{
			nbrCoups++;
			lblCoups.setText(nbrCoups.toString());
		});
	}
	
	/**
	 * Méthode qui retourne une étiquette créé avec la valeur de la variable "nbrCoups" initialisée à zero.
	 * @return
	 */
	public Label GenererCoups() {
		lblCoups = new Label(this.nbrCoups.toString());
		return lblCoups;
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
			panneau.getChildren().add(imgV);
			vtr.setBounds(imgV);
//			System.out.println("Voiture : "+vtr.getID()+" - Bounds: "+ vtr.getBounds());
						
			/* Première option de déplacement: Glisser/Déplacer 
			 * Le déplacement dépend de deux évènements: En premier, la détection du evenement glisser avec le bouton appuyé.
			 * En deuxième, la détection du souris lorsqu'il sort de l'aire de la voiture
			 */
			vtr.getImgV().setOnDragDetected(evnt -> {
				vtr.setEstGlisse(true);
				imgV.setFocusTraversable(true);   
				imgV.requestFocus();
				double posXInitial = evnt.getX();
				double posYInitial = evnt.getX();
//				System.out.println(String.format("Début: (%f,%f)",posXInitial,posYInitial));
					
				vtr.getImgV().setOnMouseExited(e ->{
//					System.out.println("Souris sorti de la voiture. Faut donc calculer:");
					double posXFinal = e.getX();
					double posYFinal = e.getY();
//					System.out.println(String.format("Fin: (%f,%f)", posXFinal,posYFinal));
					
					/* Si orientation de la voiture est Horizontale, alors je vais comparer deltaX pour un déplacement Horizontale. 
					 * Sinon, je compare deltaY pour déplacement Verticale.
					 */
					if(vtr.getOrientation().equals("H") && vtr.isEstGlisse()) {
						if(posXFinal - posXInitial > 0) {
							vtr.seDeplacer("RIGHT", imgV, lstVoitures, this); 
							if(vtr.getBounds().intersects(pointBut.getX(),pointBut.getY(),1,1)&&vtr.getCouleur().equals("rouge")) {
								Alert joueurGagne = new Alert(AlertType.INFORMATION);
								joueurGagne.setHeaderText("Vous avez gagné ");
								joueurGagne.setTitle("Félicitations !");
								joueurGagne.setGraphic(new ImageView(new Image(getClass().getResource("/images/award.gif").toString())));
								joueurGagne.showAndWait();
							}
						}else {
							vtr.seDeplacer("LEFT", imgV, lstVoitures, this);
						}
						
					}
					if(vtr.getOrientation().equals("V") && vtr.isEstGlisse()){
						if(posYFinal - posYInitial > 0 ) { 
							vtr.seDeplacer("DOWN", imgV, lstVoitures, this);
						}else {
							vtr.seDeplacer("UP", imgV, lstVoitures, this);
						}
					}
					imgV.setFocusTraversable(false);
					vtr.setEstGlisse(false);
					e.consume();
				});
				evnt.consume();
				imgV.setFocusTraversable(false);
			});
			
			/** RequestFocus pour l'objet Voiture lorsque la voiture est cliquée. Aussi, une animation courte a lieu lors du clic.*/
			imgV.setOnMouseClicked(event ->{
				imgV.requestFocus();
				
				Thread t2 = new Thread(()->{
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
				t2.start();
				
				
				/* Saisie de FocusTraversable à l'objet Voiture choisi et seulement à lui. Cela est fait 
				 * avec le but d'empecher le reste des instances Voiture de se déplacer lorsque je clique à une nouvelle Voiture
				 * Le résultat de ne pas faire cela serait donner la possibilité à plusieurs Voitures de se déplacer en même temps
				 * avec la commande du clavier (condition de course ou "racing confition").*/
				imgV.setFocusTraversable(true);   
			
					
				/**
				 * Lorsqu'une touche est appuyée, si la touche est une flèche directionnelle, la voiture se déplacera.
				 * Aussi, si la voiture rouge atteint la sortie, un message de félicitations sera affiché sous forme d'alerte.
				 */
				imgV.setOnKeyPressed(e->{
					String direction = e.getCode().toString();
					vtr.seDeplacer(direction, imgV, lstVoitures, this);
									
					if(vtr.getBounds().intersects(pointBut.getX(),pointBut.getY(),1,1)&&vtr.getCouleur().equals("rouge")&&direction.equals("RIGHT")) {
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
