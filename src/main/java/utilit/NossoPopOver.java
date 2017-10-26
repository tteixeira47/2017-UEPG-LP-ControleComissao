/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilit;

import static config.Config.i18n;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.PopOver;

/**
 *
 * @author Thiago
 */
//Classe que define as configurações de PopOver (janela que aparece por cima do programa)
public class NossoPopOver {

    //Atributo que carrega o FXML da tela
    private FXMLLoader loader;

    //Constructoe: endereço do arquivo FXML da janela, título da janela e nó (geralmente null)
    //Nó é quando a janela em questão está associada a algum elemento da tela anterior (notificações do fb, por exemplo)
    public NossoPopOver(String arquivoFXML,
            String titulo, Node node) {
        try {
            loader = new FXMLLoader();
            //Carrega o arquivo fxml da janela que será mostrada
            loader.setLocation(getClass().getResource(arquivoFXML));
            //Estabelece configurações de internacionalização
            loader.setResources(i18n);

            //Em caso do PopOver não ter nenhum nó
            if (node == null) {
                //Estabelece cena (janela)
                Scene scene = new Scene(loader.load());
                //Cria uma janela com cabeçalho
                Stage stage = new Stage();
                //Coloca a cena dentro da janela
                stage.setScene(scene);
                //Estabelece título da janela
                stage.setTitle(titulo);
                //Modo de inicialização
                stage.initStyle(StageStyle.UTILITY);
                stage.initModality(Modality.APPLICATION_MODAL);
                //Mostra a janela
                stage.show();

                //Com nó
            } else {
                PopOver popOverCRUD = new PopOver();
                popOverCRUD.setContentNode(loader.load());
                popOverCRUD.setAutoFix(true);
                popOverCRUD.setAutoHide(false);
                popOverCRUD.setHideOnEscape(true);
                popOverCRUD.setHeaderAlwaysVisible(true);
                popOverCRUD.setArrowLocation(
                        PopOver.ArrowLocation.TOP_LEFT);
                popOverCRUD.setTitle(titulo);
                popOverCRUD.show(node);

            }

        } catch (IOException ex) {
            Logger.getLogger(NossoPopOver.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Retorna um loader para o FXML
    public FXMLLoader getLoader() {
        return loader;
    }

}
