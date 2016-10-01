/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.view;

import br.com.SistemaOCTur.dao.ModuloDAO;
import br.com.SistemaOCTur.entity.Modulo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class ConfiguradorModulosController implements Initializable {

    @FXML
    private GridPane gpModulos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarModulos();
    }

    private void carregarModulos() {
        gpModulos.getChildren().clear();
        int i = 0;
        for (Modulo modulo : new ModuloDAO().pegarTodos()) {
            Label label = new Label(modulo.getNome());
            Button button = new Button(modulo.isAtivo() ? "Desativar" : "Ativar");
            button.setOnAction(new MudarModulo(modulo));
            gpModulos.addRow(i, label, button);
            i++;
        }
    }

    private class MudarModulo implements EventHandler<ActionEvent> {

        private Modulo modulo;

        public MudarModulo(Modulo modulo) {
            this.modulo = modulo;
        }

        @Override
        public void handle(ActionEvent event) {
            modulo.setAtivo(!modulo.isAtivo());
            new ModuloDAO().editar(modulo);
            carregarModulos();
        }
    }
}
