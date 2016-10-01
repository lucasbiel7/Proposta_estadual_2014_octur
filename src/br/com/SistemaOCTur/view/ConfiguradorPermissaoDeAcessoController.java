/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.view;

import br.com.SistemaOCTur.dao.ModuloDAO;
import br.com.SistemaOCTur.dao.PermissaoDAO;
import br.com.SistemaOCTur.dao.PermissaoUsuarioDAO;
import br.com.SistemaOCTur.dao.UsuarioDAO;
import br.com.SistemaOCTur.entity.Modulo;
import br.com.SistemaOCTur.entity.Permissao;
import br.com.SistemaOCTur.entity.PermissaoUsuario;
import br.com.SistemaOCTur.entity.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class ConfiguradorPermissaoDeAcessoController implements Initializable {

    @FXML
    private ComboBox<Usuario> cbUsuario;
    @FXML
    private ComboBox<Modulo> cbModulo;
    @FXML
    private GridPane gpPermissoes;

    private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
    private ObservableList<Modulo> modulos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarios.setAll(new UsuarioDAO().pegarTodos());
        modulos.setAll(new ModuloDAO().pegarTodos());
        cbUsuario.setItems(usuarios);
        cbModulo.setItems(modulos);
    }

    @FXML
    private void cbFiltrosActionEvent(ActionEvent actionEvent) {
        carregarPermissoesUsuario();

    }

    private void carregarPermissoesUsuario() {
        gpPermissoes.getChildren().clear();
        int i=0;
        if (cbUsuario.getSelectionModel().getSelectedItem() != null && cbModulo.getSelectionModel().getSelectedItem() != null) {
            for (Permissao permissao : new PermissaoDAO().pegarTodosPorModulo(cbModulo.getSelectionModel().getSelectedItem())) {
                CheckBox checkBox = new CheckBox(permissao.getNome());
                checkBox.getStyleClass().add("checkColorido");
                PermissaoUsuario permissaoUsuario = new PermissaoUsuarioDAO().pegarPorUsuarioPermissao(cbUsuario.getSelectionModel().getSelectedItem(), permissao);
                if (permissaoUsuario != null) {
                    checkBox.setSelected(permissaoUsuario.isAtivo());
                }
                checkBox.setOnAction(new AlterarPermissao(cbUsuario.getSelectionModel().getSelectedItem(), permissao));
                gpPermissoes.addRow(i,checkBox);
                i++;
            }
        }
    }

    private class AlterarPermissao implements EventHandler<ActionEvent> {

        private Usuario usuario;
        private Permissao permissao;

        public AlterarPermissao(Usuario usuario, Permissao permissao) {
            this.usuario = usuario;
            this.permissao = permissao;
        }

        @Override
        public void handle(ActionEvent event) {
            PermissaoUsuario permissaoUsuario = new PermissaoUsuarioDAO().pegarPorUsuarioPermissao(usuario, permissao);
            if (permissaoUsuario != null) {
                permissaoUsuario.setAtivo(!permissaoUsuario.isAtivo());
                new PermissaoUsuarioDAO().editar(permissaoUsuario);
            } else {
                permissaoUsuario = new PermissaoUsuario();
                permissaoUsuario.setPermissao(permissao);
                permissaoUsuario.setUsuario(usuario);
                permissaoUsuario.setAtivo(true);
                new PermissaoUsuarioDAO().cadastrar(permissaoUsuario);
            }
            carregarPermissoesUsuario();
        }

    }
}
