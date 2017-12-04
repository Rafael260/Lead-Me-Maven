package com.mycompany.lead.me;

import extrator_dados.Extrator;
import fabricas.EstacioFactory;
import fabricas.Fabrica;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        CarregadorTelaLogin carregadorTelaLogin = Fabrica.getInstance().getFactory().createCarregadorTelaLogin();
       // Extrator ext = Fabrica.getInstance().getFactory().createExtrator();
        //ext.atualizarBaseDeDados();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/"+carregadorTelaLogin.coletarNomeTelaLogin()));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        //Teste para criar as tabelas
        stage.setTitle("Lead Me - Login");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
