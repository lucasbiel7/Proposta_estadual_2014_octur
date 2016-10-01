/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.view;

import br.com.SistemaOCTur.dao.ModuloDAO;
import br.com.SistemaOCTur.entity.Modulo;
import br.com.SistemaOCTur.util.Sessao;
import br.com.SistemaOCTur.util.Utilidades;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class PrincipalController implements Initializable {

    @FXML
    private Label lbModulo;
    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private Label lbUsuario;
    @FXML
    private Label lbDataHora;

    private List<Modulo> modulos;
    private int atual = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        modulos = new ModuloDAO().pegarTodos(true);
        lbUsuario.setText("Usuário logado: " + Sessao.usuario.toString());
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), (ActionEvent event) -> {
            lbDataHora.setText(Utilidades.toDate(new Date()) + " " + Utilidades.toHour(new Date()));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        carregarModulo();
    }

    @FXML
    private void btProximoActionEvent(ActionEvent actionEvent) {
        if (atual + 1 >= modulos.size()) {
            atual = 0;
        } else {
            atual++;
        }
        carregarModulo();
    }

    @FXML
    private void btAnteriorActionEvent(ActionEvent actionEvent) {
        if (atual - 1 < 0) {
            atual = modulos.size() - 1;
        } else {
            atual--;
        }
        carregarModulo();
    }

    @FXML
    private void btSairActionEvent(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    private void carregarModulo() {
        Modulo modulo = modulos.get(atual);
        lbModulo.setText(modulo.getNome());
        switch (modulo.getId()) {
            case 1:
                Utilidades.inserirPanel(apPrincipal, Utilidades.carregarFXML("PrincipalHospedagem"));
                break;
            case 2:
                Utilidades.inserirPanel(apPrincipal, Utilidades.carregarFXML("PrincipalFinanceiro"));
                break;
            case 3:
                Utilidades.inserirPanel(apPrincipal, Utilidades.carregarFXML("PrincipalManutencao"));
                break;
            case 4:
                Utilidades.inserirPanel(apPrincipal, Utilidades.carregarFXML("PrincipalGerencial"));
                break;
            case 5:
                Utilidades.inserirPanel(apPrincipal, Utilidades.carregarFXML("PrincipalEventos"));
                break;
            case 6:
                Utilidades.inserirPanel(apPrincipal, Utilidades.carregarFXML("PrincipalConfigurador"));
                break;
        }
    }
}
