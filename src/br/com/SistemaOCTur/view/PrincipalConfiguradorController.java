/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.view;

import br.com.SistemaOCTur.util.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class PrincipalConfiguradorController implements Initializable {

    @FXML
    private TreeView<String> tvMenu;
    
    @FXML
    private AnchorPane apContainer;

    

    //Menus
    TreeItem<String> tiPrincipal = new TreeItem<>("Configurações");
    TreeItem<String> tiparametroSistema = new TreeItem<>("Parâmetros do Sistema");
    TreeItem<String> tiManipulacaoUsuario = new TreeItem<>("Manipulação de usuários");
    TreeItem<String> tiPermissaoDeAcesso = new TreeItem<>("Permissão de acesso");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tvMenu.setRoot(tiPrincipal);
        tiPrincipal.getChildren().addAll(tiparametroSistema, tiManipulacaoUsuario, tiPermissaoDeAcesso);
    }

    @FXML
    private void tvMenuMouseReleased(MouseEvent mouseEvent) {
        TreeItem menu = tvMenu.getSelectionModel().getSelectedItem();
        if (menu != null) {
            if (menu.equals(tiparametroSistema)) {
                Utilidades.inserirPanel(apContainer, Utilidades.carregarFXML("ConfiguradorModulos"));
            } else {
                if (menu.equals(tiManipulacaoUsuario)) {
//                    Utilidades.inserirPanel(apContainer, Utilidades.carregarFXML("ConfiguradorModulos"));
                } else {
                    Utilidades.inserirPanel(apContainer, Utilidades.carregarFXML("ConfiguradorPermissaoDeAcesso"));
                }
            }
        }
    }
}
