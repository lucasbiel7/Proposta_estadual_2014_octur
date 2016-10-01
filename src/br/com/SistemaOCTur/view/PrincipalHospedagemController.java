/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.view;

import br.com.SistemaOCTur.dao.PermissaoDAO;
import br.com.SistemaOCTur.dao.PermissaoUsuarioDAO;
import br.com.SistemaOCTur.entity.Permissao;
import br.com.SistemaOCTur.entity.PermissaoUsuario;
import br.com.SistemaOCTur.util.Sessao;
import br.com.SistemaOCTur.util.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class PrincipalHospedagemController implements Initializable {

    @FXML
    private Button btCadastroHospede;
    @FXML
    private Button btCadastroApartamentos;
    @FXML
    private Button btControleReservas;
    @FXML
    private Button btLancamentoDespesa;
    @FXML
    private Button btFechamentoDeConta;
    @FXML
    private Button btRelatorio;
    @FXML
    private Button btConsultas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        desabilitarBotao(btCadastroHospede, new PermissaoDAO().pegarPorId(1));
        desabilitarBotao(btCadastroApartamentos, new PermissaoDAO().pegarPorId(2));
        desabilitarBotao(btControleReservas, new PermissaoDAO().pegarPorId(3));
        desabilitarBotao(btLancamentoDespesa, new PermissaoDAO().pegarPorId(4));
        desabilitarBotao(btFechamentoDeConta, new PermissaoDAO().pegarPorId(5));
        desabilitarBotao(btRelatorio, new PermissaoDAO().pegarPorId(6));
        desabilitarBotao(btConsultas, new PermissaoDAO().pegarPorId(7));

    }

    private void desabilitarBotao(Button button, Permissao permissao) {
        PermissaoUsuario permissaoUsuario = new PermissaoUsuarioDAO().pegarPorUsuarioPermissao(Sessao.usuario, permissao);
        if (permissaoUsuario != null) {
            button.setDisable(!permissaoUsuario.isAtivo());
        } else {
            button.setDisable(true);
        }
    }
    
    @FXML
    private void btControlReservaActionEvent(ActionEvent actionEvent) {
        Utilidades.abrirJanela("HospedagemReserva");
    }
}
