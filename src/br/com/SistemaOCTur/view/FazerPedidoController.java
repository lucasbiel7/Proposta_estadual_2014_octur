/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.view;

import br.com.SistemaOCTur.dao.PedidoDAO;
import br.com.SistemaOCTur.dao.PedidoProdutoDAO;
import br.com.SistemaOCTur.entity.Pedido;
import br.com.SistemaOCTur.entity.PedidoProduto;
import br.com.SistemaOCTur.dao.ProdutoDAO;
import br.com.SistemaOCTur.dao.TipoProdutoDAO;
import br.com.SistemaOCTur.entity.Produto;
import br.com.SistemaOCTur.entity.TipoProduto;
import br.com.SistemaOCTur.util.Utilidades;
import java.net.URL;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class FazerPedidoController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ComboBox<TipoProduto> cbTipoProduto;
    @FXML
    private Spinner<Integer> spQuantidade;

    @FXML
    private Label lbValorTotal;
    @FXML
    private Label lbTempoTotal;
    //Täbelas produtos
    @FXML
    private TableView<Produto> tvProdutos;

    @FXML
    private TableColumn<Produto, String> tcNome;
    @FXML
    private TableColumn<Produto, Double> tcValor;
    @FXML
    private TableColumn<Produto, Date> tcTempo;

    //Tabelas Carrinho
    @FXML
    private TableView<PedidoProduto> tvPedidoProduto;
    @FXML
    private TableColumn<PedidoProduto, String> tcPedidoNome;
    @FXML
    private TableColumn<PedidoProduto, Double> tcPedidoValor;
    @FXML
    private TableColumn<PedidoProduto, Date> tcPedidoTempo;
    @FXML
    private TableColumn<PedidoProduto, Integer> tcPedidoQuantidade;
    @FXML
    private TableColumn<PedidoProduto, Double> tcPedidoTotal;
    //Listas
    private ObservableList<TipoProduto> tipoProdutos = FXCollections.observableArrayList();
    private ObservableList<Produto> produtos = FXCollections.observableArrayList();
    private ObservableList<PedidoProduto> pedidoProdutos = FXCollections.observableArrayList();

    private Pedido pedido;

    private int apartamento;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            if (apPrincipal.getUserData() != null) {
                apartamento = (int) apPrincipal.getUserData();
            }
        });
        tipoProdutos.setAll(new TipoProdutoDAO().pegarTodos());
        cbTipoProduto.setItems(tipoProdutos);
        spQuantidade.setValueFactory(new SpinnerValueFactory<Integer>() {

            @Override
            public void decrement(int steps) {
                if (getValue() - steps > 0) {
                    setValue(getValue() - steps);
                }
            }

            @Override
            public void increment(int steps) {
                setValue(getValue() + steps);
            }
        });
        spQuantidade.getValueFactory().setValue(0);
        //TabelaProdtuos
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcTempo.setCellValueFactory(new PropertyValueFactory<>("tempo"));
        tcValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tcTempo.setCellFactory((TableColumn<Produto, Date> param) -> new TableCell<Produto, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                if (!empty) {
                    setText(Utilidades.toHour(item));
                } else {
                    setText(null);
                }
            }
        });
        tcValor.setCellFactory((TableColumn<Produto, Double> param) -> new TableCell<Produto, Double>() {

            @Override
            protected void updateItem(Double item, boolean empty) {
                if (!empty) {
                    setText(Utilidades.toMoney(item));
                } else {
                    setText(null);
                }
            }
        });
        //Tabela Pedidos
        tcPedidoNome.setCellValueFactory((TableColumn.CellDataFeatures<PedidoProduto, String> param) -> new SimpleStringProperty(param.getValue().getId().getProduto().toString()));
        tcPedidoQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tcPedidoTempo.setCellValueFactory(new PropertyValueFactory<>("tempo"));
        tcPedidoTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        tcPedidoValor.setCellValueFactory((TableColumn.CellDataFeatures<PedidoProduto, Double> param) -> new SimpleObjectProperty<>(param.getValue().getId().getProduto().getValor()));
        tcPedidoTotal.setCellFactory((TableColumn<PedidoProduto, Double> param) -> new TableCell<PedidoProduto, Double>() {

            @Override
            protected void updateItem(Double item, boolean empty) {
                if (empty) {
                    setText(null);
                } else {
                    setText(Utilidades.toMoney(item));
                }
            }

        });
        tcPedidoTempo.setCellFactory((TableColumn<PedidoProduto, Date> param) -> new TableCell<PedidoProduto, Date>() {

            @Override
            protected void updateItem(Date item, boolean empty) {
                if (empty) {
                    setText(null);
                } else {
                    setText(Utilidades.toHour(item));
                }
            }

        });
        tcPedidoValor.setCellFactory((TableColumn<PedidoProduto, Double> param) -> new TableCell<PedidoProduto, Double>() {

            @Override
            protected void updateItem(Double item, boolean empty) {
                if (empty) {
                    setText(null);
                } else {
                    setText(Utilidades.toMoney(item));
                }
            }
        });
        pedido = new Pedido();

        tvProdutos.setItems(produtos);
        tvPedidoProduto.setItems(pedidoProdutos);

    }

    @FXML
    private void cbTipoProdutoActionEvent(ActionEvent actionEvent) {
        produtos.clear();
        System.out.println("a");
        if (cbTipoProduto.getSelectionModel().getSelectedItem() != null) {
            produtos.setAll(new ProdutoDAO().pegarPorCategoriaProduto(cbTipoProduto.getSelectionModel().getSelectedItem()));
        }
    }

    @FXML
    private void btAdicionarActionEvent(ActionEvent actionEvent) {
        if (tvProdutos.getSelectionModel().getSelectedItem() != null) {
            PedidoProduto pedidoProduto = new PedidoProduto();
            pedidoProduto.setId(new PedidoProduto.PedidoProdutoID(pedido, tvProdutos.getSelectionModel().getSelectedItem()));
            if (pedidoProdutos.contains(pedidoProduto)) {
                pedidoProduto = pedidoProdutos.get(pedidoProdutos.indexOf(pedidoProduto));
                pedidoProduto.setQuantidade(pedidoProduto.getQuantidade() + spQuantidade.getValueFactory().getValue());
                pedidoProduto.calcularTempo();
                pedidoProduto.calcularTotal();
            } else {
                pedidoProduto.setQuantidade(spQuantidade.getValueFactory().getValue());
                pedidoProduto.calcularTempo();
                pedidoProduto.calcularTotal();
                pedidoProdutos.add(pedidoProduto);
            }
        }
        calcularTempoValorToal();
    }

    @FXML
    private void btFinalizarActionEvent(ActionEvent actionEvent) {
        pedido.setApartamento(apartamento);
        System.out.println(apartamento);
        calcularTempoValorToal();
        pedido.setDataPedido(new Date());
        new PedidoDAO().cadastrar(pedido);
        for (PedidoProduto pedidoProduto : pedidoProdutos) {
            new PedidoProdutoDAO().cadastrar(pedidoProduto);
        }
        ((Stage) tvProdutos.getScene().getWindow()).close();
    }

    @FXML
    private void miRemoverActionEvent(ActionEvent actionEvent) {
        if (tvPedidoProduto.getSelectionModel().getSelectedItem() != null) {
            pedidoProdutos.remove(tvPedidoProduto.getSelectionModel().getSelectedItem());
        }
        calcularTempoValorToal();
    }

    @FXML
    private void btCancelarActionEvent(ActionEvent actionEvent) {
        ((Stage) tvProdutos.getScene().getWindow()).close();
    }

    private void calcularTempoValorToal() {
        Calendar tempoTotal = Calendar.getInstance();
        tempoTotal.setTime(Time.valueOf("00:00:00"));
        double total = 0;
        for (PedidoProduto pedidoProduto : pedidoProdutos) {
            Calendar tempoPedido = Calendar.getInstance();
            tempoPedido.setTime(pedidoProduto.getTempo());
            tempoTotal.add(Calendar.HOUR, tempoPedido.get(Calendar.HOUR));
            tempoTotal.add(Calendar.MINUTE, tempoPedido.get(Calendar.MINUTE));
            tempoTotal.add(Calendar.SECOND, tempoPedido.get(Calendar.SECOND));
            total += pedidoProduto.getTotal();
        }
        pedido.setTempoTotal(tempoTotal.getTime());
        pedido.setTotal(total);
        lbTempoTotal.setText("Tempo total: " + Utilidades.toHour(pedido.getTempoTotal()));
        lbValorTotal.setText("Valor total: " + Utilidades.toMoney(pedido.getTotal()));

    }
}
