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

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class PrincipalGerencialController implements Initializable {

    @FXML
    private AnchorPane apContainer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btGraficoGerenciaisActionEvent(ActionEvent actionEvent) {
        Utilidades.inserirPanel(apContainer, Utilidades.carregarFXML("GerencialGraficos"));
    }
    @FXML
    private void btRelatoriosActionEvent(ActionEvent actionEvent) {
        Utilidades.inserirPanel(apContainer, Utilidades.carregarFXML("GerencialRelatorio"));
    }
}
