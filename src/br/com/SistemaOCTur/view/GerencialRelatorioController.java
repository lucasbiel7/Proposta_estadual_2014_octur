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
public class GerencialRelatorioController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void btHospedesActionEvent(ActionEvent actionEvent) {
        Utilidades.inserirPanel(apPrincipal, Utilidades.carregarFXML("RelatorioHospedes"));

    }

    @FXML
    private void btEventosActionEvent(ActionEvent actionEvent) {
        Utilidades.inserirPanel(apPrincipal, Utilidades.carregarFXML("RelatorioEvento"));

    }

    @FXML
    private void btHospedagemActionEvent(ActionEvent actionEvent) {
        Utilidades.inserirPanel(apPrincipal, Utilidades.carregarFXML("RelatorioHospedagem"));

    }

    @FXML
    private void btQuartosActionEvent(ActionEvent actionEvent) {
        Utilidades.inserirPanel(apPrincipal, Utilidades.carregarFXML("RelatorioQuartos"));
    }

    @FXML
    private void btEstatisticosActionEvent(ActionEvent actionEvent) {
        Utilidades.inserirPanel(apPrincipal, Utilidades.carregarFXML("RelatorioEstatisticos"));
    }

}
