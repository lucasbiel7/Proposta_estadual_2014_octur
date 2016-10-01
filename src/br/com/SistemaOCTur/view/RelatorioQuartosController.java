/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.view;

import br.com.SistemaOCTur.dao.HospedagemDAO;
import br.com.SistemaOCTur.dao.TipoApartamentoDAO;
import br.com.SistemaOCTur.entity.TipoApartamento;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class RelatorioQuartosController implements Initializable {

    @FXML
    private DatePicker dpInicio;
    @FXML
    private DatePicker dpFim;
    @FXML
    private BarChart<String, Integer> bcTipoQuartos;

    private ObservableList<XYChart.Series<String, Integer>> series = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        bcTipoQuartos.setData(series);
    }

    @FXML
    private void colocargraficoActionEvent(ActionEvent actionEvent) {
        if (dpInicio.getValue() != null && dpFim.getValue() != null) {
            Date inicio = Date.from(dpInicio.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date fim = Date.from(dpFim.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            series.clear();
            for (TipoApartamento tipoApartamento : new TipoApartamentoDAO().pegarTodos()) {
                ObservableList<XYChart.Data<String, Integer>> dados = FXCollections.observableArrayList();
                dados.setAll(new XYChart.Data<>("hospedes", new HospedagemDAO().pegarPorTipoApartamento(tipoApartamento, inicio, fim).size()));
                XYChart.Series<String, Integer> serie = new XYChart.Series<>(tipoApartamento.getNome(), dados);
                series.add(serie);
            }
        }
    }
}
