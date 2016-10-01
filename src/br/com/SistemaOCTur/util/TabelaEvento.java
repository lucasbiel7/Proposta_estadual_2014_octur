/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.util;

import br.com.SistemaOCTur.dao.EventoDAO;
import br.com.SistemaOCTur.dao.SalaoDAO;
import br.com.SistemaOCTur.entity.Evento;
import br.com.SistemaOCTur.entity.Salao;
import br.com.SistemaOCTur.view.EventosControleDeSalaoController;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Callback;

/**
 *
 * @author OCTI01
 */
public class TabelaEvento extends TableView<EventoMes> {

    private int mes;
    private int ano;
    private TableColumn<EventoMes, Salao> salao;
    private List<TableColumn<EventoMes, Evento>> colunas;

    private ObservableList<EventoMes> eventoMeses = FXCollections.observableArrayList();

    public TabelaEvento(int mes, int ano) {
        this.mes = mes;
        this.ano = ano;
        AnchorPane.setBottomAnchor(TabelaEvento.this, 0d);
        AnchorPane.setTopAnchor(TabelaEvento.this, 0d);
        AnchorPane.setLeftAnchor(TabelaEvento.this, 0d);
        AnchorPane.setRightAnchor(TabelaEvento.this, 0d);
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        setVisible(true);
        salao = new TableColumn<>("Salão");
        salao.setCellValueFactory(new PropertyValueFactory<>("salao"));
        getColumns().add(salao);
        setItems(eventoMeses);
        getSelectionModel().cellSelectionEnabledProperty().set(true);
        carregarDados();
    }

    public void carregarDados() {
        getColumns().clear();
        eventoMeses.clear();
        getColumns().add(salao);
        Date inicio = Utilidades.toDate("01/" + mes + "/" + ano);
        Date fim = Utilidades.toDate("01/" + (mes + 1) + "/" + ano);
        for (Salao salao : new SalaoDAO().pegarTodos()) {
            EventoMes eventoMes = new EventoMes();
            eventoMes.setSalao(salao);
            eventoMes.setEventos(new ArrayList<>());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(inicio);
            while (calendar.getTime().before(fim)) {
                if (salao.getId() == 1) {
                    TableColumn<EventoMes, Evento> tcDia = new TableColumn<>(Utilidades.toDay(calendar.getTime()));
                    final int i = calendar.get(Calendar.DAY_OF_MONTH);
                    tcDia.setCellValueFactory((TableColumn.CellDataFeatures<EventoMes, Evento> param) -> new SimpleObjectProperty<>(param.getValue().getEventos().get(i - 1)));
                    tcDia.setCellFactory((TableColumn<EventoMes, Evento> param) -> new TableCell<EventoMes, Evento>() {
                        @Override
                        protected void updateItem(Evento item, boolean empty) {
                            if (item != null) {
                                switch (item.getTipoServico().getId()) {
                                    case 1:
                                        setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                                        break;
                                    case 2:
                                        setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
                                        break;
                                    case 3:
                                        setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                                        break;
                                    case 4:
                                        setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                                        break;
                                }
                                final Evento evento=item;
                                setOnMouseReleased((MouseEvent event) -> {
                                    EventosControleDeSalaoController.evento = evento;
                                });
                            } else {
                                setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                            }
                        }
                    });
                    getColumns().add(tcDia);
                }
                eventoMes.getEventos().add(new EventoDAO().pegarPorSalaoDia(salao, calendar.getTime()));
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            eventoMeses.add(eventoMes);
        }
    }

    public class ColunaValor implements Callback<TableColumn.CellDataFeatures<EventoMes, Evento>, ObservableValue<Evento>> {

        private final int numeroColuna;

        public ColunaValor(int coluna) {
            this.numeroColuna = coluna;
        }

        @Override
        public ObservableValue<Evento> call(TableColumn.CellDataFeatures<EventoMes, Evento> param) {
            System.out.println(numeroColuna);
            return new SimpleObjectProperty<>(param.getValue().getEventos().get(numeroColuna));
        }

    }
}
