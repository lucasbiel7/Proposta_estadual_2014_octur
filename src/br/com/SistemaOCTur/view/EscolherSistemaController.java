/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.view;

import br.com.SistemaOCTur.util.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class EscolherSistemaController implements Initializable {

    @FXML
    private AnchorPane apContainer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void btAreaDoFuncionarioActionEvent(ActionEvent actionEvent) {
        Utilidades.abrirJanela("Login", "Tela Login", true);
        ((Stage) apContainer.getScene().getWindow()).close();
    }

    @FXML
    private void btFazerPedidoActionEvent(ActionEvent actionEvent) {
        Utilidades.abrirJanela("EscolherQuarto", "Escolha o quarto do pedido", true);
        ((Stage) apContainer.getScene().getWindow()).close();
    }
}
