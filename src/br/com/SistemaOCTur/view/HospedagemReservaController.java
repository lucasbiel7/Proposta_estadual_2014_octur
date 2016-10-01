/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.view;

import br.com.SistemaOCTur.dao.ClienteDAO;
import br.com.SistemaOCTur.dao.HospedagemDAO;
import br.com.SistemaOCTur.dao.TipoApartamentoDAO;
import br.com.SistemaOCTur.entity.Cliente;
import br.com.SistemaOCTur.entity.Hospedagem;
import br.com.SistemaOCTur.entity.TipoApartamento;
import br.com.SistemaOCTur.util.Utilidades;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class HospedagemReservaController implements Initializable {

    //Dados hospedes
    @FXML
    private TextField tfCodigo;
    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfSobrenome;
    @FXML
    private ComboBox<String> cbGenero;
    @FXML
    private TextField tfCidade;
    @FXML
    private TextField tfTelefone;
    @FXML
    private DatePicker dpNascimento;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfDocumentoIdentidade;
    @FXML
    private Label lbValorTotal;
    //Dados hospedagem
    @FXML
    private DatePicker dpEntrada;
    @FXML
    private DatePicker dpSaida;
    @FXML
    private ComboBox<TipoApartamento> cbTipoApto;
    @FXML
    private TextField tfValorDiarioPorPessoa;
    @FXML
    private ComboBox<Integer> cbQuantidadePessoas;
    @FXML
    private ComboBox<String> cbStatus;
    @FXML
    private ComboBox<String> cbNumeroApto;
    @FXML
    private ComboBox<String> cbMotivoViagem;
    //Tabela
    @FXML
    private TableView<Hospedagem> tvHospedagem;
    @FXML
    private TableColumn<Hospedagem, Integer> tcId;
    @FXML
    private TableColumn<Hospedagem, String> tcNome;
    @FXML
    private TableColumn<Hospedagem, Date> tcEntrada;
    @FXML
    private TableColumn<Hospedagem, Date> tcSaida;
    @FXML
    private TableColumn<Hospedagem, String> tcApto;
    @FXML
    private TableColumn<Hospedagem, String> tcTipoApto;
    @FXML
    private TableColumn<Hospedagem, String> tcStatus;
    @FXML
    private ImageView ivFoto;
    private Hospedagem hospedagem;
    //Listas
    private ObservableList<String> generos = FXCollections.observableArrayList();
    private ObservableList<TipoApartamento> tipoApartamentos = FXCollections.observableArrayList();
    private ObservableList<Integer> quantidadePessoas = FXCollections.observableArrayList();
    private ObservableList<String> status = FXCollections.observableArrayList();
    private ObservableList<String> numeroApto = FXCollections.observableArrayList();
    private ObservableList<String> motivos = FXCollections.observableArrayList();

    private ObservableList<Hospedagem> hospedagems = FXCollections.observableArrayList();

    private Cliente cliente;
    private double total = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Carregando listas
        generos.setAll("Masculino", "Feminino");
        tipoApartamentos.setAll(new TipoApartamentoDAO().pegarTodos());
        for (int i = 0; i < 1000; i++) {
            quantidadePessoas.add(i);
        }
        status.setAll("Hospedado", "Check-in", "Check-out", "Reservado", "Cancelado");
        motivos.setAll("Compras", "Congresso", "Estudos", "Negócios", "Passeio", "Saúde", "Outros");
        //Carregando componentes
        cbGenero.setItems(generos);
        cbTipoApto.setItems(tipoApartamentos);
        cbQuantidadePessoas.setItems(quantidadePessoas);
        cbStatus.setItems(status);
        cbNumeroApto.setItems(numeroApto);
        cbQuantidadePessoas.getSelectionModel().selectFirst();
        cbMotivoViagem.setItems(motivos);
        tvHospedagem.setItems(hospedagems);
        //Carregar Tabela
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcNome.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        tcEntrada.setCellValueFactory(new PropertyValueFactory<>("entrada"));
        tcSaida.setCellValueFactory(new PropertyValueFactory<>("saida"));
        tcTipoApto.setCellValueFactory(new PropertyValueFactory<>("tipoApartamento"));
        tcApto.setCellValueFactory(new PropertyValueFactory<>("apartamento"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        hospedagems.setAll(new HospedagemDAO().pegarTodos());
    }

    @FXML
    private void cbTipoAptoActionEvent(ActionEvent actionEvent) {
        numeroApto.clear();
        if (cbTipoApto.getSelectionModel().getSelectedItem() != null) {
            for (int i = 1; i < 10; i++) {
                switch (cbTipoApto.getSelectionModel().getSelectedItem().getId()) {
                    case 1:
                        numeroApto.add(i + "01");
                        numeroApto.add(i + "02");
                        break;
                    case 2:
                        numeroApto.add(i + "03");
                        numeroApto.add(i + "04");
                        break;
                    case 3:
                        numeroApto.add(i + "05");
                        numeroApto.add(i + "06");
                        break;
                    case 4:
                        numeroApto.add(i + "07");
                        numeroApto.add(i + "08");
                        break;
                    case 5:
                        numeroApto.add(i + "09");
                        numeroApto.add(i + "10");
                        break;
                }
            }
        }
        calcularTotalActionEvent(null);
    }

    @FXML
    private void keCodigoKeyRelease(ActionEvent keyEvent) {
        if (!tfCodigo.getText().isEmpty()) {
            cliente = new ClienteDAO().pegarPorId(Integer.parseInt(tfCodigo.getText()));
        }
        carregarCliente();
    }

    @FXML
    private void btNovoActionEvent(ActionEvent actionEvent) {
        hospedagem = new Hospedagem();
        hospedagem.setCliente(cliente);
        hospedagem.setEntrada(Date.from(dpEntrada.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        hospedagem.setSaida(Date.from(dpSaida.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        hospedagem.setApartamento(Integer.parseInt(cbNumeroApto.getSelectionModel().getSelectedItem()));
        hospedagem.setMotivo(cbMotivoViagem.getSelectionModel().getSelectedItem());
        hospedagem.setStatus(cbStatus.getSelectionModel().getSelectedItem());
        hospedagem.setTipoApartamento(cbTipoApto.getSelectionModel().getSelectedItem().getNome());
        calcularTotalActionEvent(actionEvent);
        hospedagem.setValor(total);
        new HospedagemDAO().cadastrar(hospedagem);
         hospedagems.setAll(new HospedagemDAO().pegarTodos());
        new Alert(Alert.AlertType.INFORMATION, "Cadastrado com sucesso").showAndWait();
    }

    @FXML
    private void btAlterarActionEvent(ActionEvent actionEvent) {
        if (hospedagem != null) {
            hospedagem.setCliente(cliente);
            hospedagem.setEntrada(Date.from(dpEntrada.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            hospedagem.setSaida(Date.from(dpSaida.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            hospedagem.setApartamento(Integer.parseInt(cbNumeroApto.getSelectionModel().getSelectedItem()));
            hospedagem.setMotivo(cbMotivoViagem.getSelectionModel().getSelectedItem());
            hospedagem.setStatus(cbStatus.getSelectionModel().getSelectedItem());
            hospedagem.setTipoApartamento(cbTipoApto.getSelectionModel().getSelectedItem().getNome());
            calcularTotalActionEvent(actionEvent);
            hospedagem.setValor(total);
            new HospedagemDAO().editar(hospedagem);
            hospedagems.setAll(new HospedagemDAO().pegarTodos());
            new Alert(Alert.AlertType.INFORMATION, "Editado com sucesso").showAndWait();
        } else {
            new Alert(Alert.AlertType.ERROR, "Selecione o usuário para editar").showAndWait();
        }
    }

    @FXML
    private void tvHospedagemMouseReleased(MouseEvent mouseEvent) {
        if (tvHospedagem.getSelectionModel().getSelectedItem() != null) {
            hospedagem = tvHospedagem.getSelectionModel().getSelectedItem();
            tfCodigo.setText("" + hospedagem.getCliente().getId());
            keCodigoKeyRelease(null);
            carregarCliente();
            dpEntrada.setValue(LocalDateTime.ofInstant(Instant.ofEpochMilli(hospedagem.getEntrada().getTime()), ZoneId.systemDefault()).toLocalDate());
            dpSaida.setValue(LocalDateTime.ofInstant(Instant.ofEpochMilli(hospedagem.getSaida().getTime()), ZoneId.systemDefault()).toLocalDate());
            TipoApartamento tipoApartamento = new TipoApartamentoDAO().pegarPorNome(hospedagem.getTipoApartamento());
            if (tipoApartamento != null) {
                cbTipoApto.getSelectionModel().select(tipoApartamento);
                tfValorDiarioPorPessoa.setText(Utilidades.toMoney(tipoApartamento.getPreco()));
            }
            cbNumeroApto.getSelectionModel().select("" + hospedagem.getApartamento());
            cbMotivoViagem.getSelectionModel().select(hospedagem.getMotivo());
            cbStatus.getSelectionModel().select(hospedagem.getStatus());
        }
    }

    @FXML
    private void calcularTotalActionEvent(ActionEvent actionEvent) {
        if (dpEntrada.getValue() != null && dpSaida.getValue() != null && cbTipoApto.getSelectionModel().getSelectedItem() != null) {
            Date inicio = Date.from(dpEntrada.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date fim = Date.from(dpSaida.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            int dias = 0;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(inicio);
            while (calendar.getTime().before(fim)) {
                dias++;
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            tfValorDiarioPorPessoa.setText(Utilidades.toMoney(cbTipoApto.getSelectionModel().getSelectedItem().getPreco()));
            lbValorTotal.setText("Valor Total: " + Utilidades.toMoney(dias * cbTipoApto.getSelectionModel().getSelectedItem().getPreco() * cbQuantidadePessoas.getSelectionModel().getSelectedItem()));
            total = dias * cbTipoApto.getSelectionModel().getSelectedItem().getPreco();
        }
    }

    @FXML
    private void btSairActionEvent(ActionEvent actionEvent) {
        ((Stage) tfCodigo.getScene().getWindow()).close();
    }

    @FXML
    private void btCancelarActionEvent(ActionEvent actionEvent) {
        if (tvHospedagem.getSelectionModel().getSelectedItem() != null) {
            Date entrada = Date.from(dpEntrada.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date ultima = new Date();
            if (entrada.after(ultima)) {
                Hospedagem hospedagem = tvHospedagem.getSelectionModel().getSelectedItem();
                hospedagem.setStatus("Cancelado");
                new HospedagemDAO().editar(hospedagem);
                new Alert(Alert.AlertType.INFORMATION, "Cancelado!").showAndWait();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Não é possível cancelar a hospedagem").showAndWait();
            }
        }
    }

    @FXML
    private void btExcluirActionEvent(ActionEvent actionEvent) {
        if (tvHospedagem.getSelectionModel().getSelectedItem() != null) {
            Optional<ButtonType> button = new Alert(Alert.AlertType.CONFIRMATION, "Deseja excluir a hospedagem", ButtonType.YES, ButtonType.NO).showAndWait();
            if (button.get().equals(ButtonType.YES)) {
                new HospedagemDAO().excluir(tvHospedagem.getSelectionModel().getSelectedItem());
                hospedagems.setAll(new HospedagemDAO().pegarTodos());
            }
        }

    }

    @FXML
    private void btLocalizarActionEvent(ActionEvent actionEvent) {
        hospedagems.setAll(new HospedagemDAO().pesquisarPorNome(tfNome.getText()));
    }

    @FXML
    private void btFiltrarActionEvent(ActionEvent actionEvent) {
        hospedagems.setAll(new HospedagemDAO().filtrar(dpEntrada.getValue() == null ? null : Date.from(dpEntrada.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()), cbStatus.getSelectionModel().getSelectedItem()));
    }

    private void carregarCliente() {
        if (cliente != null) {
            tfCodigo.setText("" + cliente.getId());
            tfNome.setText(cliente.getNome());
            tfSobrenome.setText(cliente.getSobrenome());
            cbGenero.getSelectionModel().select(cliente.getGenero());
            tfCidade.setText(cliente.getCidade());
            tfTelefone.setText(cliente.getTelefone());
            tfEmail.setText(cliente.getEmail());
            tfDocumentoIdentidade.setText(cliente.getIdentidade());
            dpNascimento.setValue(LocalDateTime.ofInstant(cliente.getNascimento().toInstant(), ZoneId.systemDefault()).toLocalDate());
            Image image = new Image(new ByteArrayInputStream(cliente.getFoto()));
            ivFoto.setImage(image);
        } else {
            tfCodigo.clear();
            tfNome.clear();
            tfSobrenome.clear();
            cbGenero.getSelectionModel().clearSelection();
            tfCidade.clear();
            tfTelefone.clear();
            tfEmail.clear();
            tfDocumentoIdentidade.clear();
            dpNascimento.setValue(null);
            ivFoto.setImage(null);
        }
    }
}
