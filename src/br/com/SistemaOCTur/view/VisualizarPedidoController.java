/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.view;

import br.com.SistemaOCTur.dao.PedidoDAO;
import br.com.SistemaOCTur.entity.Pedido;
import br.com.SistemaOCTur.util.Utilidades;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class VisualizarPedidoController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;

    @FXML
    private TableView<Pedido> tvPedido;
    @FXML
    private TableColumn<Pedido, Integer> tcApartamento;

    @FXML
    private TableColumn<Pedido, Date> tcTempoTotal;
    @FXML
    private TableColumn<Pedido, Date> tcData;
    @FXML
    private TableColumn<Pedido, Date> tcHora;
    @FXML
    private TableColumn<Pedido, Date> tcPrevisaoEntrega;
    @FXML
    private TableColumn<Pedido, Pedido> tcStatus;

    private int apartamento;

    private ObservableList<Pedido> pedidos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            if (apPrincipal.getUserData() != null) {
                apartamento = (int) apPrincipal.getUserData();
                pedidos.setAll(new PedidoDAO().pegarTodosPorApartamento(apartamento));
            }
        });
        tcApartamento.setCellValueFactory(new PropertyValueFactory<>("apartamento"));
        tcData.setCellValueFactory(new PropertyValueFactory<>("dataPedido"));
        tcTempoTotal.setCellValueFactory(new PropertyValueFactory<>("tempoTotal"));
        tcPrevisaoEntrega.setCellValueFactory((TableColumn.CellDataFeatures<Pedido, Date> param) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(param.getValue().getDataPedido());
            Calendar tempoTotal = Calendar.getInstance();
            tempoTotal.setTime(param.getValue().getTempoTotal());
            calendar.add(Calendar.HOUR, tempoTotal.get(Calendar.HOUR));
            calendar.add(Calendar.MINUTE, tempoTotal.get(Calendar.MINUTE));
            calendar.add(Calendar.SECOND, tempoTotal.get(Calendar.SECOND));
            return new SimpleObjectProperty<>(calendar.getTime());
        });
        tcStatus.setCellValueFactory((TableColumn.CellDataFeatures<Pedido, Pedido> param) -> new SimpleObjectProperty<>(param.getValue()));
        tcHora.setCellValueFactory(new PropertyValueFactory<>("dataPedido"));
        tcData.setCellFactory((TableColumn<Pedido, Date> param) -> new TableCell<Pedido, Date>() {

            @Override
            protected void updateItem(Date item, boolean empty) {
                if (empty) {
                    setText(null);
                } else {
                    setText(Utilidades.toDate(item));
                }
            }
        });
        tcHora.setCellFactory((TableColumn<Pedido, Date> param) -> new TableCell<Pedido, Date>() {

            @Override
            protected void updateItem(Date item, boolean empty) {
                if (empty) {
                    setText(null);
                } else {
                    setText(Utilidades.toHour(item));
                }
            }
        });
        tcPrevisaoEntrega.setCellFactory((TableColumn<Pedido, Date> param) -> new TableCell<Pedido, Date>() {

            @Override
            protected void updateItem(Date item, boolean empty) {
                if (empty) {
                    setText(null);
                } else {
                    setText(Utilidades.toDate(item) + " " + Utilidades.toHour(item));
                }
            }

        });
        tcStatus.setCellFactory((TableColumn<Pedido, Pedido> param) -> new TableCell<Pedido, Pedido>() {
            @Override
            protected void updateItem(Pedido item, boolean empty) {
                if (empty) {
                    setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
                } else {
                    if (item.isRecebido()) {
                        setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                    } else {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(item.getDataPedido());
                        Calendar tempoTotal = Calendar.getInstance();
                        tempoTotal.setTime(item.getTempoTotal());
                        calendar.add(Calendar.HOUR, tempoTotal.get(Calendar.HOUR));
                        calendar.add(Calendar.MINUTE, tempoTotal.get(Calendar.MINUTE));
                        calendar.add(Calendar.SECOND, tempoTotal.get(Calendar.SECOND));
                        if (calendar.getTime().before(new Date())) {
                            setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                        } else {
                            setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
                        }
                    }
                }
            }
        });
        tvPedido.setItems(pedidos);
    }

    @FXML
    private void btCancelarPedidoActionEvent(ActionEvent actionEvent) {
        if (tvPedido.getSelectionModel().getSelectedItem() != null) {
            Pedido pedido = tvPedido.getSelectionModel().getSelectedItem();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(pedido.getDataPedido());
            Calendar tempoTotal = Calendar.getInstance();
            tempoTotal.setTime(pedido.getTempoTotal());
            calendar.add(Calendar.HOUR, tempoTotal.get(Calendar.HOUR));
            calendar.add(Calendar.MINUTE, tempoTotal.get(Calendar.MINUTE));
            calendar.add(Calendar.SECOND, tempoTotal.get(Calendar.SECOND));
            if (calendar.getTime().after(new Date())) {
                new PedidoDAO().excluir(pedido);
                pedidos.setAll(new PedidoDAO().pegarTodosPorApartamento(apartamento));
            } else {
                new Alert(Alert.AlertType.ERROR, "Não há como cancelar um pedido atrasado!").showAndWait();
            }
        }
    }

    @FXML
    private void btExcluirActionEvent(ActionEvent actionEvent) {
        if (tvPedido.getSelectionModel().getSelectedItem() != null) {
            Pedido pedido = tvPedido.getSelectionModel().getSelectedItem();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(pedido.getDataPedido());
            Calendar tempoTotal = Calendar.getInstance();
            tempoTotal.setTime(pedido.getTempoTotal());
            calendar.add(Calendar.HOUR, tempoTotal.get(Calendar.HOUR));
            calendar.add(Calendar.MINUTE, tempoTotal.get(Calendar.MINUTE));
            calendar.add(Calendar.SECOND, tempoTotal.get(Calendar.SECOND));
            if (calendar.getTime().before(new Date())) {
                new PedidoDAO().excluir(pedido);
                pedidos.setAll(new PedidoDAO().pegarTodosPorApartamento(apartamento));
            } else {
                new Alert(Alert.AlertType.ERROR, "Não há como excluir um pedido em andamento!").showAndWait();
            }
        }
    }

    @FXML
    private void btAcusarRecebimentoActionEvent(ActionEvent actionEvent) {
        if (tvPedido.getSelectionModel().getSelectedItem() != null) {
            Pedido pedido = tvPedido.getSelectionModel().getSelectedItem();
            pedido.setRecebido(true);
            new PedidoDAO().editar(pedido);
            pedidos.setAll(new PedidoDAO().pegarTodosPorApartamento(apartamento));
        }
    }
}
