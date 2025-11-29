package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class Main extends Application {
	BorderPane root = new BorderPane();
	Scene scene;
	Stage stage;
	Jeu game;
	
	// MENU PRINCIPAL
	HBox hbox ;
	Image imgTitre;
	Image imgJeuFacile;
	Image imgJeuMoyen ;
	Image imgJeuDIfficile ;
	BackgroundSize taille_image;
	BackgroundImage image_fond;
	ImageView logo;
	ImageView jeuFacile;
	ImageView jeuMoyen;
	ImageView jeuDifficile;
	
	// JEU
	HBox hBox2;
	Image imgArrierePlan;
	
	VBox vBoxGauche;
	Chronometre temps;
	Label lblTemps;
	Label lblCoups;
	Label btnReinit;
	Label btnMenu;
	
	
	BorderPane grilleJeu;
	Pane panneauJeu;
	Image imgGrille;
	ImageView grille;
	
	String dernierJeu = "";
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			
			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			

			
			Image appIcon = new Image(getClass().getResource("/images/icone.png").toString());
			stage.getIcons().add(appIcon);
			
			stage.setTitle("Projet: Rush Hours par Gabriel Medina");

			creerMenuPrincipal();
			configurerMenuPrincipal();
			assemblerMenuPrincipal();

			stage.setScene(scene);
			stage.show();
			stage.setResizable(false);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void creerMenuPrincipal() {
		hbox = new HBox(30);
		imgTitre = new Image(getClass().getResource("/images/logo.gif").toString());
		imgJeuFacile = new Image(getClass().getResource("/images/mini_facile.png").toString());
		imgJeuMoyen = new Image(getClass().getResource("/images/mini_moyen.png").toString());
		imgJeuDIfficile = new Image(getClass().getResource("/images/mini_diff.png").toString());
		
				
		logo = new ImageView(imgTitre);
		jeuFacile = new ImageView(imgJeuFacile);
		jeuMoyen = new ImageView(imgJeuMoyen);
		jeuDifficile = new ImageView(imgJeuDIfficile);
	}
	
	public void configurerMenuPrincipal() {
		root.setAlignment(logo, Pos.CENTER);
		root.setMargin(logo, new Insets(50,0,0,0));
		root.setBackground(Background.fill(Color.BLUE));
		hbox.setAlignment(Pos.CENTER);
		
		jeuFacile.setFocusTraversable(true);
		jeuFacile.setId("facile");
		jeuFacile.setOnMouseClicked(event ->{
			ImageView imgCliquee = (ImageView) event.getTarget();
			dernierJeu = imgCliquee.getId();
			System.out.println("Vous avez cliqué sur: "+imgCliquee.getId());
			creerJeu(imgCliquee.getId());
		});
		
		jeuMoyen.setFocusTraversable(true);
		jeuMoyen.setId("moyen");
		jeuMoyen.setOnMouseClicked(event ->{
			ImageView imgCliquee = (ImageView) event.getTarget();
			System.out.println("Vous avez cliqué sur: "+imgCliquee.getId());
			creerJeu(imgCliquee.getId());
		});
		
		jeuDifficile.setFocusTraversable(true);
		jeuDifficile.setId("difficile");
		jeuDifficile.setOnMouseClicked(event ->{
			
			ImageView imgCliquee = (ImageView) event.getTarget();
			System.out.println("Vous avez cliqué sur: "+imgCliquee.getId());
			System.out.println(imgCliquee.getTypeSelector());
			creerJeu(imgCliquee.getId());
		});
		
		
	}
	
	public void assemblerMenuPrincipal() {
		root.setTop(logo);
		hbox.getChildren().addAll(jeuFacile, jeuMoyen, jeuDifficile);
		root.setCenter(hbox);
		stage.setWidth(600);
		stage.setHeight(400);
		stage.centerOnScreen();

	}
	
	
	
	public void creerJeu(String difficulte) {
		vBoxGauche = new VBox(20);
		grilleJeu = new BorderPane();
		
		lblTemps = new Label("00:00");
		btnReinit = new Label("Réinitialiser");
		btnMenu = new Label("Retour au menu");
		
		
		hBox2 = new HBox(10);
		hBox2.setBackground(Background.fill(Color.BLACK));
		scene.setRoot(hBox2);

		game = new Jeu(difficulte);
		lblCoups = new Label(game.getCoups());
		
		imgGrille = new Image(getClass().getResource("/images/grille.gif").toString());
		grille = new ImageView(imgGrille);
		
		panneauJeu = new Pane(grille);
		
		

		configurerJeu();
		
		}
	
	public void configurerJeu() {
		imgArrierePlan = new Image(getClass().getResource("/images/jaunenoir.gif").toString());
		BackgroundSize tailleBg = new BackgroundSize(1.0,1.0,true,true,false,false);
		BackgroundImage imgArrPlan = new BackgroundImage(imgArrierePlan,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,tailleBg);
		hBox2.setBackground(new Background(imgArrPlan));
		hBox2.setPadding(new Insets(10));
		
		// Côté des indicateurs (Gauche)
		vBoxGauche.setSpacing(50);
		vBoxGauche.setPadding(new Insets(5));
		vBoxGauche.setBackground(Background.fill(Color.WHITE));
		vBoxGauche.setAlignment(Pos.CENTER);
		
		
		BorderStroke bdStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,new CornerRadii(5), new BorderWidths(2.0) );

		
		temps = new Chronometre();
		temps.start();
		lblTemps = (temps.getLabel());
		
		lblCoups.setBorder(new Border(bdStroke));
		lblCoups.setPadding(new Insets(5,15,5,15));

		btnReinit.setBorder(new Border(bdStroke));
		btnReinit.setPadding(new Insets(5,15,5,15));
		
		/*
		 * J'avais préalablement gargé en mémoire l'imageView du dernier jeu afin de pouvoir obtenir son ID, et faire un nouveau jeu avec
		 * */
		btnReinit.setOnMouseClicked(event ->{
			temps.arreterChronometre();
			creerMenuPrincipal();
			configurerMenuPrincipal();
			assemblerMenuPrincipal();
			creerJeu(dernierJeu);
		});
		
		btnMenu.setBorder(new Border(bdStroke));
		btnMenu.setPadding(new Insets(5,15,5,15));
		
		btnMenu.setOnMouseClicked(event -> {
			System.out.println("Type : "+event.getEventType());
			temps.arreterChronometre();
			scene.setRoot(root);
			creerMenuPrincipal();
			configurerMenuPrincipal();
			assemblerMenuPrincipal();
		});
		
		assemblerJeu();
		
		
		
		// Tests
		grille.setOnMouseClicked(event ->{
			System.out.println("("+event.getX()+","+event.getY()+")");
		});
		
		
		
	}
	
	public void assemblerJeu() {
		
		vBoxGauche.getChildren().addAll(lblTemps,lblCoups,btnReinit,btnMenu);
		grilleJeu.setCenter(panneauJeu);
		hBox2.getChildren().addAll(vBoxGauche, grilleJeu);
		stage.sizeToScene();
		stage.centerOnScreen();
		game.afficheVoitures(panneauJeu);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
