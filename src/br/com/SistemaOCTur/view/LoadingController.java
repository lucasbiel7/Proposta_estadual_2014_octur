/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.view;

import br.com.SistemaOCTur.util.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class LoadingController implements Initializable {

    @FXML
    private ProgressBar pbLoading;
    @FXML
    private Label lbProgresso;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Task carregarProgresso = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i < 101; i++) {
                    updateProgress(i, 100);
                    String texto = "";
                    if (i <= 25) {

                        texto = i + "% - Carregando Banco de dados";
                    } else {
                        if (i <= 50) {
                            texto = i + "% - Carregando Sistema";
                        } else {
                            if (i < 99) {
                                texto = i + "% - Verificando nível de Acesso";
                            } else {
                                texto = i + "% - Sistema Carregado com sucesso";
                            }
                        }
                    }
                    String a = texto;
                    Platform.runLater(new Runnable() {

                        public void run() {
                            lbProgresso.setText(a);
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LoadingController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                Platform.runLater(() -> {
                    ((Stage) pbLoading.getScene().getWindow()).close();
                    Stage stage = Utilidades.abrirJanela("Principal", Utilidades.NOME_PROGRAMA, true,true);
                });
                return null;
            }
        };
        pbLoading.progressProperty().bind(carregarProgresso.progressProperty());
        new Thread(carregarProgresso).start();
    }

}
