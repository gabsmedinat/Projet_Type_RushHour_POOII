package application;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Point2D;


/**
 * Classe Voiture contenant les informations de la voiture, et les méthodes définissant son comportement dans la grille de jeu.
 * @author Gabme
 */
public class Voiture implements Runnable{
	private double CASE = 58.3;     // Dimensions de chaque case de la grille ( CASE * CASE) ~ environ
	private int BORDS = 13;
	private String idVoiture;  
	private String couleur;
	private String orientation;
	private int taille;
	private int colOrigine;
	private int linOrigine;
	private String typeVehicule;
	private Image imgVoiture;
	private ImageView imgV_vtr;
	private Bounds dimensions;   					// Limites du perimètre de l'objet pour le système de collisions
	private boolean estGlisse = false;
	private boolean enMouvement = false;
	
	public boolean isEstGlisse() {
		return estGlisse;
	}

	public void setEstGlisse(boolean estGlisse) {
		this.estGlisse = estGlisse;
	}
	
	public void setMouvement(boolean bool) {
		enMouvement = bool;
	}

	/**
	 * Constructeur de classe Voiture.
	 * @param couleur Couleur de la voiture.
	 * @param taille Nombre de cellules utilisées par la Voiture dans la grille.
	 * @param col Colonne d'origine de la voiture (Plus à gauche).
	 * @param lin Ligne d'origine de la voiture (Plus en haut).
	 * @param orientation Horizontale ou Verticale.
	 */
	public Voiture(String couleur, int taille, int col, int lin, String orientation ) {
		this.couleur = couleur;
		this.orientation = orientation;
		this.taille = taille;
		this.colOrigine = col;
		this.linOrigine = lin;
		if(taille == 2) {
			this.typeVehicule = "auto";
		}
		else if(taille == 3) {
			this.typeVehicule = "camion";
		}else { 
			// Morceau de code fait pour parvenir si le document de texte des voitures contient des éléments inconnus.
			Alert alerte = new Alert(Alert.AlertType.WARNING);
			alerte.setContentText("Attention ! Les voitures doivent occuper soit 2 spaces soit 3");
			alerte.showAndWait();
		}
		this.imgVoiture = new Image(getClass().getResource("/images/"+this.typeVehicule+"_"+this.orientation+"_"+this.couleur+".gif").toString());
	}
	
	/**
	 * Getter du paramètre ID.
	 * @return idVoiture
	 */
	public String getID() {
		
		return this.idVoiture;
	}
	
	/**
	 * Crée un Identifiant Unique pour chaque objet Voiture avec le code VTR_[cle]_[typeVehicule]_[orientation]_[couleur]. 
	 * L'argument d'entrée sera la position de l'objet dans l'ArrayList qui le contient.
	 * @param cle
	 */
	public void setID(String cle) {
		// Cette méthode recoit la position de cette voiture dans lstVoitures de la classe JEU afin de créér un numéro unique pour la voiture 
		idVoiture = "VTR_"+cle+this.typeVehicule+"_"+this.orientation+"_"+this.couleur;
	}
	
	
	/**
	 * Méthode utilisée pour définir les coordonnées de l'objet Voiture et son perimètre une fois qu'il est instancié dans son 
	 * parent (Pane). Méthode importante pour le système de collisions.
	 * @param vtr
	 */
	public void setBounds(ImageView vtr) {
		this.imgV_vtr = vtr;
	}
	
	/**
	 * Getter pour obtenir les limites de l'objet Voiture dans la grille.
	 * @return dimensions
	 */
	public Bounds getBounds() {
		dimensions = imgV_vtr.getBoundsInParent();
		return dimensions;
	}
	
	/**
	 * Getter de la couleur de l'objet Voiture
	 * @return couleur
	 */
	public String getCouleur() {
		return couleur;
	}

	/**
	 * Méthode puor définir la couleur de l'objet Voiture.
	 * @param couleur
	 */
	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}

	/**
	 * Getter Orientation de la voiture. Il retournera soit Horizontale soit Verticale.
	 * @return orientation
	 */
	public String getOrientation() {
		return orientation;
	}

	/**
	 * Méthode pour définir l'orientation de l'objet Voiture
	 * @param orientation
	 */
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	/**
	 * Nombre de cellules que l'objet Voiture occupe dans la grille.
	 * @return taille
	 */
	public int getTaille() {
		return taille;
	}

	/**
	 * Méthode pour définir le nombre de cellules que l'objet Voiture occupera dans la grille.
	 * @param taille
	 */
	public void setTaille(int taille) {
		this.taille = taille;
	}

	/**
	 * Getter de la colonne d'origine occupée par l'objet Voiture.
	 * @return colOrigine
	 */
	public int getColOrigine() {
		return colOrigine;
	}

	/**
	 * Méthode pour définir la colonne d'origine de l'objet Voiture
	 * @param colOrigine
	 */
	public void setColOrigine(int colOrigine) {
		this.colOrigine = colOrigine;
	}

	/**
	 * Getter de ligne d'origine de l'objet Voiture
	 * @return linOrigine
	 */
	public int getLinOrigine() {
		return linOrigine;
	}

	/**
	 * Méthode pour définir la ligne d'origine de l'objet Voiture.
	 * @param linOrigine
	 */
	public void setLinOrigine(int linOrigine) {
		this.linOrigine = linOrigine;
	}

	/**
	 * Getter du type de véhicule entre "Camion" et "Auto". Servira pour définir l'identifiant unique de chaque objet Voiture.
	 * @return typeVehicule
	 */
	public String getTypeVehicule() {
		return typeVehicule;
	}

	/**
	 * Méthode pour définir le type de véhicule.
	 * @param typeVehicule
	 */
	public void setTypeVehicule(String typeVehicule) {
		this.typeVehicule = typeVehicule;
	}

	/**
	 * Getter de l'image qui représente l'objet Voiture
	 * @return imgVoiture
	 */
	public Image getImgVoiture() {
		return imgVoiture;
	}

	/**
	 * Méthode pour définir l'image qui représente l'objet Voiture
	 * @param imgVoiture
	 */
	public void setImgVoiture(Image imgVoiture) {
		this.imgVoiture = imgVoiture;
	}


	// J'ai du faire des approximations de la position en X et en Y de chaque casse de la grille en ajoutant un évènement lorsque la grille est
	// cliquée. Lorsqu'elle est cliquée, j'ai obtenue les coordonnées du tableau afin de créér une formule qui s'adapte à la position de la grille. 
	/**
	 * Méthode pour obtenir la coordonnée des abcisses corréspondante au coin supérieur gauche de l'image de l'objet 
	 * Voiture, soit son origine.
	 * @return coordonnéeX
	 */
	public Double getX() {
		Double coordonneeX = this.colOrigine*(CASE+BORDS)+52;   
		return coordonneeX;
	}
	
	/**
	 * Méthode pour obtenir la coordonnée des les ordonnées corréspondante au coin supérieur gauche de l'image de l'objet 
	 * Voiture, soit son origine.
	 * @return coordonnéeX
	 */
	public Double getY() {
		Double coordonneeX = this.linOrigine*(CASE+BORDS)+75;
		return coordonneeX;
	}
	
	/**Pointeur: Il trace un point dynamique durant le deplacement du véhicule. Une fois en arrêt, le pointeur sera effacé.
	 Fonctionnement: La variable pointDirecteur obtiendra ses coordonnées à l'aide de l'Orientation + la direction du mouvement. 
	 Alors, une fois le deplacement commence, un point sera tracé à ~15px en dehors et en direction du déplacement, et: Si le point n'intersecte
	 aucune voiture, ca veut dire qu'il peut se déplacer à cette case. Sinon, mouvement interdit.
	 @param direction Direction du déplacement
	 @paran orientation Orientation de la voiture (Horizontale ou Verticale)
	 **/
	public Point2D genererPointeur(String direction, String orientation) {
		if(orientation.equals("V")) {
			if(direction.equals("UP")) {
				Point2D pointDirecteur = new Point2D(this.getX()+(CASE/2),this.getY()-(CASE/2));
//				System.out.println(String.format("Point généré à (%S,%S)",pointDirecteur.getX(),pointDirecteur.getY()));
				return  pointDirecteur;
			}
			else if(direction.equals("DOWN")) {
				Point2D pointDirecteur = new Point2D(this.getX()+(CASE/2),this.getY()+(taille+0.5)*CASE+(taille*BORDS));
//				System.out.println(String.format("Point généré à (%S,%S)",pointDirecteur.getX(),pointDirecteur.getY()));
				return  pointDirecteur;
			}
			else {
//				System.out.println("orientation V ...pourtant nous sommes dans l'else");
				return new Point2D(0,0);
			}
		}else if(orientation.equals("H")) {
			if(direction.equals("LEFT")) {
				Point2D pointDirecteur = new Point2D(this.getX()-(CASE/2),this.getY()+(CASE/2));
//				System.out.println(String.format("Point généré à (%S,%S)",pointDirecteur.getX(),pointDirecteur.getY()));
				return  pointDirecteur;
			}else if (direction.equals("RIGHT")) {
				Point2D pointDirecteur = new Point2D(this.getX()+(taille+0.5)*CASE+(taille*BORDS),this.getY()+(CASE/2));
//				System.out.println(String.format("Point généré à (%S,%S)",pointDirecteur.getX(),pointDirecteur.getY()));
				return  pointDirecteur;
			}else {
//				System.out.println("orientation H ...pourtant nous sommes dans l'else");
				return new Point2D (0,0);
			}
		}else {
			return new Point2D(0,0);
		}
	
	}
	
	// Si le point detecte une voiture, la méthode retourne vrai
	public boolean detectionCollision(BoundingBox point, ArrayList<Voiture> lstVTR) {
		boolean detection = false;
//		System.out.println(String.format("Le point à comparer est (%f,%f) ",point.getCenterX(),point.getCenterY()));
		for(Voiture v : lstVTR) {
			if((point.intersects(v.getBounds())&&!this.getBounds().intersects(point)) || ( point.getCenterX() < 40 || point.getCenterX() > 480 || point.getCenterY() < 60 || point.getCenterY() > 505 ) ) {
//				System.out.println(String.format("Voiture (%S) détectée ", v.getID()));
				detection = true;
				break;                  
			}
		}
		return detection;
	}
	
	
	
	
	public ImageView getImgV() {
		return imgV_vtr;
	}

	public void setImgV(ImageView imgV_vtr) {
		this.imgV_vtr = imgV_vtr;
	}

	/**
	 * Méthode gestionnaire du mouvement horizontale et/ou verticale de l'objet Voiture dans la grille de jeu.
	 * @param direction
	 * @param imgV
	 * @param lstVTR
	 */
	public synchronized void seDeplacer(String direction, ImageView imgV, ArrayList<Voiture> lstVTR, Jeu jeu) {
		
		if(enMouvement) {
			return;
		}
		setMouvement(true);
		
		BoundingBox pointDansGrille = new BoundingBox(genererPointeur(direction,orientation).getX(),genererPointeur(direction,orientation).getY(),1,1);
		
		if(detectionCollision(pointDansGrille,lstVTR)) {
        	setMouvement(false);
			return;
		}else {
			if(this.orientation.equals("H")) {
				// Code pour les voitures horizontales
				if(direction.equals("RIGHT")) {
					Thread t = new Thread(()->{
						double startX = imgV.getLayoutX();
		                double endX = startX + CASE + BORDS; 
		                double i = startX;
	
		                while (i < endX) {
		                    double pos = i; 
		                    Platform.runLater(() -> {
		                        imgV.setLayoutX(pos); 
		                    });
		                    i += 0.8; 
		                    
		                    try {
		                        Thread.sleep(5); 
		                    } catch (InterruptedException e) {
		                        e.printStackTrace();
		                    }
		                }
	
		                Platform.runLater(() ->{
		                	imgV.setLayoutX(endX);
	                        jeu.ajouterCoup();
	                        this.colOrigine++;
	                        setMouvement(false);
		                });
		            });
					t.start();
				}
				if(direction.equals("LEFT")) {
					Thread t = new Thread(()->{
						double startX = imgV.getLayoutX();
		                double endX = startX - CASE - BORDS; 
		                double i = startX;
	
		                while (endX < i) {
		                    double pos = i; 
		                    Platform.runLater(() -> {
		                        imgV.setLayoutX(pos);
		                    });
		                    i -= 0.8; 
	
		                    try {
		                        Thread.sleep(5); 
		                    } catch (InterruptedException e) {
		                        e.printStackTrace();
		                    }
		                }
	
		                Platform.runLater(() ->{
		                	imgV.setLayoutX(endX);
	                        jeu.ajouterCoup();
		                	this.colOrigine--;
		                	setMouvement(false);
		                });
		            });
					t.start();
				}
			}else { 
				if(direction.equals("UP")) {
					Thread t = new Thread(()->{
						double startY = imgV.getLayoutY();
		                double endY = startY - CASE - BORDS; 
		                double i = startY;
		                
		                while (endY < i) {
		                    double pos = i; 
		                    Platform.runLater(() -> {
		                        imgV.setLayoutY(pos);
		                    });
	
		                    i -= 0.8; 
	
		                    try {
		                        Thread.sleep(5); 
		                    } catch (InterruptedException e) {
		                        e.printStackTrace();
		                    }
		                }
	
		                Platform.runLater(() -> {
		                	imgV.setLayoutY(endY);
	                        jeu.ajouterCoup();
		                	this.linOrigine--;
		                	setMouvement(false);
		                });
	
		            });
					t.start();
				}
				if(direction.equals("DOWN")) {
					Thread t = new Thread(()->{
						double startY = imgV.getLayoutY();
		                double endY = startY + CASE + BORDS; // Distance à se déplacer (58.3 c'est la longueur de chaque casse + 13px des bords)
		                double i = startY;
	
		                while (i < endY) {
		                    double pos = i; 
	
		                    Platform.runLater(() -> {
		                        imgV.setLayoutY(pos);
		                    });
	
		                    i += 0.8; 
	
		                    try {
		                        Thread.sleep(5); 
		                    } catch (InterruptedException e) {
		                        e.printStackTrace();
		                    }
		                }
	
		                Platform.runLater(() -> {
		                	imgV.setLayoutY(endY);
	                        jeu.ajouterCoup();
		                	this.linOrigine++;
		                	setMouvement(false);
		                });
		            });
					t.start();
				}
			}
		}
	}
	
	
	/**
	 * Méthode "overrided" pour afficher l'objet Voiture d'une manière claire et lisible.
	 */
	@Override
	public String toString() {
		return "Couleur : "+this.couleur + " - Origine: ("+ this.colOrigine+","+this.linOrigine+") - Orientation : " + this.orientation + " - Type : "+this.typeVehicule;
	}

	public void start() {
	}

	@Override
	public void run() {
	}
	
}
