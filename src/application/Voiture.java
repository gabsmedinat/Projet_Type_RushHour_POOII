package application;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Transform;

public class Voiture implements Runnable{
	private String couleur;
	private String orientation;
	private int taille;
	private int colOrigine;
	private int linOrigine;
	private String typeVehicule;
	Image imgVoiture;
	
	
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
			Alert alerte = new Alert(Alert.AlertType.WARNING);
			alerte.setContentText("Attention ! Les voitures doivent occuper soit 2 spaces soit 3");
			alerte.showAndWait();
		}
		
		this.imgVoiture = new Image(getClass().getResource("/images/"+this.typeVehicule+"_"+this.orientation+"_"+this.couleur+".gif").toString());
		
	}
	
	
	public String getCouleur() {
		return couleur;
	}


	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}


	public String getOrientation() {
		return orientation;
	}


	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}


	public int getTaille() {
		return taille;
	}


	public void setTaille(int taille) {
		this.taille = taille;
	}


	public int getColOrigine() {
		return colOrigine;
	}


	public void setColOrigine(int colOrigine) {
		this.colOrigine = colOrigine;
	}


	public int getLinOrigine() {
		return linOrigine;
	}


	public void setLinOrigine(int linOrigine) {
		this.linOrigine = linOrigine;
	}


	public String getTypeVehicule() {
		return typeVehicule;
	}


	public void setTypeVehicule(String typeVehicule) {
		this.typeVehicule = typeVehicule;
	}


	public Image getImgVoiture() {
		return imgVoiture;
	}


	public void setImgVoiture(Image imgVoiture) {
		this.imgVoiture = imgVoiture;
	}


	// J'ai du faire des approximations de la position en X et en Y de chaque casse de la grille en ajoutant un évènement lorsque la grille est
	// cliquée. Lorsqu'elle est cliquée, j'ai obtenue les coordonnées du tableau afin de créér une formule qui s'adapte à la position de la grille. 
	public Double getX() {
		Double coordonneeX = this.colOrigine*(58.3+13)+52;   
		return coordonneeX;
	}
	
	public Double getY() {
		Double coordonneeX = this.linOrigine*(58.3+13)+75;
		return coordonneeX;
	}
	
	public void seDeplacer(String direction, ImageView imgV) {
		if(this.orientation.equals("H")) {
			// Code pour les voitures horizontales
			System.out.println("Votre voiture peut se déplacer vers la droite ou vers la gauche");
			if(direction.equals("RIGHT")) {
				System.out.println("->");
				Thread t = new Thread(()->{
					double startX = imgV.getLayoutX();
	                double endX = startX + 58.3 + 13; // Distance à se déplacer (58.3 c'est la longueur de chaque casse + 13px des bords)
	                double i = startX;

	                while (i < endX) {

	                    double pos = i; 

	                    Platform.runLater(() -> {
	                        imgV.setLayoutX(pos);
	                    });

	                    i += 0.8; 

	                    try {
	                        Thread.sleep(10); 
	                    } catch (InterruptedException e) {
	                        e.printStackTrace();
	                    }
	                }

	                Platform.runLater(() -> imgV.setLayoutX(endX));

	            });
					
				
				t.start();
			}
			if(direction.equals("LEFT")) {
				System.out.println("<-");
				Thread t = new Thread(()->{
					double startX = imgV.getLayoutX();
	                double endX = startX - 58.3 - 13; // Distance à se déplacer (58.3 c'est la longueur de chaque casse + 13px des bords)
	                double i = startX;

	                while (endX < i) {

	                    double pos = i; 

	                    Platform.runLater(() -> {
	                        imgV.setLayoutX(pos);
	                    });

	                    i -= 0.8; 

	                    try {
	                        Thread.sleep(10); 
	                    } catch (InterruptedException e) {
	                        e.printStackTrace();
	                    }
	                }

	                Platform.runLater(() -> imgV.setLayoutX(endX));

	            });
					
				
				t.start();
			}
		}else {
			// Code pour les voitures Verticales 
			System.out.println("Votre voiture peut se déplacer vers le haut ou vers le bas");
			if(direction.equals("UP")) {
				System.out.println("^");
				Thread t = new Thread(()->{
					double startY = imgV.getLayoutY();
	                double endY = startY - 58.3 - 13; // Distance à se déplacer (58.3 c'est la longueur de chaque casse + 13px des bords)
	                double i = startY;

	                while (endY < i) {

	                    double pos = i; 

	                    Platform.runLater(() -> {
	                        imgV.setLayoutY(pos);
	                    });

	                    i -= 0.8; 

	                    try {
	                        Thread.sleep(10); 
	                    } catch (InterruptedException e) {
	                        e.printStackTrace();
	                    }
	                }

	                Platform.runLater(() -> imgV.setLayoutY(endY));

	            });
					
				
				t.start();
			}
			if(direction.equals("DOWN")) {
				System.out.println("v");
				Thread t = new Thread(()->{
					double startY = imgV.getLayoutY();
	                double endY = startY + 58.3 + 13; // Distance à se déplacer (58.3 c'est la longueur de chaque casse + 13px des bords)
	                double i = startY;

	                while (i < endY) {

	                    double pos = i; 

	                    Platform.runLater(() -> {
	                        imgV.setLayoutY(pos);
	                    });

	                    i += 0.8; 

	                    try {
	                        Thread.sleep(10); 
	                    } catch (InterruptedException e) {
	                        e.printStackTrace();
	                    }
	                }

	                Platform.runLater(() -> imgV.setLayoutY(endY));

	            });
					
				
				t.start();
			}
		}

	}
	
	@Override
	public String toString() {
		return "Couleur : "+this.couleur + " - Origine: ("+ this.colOrigine+","+this.linOrigine+") - Orientation : " + this.orientation + " - Type : "+this.typeVehicule;
	}

	public void start() {
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
