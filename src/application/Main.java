package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
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
	private BorderPane root = new BorderPane();
	private Scene scene;
	private Stage stage;
	private Jeu game;
	
	// MENU PRINCIPAL
	private HBox hbox ;
	private Image imgTitre;
	private Image imgJeuFacile;
	private Image imgJeuMoyen ;
	private Image imgJeuDIfficile ;
	private BackgroundSize taille_image;
	private BackgroundImage image_fond;
	private ImageView logo;
	private ImageView jeuFacile;
	private ImageView jeuMoyen;
	private ImageView jeuDifficile;
	
	// JEU
	private HBox hBox2;
	private Image imgArrierePlan;
	
	private VBox vBoxGauche;
	private Chronometre temps;
	private Label lblTemps;
	private Label lblCoups;
	private Label btnReinit;
	private Label btnMenu;
	
	
	private BorderPane grilleJeu;
	private Pane panneauJeu;
	private Image imgGrille;
	private ImageView grille;
	
	private String dernierJeu = "";
	
	
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
	
	/**
	 * Méthode de création du menu Pricipal. Faite pour mieux organiser le code et séparer le code destiné à creer le menu
	 */
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
	
	
	/**
	 * Méthode de configuration du menu Pricipal. Faite pour mieux organiser le code et séparer le code destiné à définir le comportement du 
	 * menu principal.
	 */
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
//			System.out.println("Vous avez cliqué sur: "+imgCliquee.getId());
			creerJeu(imgCliquee.getId());
		});
		
		jeuMoyen.setFocusTraversable(true);
		jeuMoyen.setId("moyen");
		jeuMoyen.setOnMouseClicked(event ->{
			ImageView imgCliquee = (ImageView) event.getTarget();
			dernierJeu = imgCliquee.getId();
//			System.out.println("Vous avez cliqué sur: "+imgCliquee.getId());
			creerJeu(imgCliquee.getId());
		});
		
		jeuDifficile.setFocusTraversable(true);
		jeuDifficile.setId("difficile");
		jeuDifficile.setOnMouseClicked(event ->{
			
			ImageView imgCliquee = (ImageView) event.getTarget();
			dernierJeu = imgCliquee.getId();
//			System.out.println("Vous avez cliqué sur: "+imgCliquee.getId());
//			System.out.println(imgCliquee.getTypeSelector());
			creerJeu(imgCliquee.getId());
		});
		
		
	}
	
	/** 
	 * Méthode d'assemblage des composants graphiques du menu Pricipal. Faite pour mieux organiser le code et séparer le code destiné à 
	 * armer le menu et ses composants.
	 */
	public void assemblerMenuPrincipal() {
		root.setTop(logo);
		hbox.getChildren().addAll(jeuFacile, jeuMoyen, jeuDifficile);
		root.setCenter(hbox);
		stage.setWidth(600);
		stage.setHeight(400);
		stage.centerOnScreen();
	}
	
	
	/**
	 * Méthode de création du jeu. Faite pour mieux organiser le code et séparer le code destiné à creer l'interface graphique du jeu
	 * @param difficulte Difficulté parmi "facile","moyen" et "difficile"
	 */
	public void creerJeu(String difficulte) {
		vBoxGauche = new VBox(20);
		grilleJeu = new BorderPane();
		hBox2 = new HBox(10);
		
		
		btnReinit = new Label("Réinitialiser");
		btnMenu = new Label("Retour au menu");

		game = new Jeu(difficulte);

		temps = new Chronometre();
		lblTemps = new Label("00:00");
		lblCoups = game.GenererCoups();																				// ******TODO
		
		imgGrille = new Image(getClass().getResource("/images/grille.gif").toString());
		grille = new ImageView(imgGrille);
		panneauJeu = new Pane(grille);
		
		configurerJeu();  
		}
	
	/** Lignes de code destinées à la configuration, mise en forme et le fonctionnement du jeu
	 * 
	 */
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
		
		
		/**
		 * Retour au menu principale
		 */
		btnMenu.setOnMouseClicked(event -> {
//			System.out.println("Type : "+event.getEventType());
			temps.arreterChronometre();
			scene.setRoot(root);
			creerMenuPrincipal();
			configurerMenuPrincipal();
			assemblerMenuPrincipal();
		});
		
		assemblerJeu();
		
		
		
		/**
		 * Utilisé pour identifier les coordonnées d'un endroit cliqué dans la grille. À savoir: Détection exclusivement de la grille et non
		 * pas des voitures ou autres elements en dehors.
		 */
		grille.setOnMouseClicked(event ->{
			System.out.println("Souris cliquée dans le point P("+event.getX()+","+event.getY()+")");
		});
	}
	
	/** Méthode faite pour mieux organiser le code main, et séparer les lignes de code destinées à armer les composants du jeu
	 * 
	 */
	public void assemblerJeu() {
		scene.setRoot(hBox2);                     // Changer le layout qui sera affiché sur scene. 
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
