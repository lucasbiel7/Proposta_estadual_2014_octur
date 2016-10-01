/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.view;

import br.com.SistemaOCTur.dao.HospedagemDAO;
import br.com.SistemaOCTur.entity.Hospedagem;
import br.com.SistemaOCTur.util.Utilidades;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class EscolherQuartoController implements Initializable {
    
    @FXML
    private ComboBox<String> cbAndares;
    @FXML
    private RadioButton rbFazerPedido;
    @FXML
    private RadioButton rbVisualizarPedido;
    
    private ObservableList<String> andares = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (int i = 1; i < 10; i++) {
            andares.add(i + "º Andar");
        }
        cbAndares.setItems(andares);
        cbAndares.getSelectionModel().selectFirst();
    }
    
    @FXML
    private void selecionarAndarMouseReleased(MouseEvent mouseEvent) {
        String andar = ((Label) mouseEvent.getSource()).getText();
        int quarto = Integer.parseInt((cbAndares.getSelectionModel().getSelectedIndex() + 1) + andar);
        List<Hospedagem> hospedagens = new HospedagemDAO().pegarEntreODia(new Date(), quarto);
        System.out.println(hospedagens.size());
        if (hospedagens.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Não é possível fazer pedido, porque não há ninguem no quarto").showAndWait();
        } else {
            if (rbFazerPedido.isSelected()) {
                Utilidades.abrirJanela("FazerPedido", quarto);
            } else {
                Utilidades.abrirJanela("VisualizarPedido", quarto);
            }
        }
        
    }
}
