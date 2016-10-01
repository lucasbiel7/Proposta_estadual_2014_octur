/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.view;

import br.com.SistemaOCTur.dao.CargoDAO;
import br.com.SistemaOCTur.dao.UsuarioDAO;
import br.com.SistemaOCTur.entity.Usuario;
import br.com.SistemaOCTur.util.Sessao;
import br.com.SistemaOCTur.util.Utilidades;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class LoginController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private Button bt1;
    @FXML
    private Button bt2;
    @FXML
    private Button bt3;
    @FXML
    private Button bt4;
    @FXML
    private Button bt5;
    @FXML
    private PasswordField pfSenha;
    @FXML
    private TextField tfUsuario;
    @FXML
    private RadioButton rbAdministrador;
    @FXML
    private RadioButton rbUsuarios;
    @FXML
    private RadioButton rbAmbos;
    @FXML
    private GridPane gpUsuarios;
    @FXML
    private Label lbDataHora;

    private List<String> teclado;
    private Usuario usuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        teclado = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            teclado.add("" + i);
        }
        teclado.sort((String o1, String o2) -> new Random().nextInt(3) - 1);
        bt1.setText(teclado.get(0) + "-" + teclado.get(1));
        bt2.setText(teclado.get(2) + "-" + teclado.get(3));
        bt3.setText(teclado.get(4) + "-" + teclado.get(5));
        bt4.setText(teclado.get(6) + "-" + teclado.get(7));
        bt5.setText(teclado.get(8) + "-" + teclado.get(9));
        bt1.setOnAction(new TecladoVirtual(1));
        bt2.setOnAction(new TecladoVirtual(2));
        bt3.setOnAction(new TecladoVirtual(3));
        bt4.setOnAction(new TecladoVirtual(4));
        bt5.setOnAction(new TecladoVirtual(5));
        carregarUsuarios(new UsuarioDAO().pegarTodos());
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), (ActionEvent event) -> {
            lbDataHora.setText(Utilidades.toDate(new Date()) + " " + Utilidades.toHour(new Date()));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void jpSenhaMouseReleased(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Utilize o teclado virtual para digitar sua Senha");
        alert.showAndWait();
    }

    @FXML
    private void btEntrarActionEvent(ActionEvent actionEvent) {
        if (usuario == null) {
            usuario = new UsuarioDAO().login(tfUsuario.getText());
        }
        if (usuario == null) {
            new Alert(Alert.AlertType.ERROR, "Usuário incorreto").showAndWait();
        } else {
            if (pfSenha.getText().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Senha vazia").showAndWait();
            } else {
                int countSenha = 0;
                for (int i = 0; i < pfSenha.getText().length(); i++) {
                    int value = Integer.parseInt(pfSenha.getText().substring(i, i + 1));
                    String letraSenha = usuario.getSenha().substring(i, i + 1);
                    switch (value) {
                        case 1:
                            if (letraSenha.equals(teclado.get(0)) || letraSenha.equals(teclado.get(1))) {
                                countSenha++;
                            }
                            break;
                        case 2:
                            if (letraSenha.equals(teclado.get(2)) || letraSenha.equals(teclado.get(3))) {
                                countSenha++;
                            }
                            break;
                        case 3:
                            if (letraSenha.equals(teclado.get(4)) || letraSenha.equals(teclado.get(5))) {
                                countSenha++;
                            }
                            break;
                        case 4:
                            if (letraSenha.equals(teclado.get(6)) || letraSenha.equals(teclado.get(7))) {
                                countSenha++;
                            }
                            break;
                        case 5:
                            if (letraSenha.equals(teclado.get(8)) || letraSenha.equals(teclado.get(9))) {
                                countSenha++;
                            }
                            break;
                    }
                }
                if (countSenha >= 6) {
                    ((Stage) apPrincipal.getScene().getWindow()).close();
                    Sessao.usuario = usuario;
                    Utilidades.abrirJanela("Loading", "Carregando sistema", true);
                } else {
                    pfSenha.setText("");
                    new Alert(Alert.AlertType.ERROR, "Senha Incorreta").showAndWait();
                }
            }
        }
    }

    @FXML
    private void btLimparActionEvent(ActionEvent actionEvent) {
        tfUsuario.clear();
        pfSenha.clear();
        filtrarUsuarios();
    }

    @FXML
    private void btCancelarActionEvent(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void tfLoginKeyRelease(KeyEvent keyEvent) {
        filtrarUsuarios();
        usuario = null;
    }

    @FXML
    private void rbFiltrarActionEvent(ActionEvent actionEvent) {
        filtrarUsuarios();
    }

    private void carregarUsuarios(List<Usuario> usuarios) {
        gpUsuarios.getChildren().clear();
        int i = 0;
        for (Usuario usuario : usuarios) {
            if (usuario.getFoto() != null) {
                Button button = new Button(usuario.getNome() + "\n"
                        + usuario.getCargo());
                ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(usuario.getFoto())));
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
                button.setGraphic(imageView);
                button.setOnAction(new SelecionarUsuario(usuario));
                gpUsuarios.addRow(i, button);
                i++;
            }
        }
    }

    public class SelecionarUsuario implements EventHandler<ActionEvent> {

        private Usuario usuarioSelecionado;

        public SelecionarUsuario(Usuario usuarioSelecionado) {
            this.usuarioSelecionado = usuarioSelecionado;
        }

        @Override
        public void handle(ActionEvent event) {
            usuario = usuarioSelecionado;
            tfUsuario.setText(usuario.getNome());
            filtrarUsuarios(usuario);
        }

        private void filtrarUsuarios(Usuario usuario) {
            gpUsuarios.getChildren().clear();
            if (usuario.getFoto() != null) {
                Button button = new Button(usuario.getNome() + "\n"
                        + usuario.getCargo());
                ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(usuario.getFoto())));
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
                button.setGraphic(imageView);
                button.setOnAction(new SelecionarUsuario(usuario));
                gpUsuarios.addRow(0, button);
            }

        }
    }

    private void filtrarUsuarios() {
        if (rbUsuarios.isSelected()) {
            carregarUsuarios(new UsuarioDAO().pegarTodosPorNaoCargo(tfUsuario.getText(), new CargoDAO().pegarPorId(17)));
        } else {
            if (rbAmbos.isSelected()) {
                carregarUsuarios(new UsuarioDAO().pegarTodosPorNome(tfUsuario.getText()));
            } else {
                carregarUsuarios(new UsuarioDAO().pegarTodosPorCargo(tfUsuario.getText(), new CargoDAO().pegarPorId(17)));
            }
        }
    }

    public class TecladoVirtual implements EventHandler<ActionEvent> {

        private int tecla;

        public TecladoVirtual(int tecla) {
            this.tecla = tecla;
        }

        @Override
        public void handle(ActionEvent event) {
            pfSenha.setText(pfSenha.getText() + tecla);
            if (pfSenha.getText().length() >= 6) {
                btEntrarActionEvent(event);
            }
        }
    }
}
