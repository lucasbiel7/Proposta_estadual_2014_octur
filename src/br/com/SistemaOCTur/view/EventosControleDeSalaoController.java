/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.view;

import br.com.SistemaOCTur.dao.EventoDAO;
import br.com.SistemaOCTur.dao.SalaoDAO;
import br.com.SistemaOCTur.dao.TipoServicoDAO;
import br.com.SistemaOCTur.entity.Evento;
import br.com.SistemaOCTur.entity.Salao;
import br.com.SistemaOCTur.entity.TipoServico;
import br.com.SistemaOCTur.util.TabelaEvento;
import br.com.SistemaOCTur.util.Utilidades;
import java.net.URL;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class EventosControleDeSalaoController implements Initializable {

    @FXML
    private ComboBox<String> cbMes;
    @FXML
    private ComboBox<TipoServico> cbTipoServico;
    @FXML
    private Spinner<Integer> spAno;
    @FXML
    private ComboBox<Salao> cbSalao;
    @FXML
    private AnchorPane apTabela;
    @FXML
    private DatePicker dpInicio;
    @FXML
    private DatePicker dpFim;
    private TabelaEvento tvEventos;

    private ObservableList<String> meses = FXCollections.observableArrayList();
    private ObservableList<TipoServico> tipoServicos = FXCollections.observableArrayList();
    private ObservableList<Salao> salaos = FXCollections.observableArrayList();

    public static Evento evento;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spAno.setValueFactory(new SpinnerValueFactory<Integer>() {

            @Override
            public void decrement(int steps) {
                setValue(getValue() - steps);
            }

            @Override
            public void increment(int steps) {
                setValue(getValue() + steps);
            }
        });
        spAno.getValueFactory().setValue(Calendar.getInstance().get(Calendar.YEAR));
        carregarMes();
        //Carregar Listas
        salaos.setAll(new SalaoDAO().pegarTodos());
        tipoServicos.setAll(new TipoServicoDAO().pegarTodos());
        //Carregar componentes
        cbMes.setItems(meses);
        cbSalao.setItems(salaos);
        cbTipoServico.setItems(tipoServicos);
        cbMes.getSelectionModel().select(Utilidades.toMonth(new Date()));
        spAno.setOnMouseReleased((e) -> atualizarTabelaActionEvent(e));
        carregarEvento();
    }

    @FXML
    private void btExcluirActionEvent(ActionEvent actionEvent) {
        if (evento != null) {
            if (new Alert(Alert.AlertType.CONFIRMATION, "Deseja excluir o evento: \n"
                    + "Salão"+evento.getSalao()+"\n"
                    + "Tipo serviço: "+evento.getTipoServico()+"\n"
                    + "Inicio: "+Utilidades.toDate(evento.getInicio())+"\n"
                    + "Fim: "+Utilidades.toDate(evento.getFim()), ButtonType.YES, ButtonType.NO).showAndWait().get().equals(ButtonType.YES)) {
                new EventoDAO().excluir(evento);
                evento=null;
                tvEventos.carregarDados();
            }
        }
    }

    @FXML
    private void btRegistrarActionEvent(ActionEvent actionEvent) {
        Evento evento = new Evento();
        evento.setInicio(Date.from(dpInicio.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        evento.setFim(Date.from(dpFim.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        evento.setSalao(cbSalao.getSelectionModel().getSelectedItem());
        evento.setTipoServico(cbTipoServico.getSelectionModel().getSelectedItem());
        if (evento.getSalao() != null && evento.getTipoServico() != null && evento.getInicio() != null && evento.getFim() != null) {
            if (new EventoDAO().validar(evento)) {
                new EventoDAO().cadastrar(evento);
                tvEventos.carregarDados();
                new Alert(Alert.AlertType.INFORMATION, "Evento cadastrado com sucesso").showAndWait();
            } else {
                new Alert(Alert.AlertType.ERROR, "Não é permitido cadastrar um evento dentro do periodo de outro evento").showAndWait();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Todos os dados do evento são obrigatorios!").showAndWait();
        }
    }

    @FXML
    private void atualizarTabelaActionEvent(ActionEvent actionEvent) {
        carregarEvento();
    }

    private void atualizarTabelaActionEvent(MouseEvent e) {
        carregarEvento();
    }

    private void carregarMes() {
        Date inicio = Utilidades.toDate("01/01/2000");
        Date fim = Utilidades.toDate("01/01/2001");
        meses.clear();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inicio);
        while (calendar.getTime().before(fim)) {
            meses.add(Utilidades.toMonth(calendar.getTime()));
            calendar.add(Calendar.MONTH, 1);
        }
    }

    private void carregarEvento() {
        apTabela.getChildren().clear();
        tvEventos = new TabelaEvento(cbMes.getSelectionModel().getSelectedIndex() + 1, spAno.getValue());
        apTabela.getChildren().add(tvEventos);
    }

}
