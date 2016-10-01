package br.com.SistemaOCTur.view;

import br.com.SistemaOCTur.dao.HospedagemDAO;
import br.com.SistemaOCTur.entity.Hospedagem;
import br.com.SistemaOCTur.util.Utilidades;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;

public class GerencialGraficosController implements Initializable {

    @FXML
    private DatePicker dpData;
    @FXML
    private BarChart<String, Integer> bcOcupacaoDiaria;
    @FXML
    private StackedBarChart<String, Integer> sbcPrevisaoChegada;
    @FXML
    private StackedBarChart<String, Integer> sbcPrevisaoSaida;
    @FXML
    private LineChart<String, Double> lnGraficoOcupacaoMensal;

    private ObservableList<XYChart.Series<String, Integer>> olOcupacaoDiaria = FXCollections.observableArrayList();
    private ObservableList<XYChart.Series<String, Integer>> olPrevisaoChegada = FXCollections.observableArrayList();
    private ObservableList<XYChart.Series<String, Integer>> olPrevisaoSaida = FXCollections.observableArrayList();
    private ObservableList<XYChart.Series<String, Double>> olOcupacaoMensal = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dpData.setValue(LocalDate.now());
        lnGraficoOcupacaoMensal.setData(olOcupacaoMensal);
        bcOcupacaoDiaria.setData(olOcupacaoDiaria);
        sbcPrevisaoChegada.setData(olPrevisaoChegada);
        sbcPrevisaoSaida.setData(olPrevisaoSaida);
        dpDataActionEvent(null);
    }

    @FXML
    private void dpDataActionEvent(ActionEvent actionEvent) {

        carregarOcupacaoPrevisaoChegada();
        carregarOcupacaoPrevisaoSaida();
        carregarGraficoOcupacaoMensal();

        carregarOcupacaoDiaria();
    }

    private void carregarOcupacaoDiaria() {
        if (dpData.getValue() != null) {
            Date inicio = Date.from(dpData.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            ObservableList<XYChart.Data<String, Integer>> dados = FXCollections.observableArrayList();
            int porcento = new HospedagemDAO().pegarEntreODia(inicio).size();
            dados.setAll(new XYChart.Data<>("Ocupacao", porcento));
            XYChart.Series<String, Integer> pessoas = new XYChart.Series<>("ocupacao", dados);
            olOcupacaoDiaria.setAll(pessoas);
            String style = "-fx-bar-fill: ";
            String color = "";
            if (porcento < 40) {
                color = "green;";
            } else {
                if (porcento < 80) {
                    color = "yellow;";
                } else {
                    color = "red;";
                }
            }
            for (Node lookupAll : bcOcupacaoDiaria.lookupAll(".default-color0.chart-bar")) {
                lookupAll.setStyle(style + color);
            }

        }

    }

    private void carregarOcupacaoPrevisaoChegada() {
        if (dpData.getValue() != null) {
            ObservableList<XYChart.Data<String, Integer>> olCheckIn = FXCollections.observableArrayList();
            ObservableList<XYChart.Data<String, Integer>> olReservado = FXCollections.observableArrayList();
            int checkInValue = new HospedagemDAO().pegarCheckIn(Date.from(dpData.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())).size();
            int reservadoValue = new HospedagemDAO().pegarEntreODia(Date.from(dpData.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())).size();
            olCheckIn.setAll(new XYChart.Data<>("Pessoas", checkInValue));
            olReservado.setAll(new XYChart.Data<>("Pessoas", reservadoValue - checkInValue));
            XYChart.Series<String, Integer> checkIn = new XYChart.Series<>("Check-in", olCheckIn);
            XYChart.Series<String, Integer> reservado = new XYChart.Series<>("Reservado", olReservado);
            olPrevisaoChegada.setAll(checkIn, reservado);
        }
    }

    private void carregarOcupacaoPrevisaoSaida() {
        if (dpData.getValue() != null) {
            ObservableList<XYChart.Data<String, Integer>> olCheckOut = FXCollections.observableArrayList();
            ObservableList<XYChart.Data<String, Integer>> olHospedado = FXCollections.observableArrayList();
            int checkOutValue = new HospedagemDAO().pegarCheckOut(Date.from(dpData.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())).size();
            int hospedadoValue = new HospedagemDAO().pegarEntreODia(Date.from(dpData.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())).size();
            olCheckOut.setAll(new XYChart.Data<>("Pessoas", checkOutValue));
            olHospedado.setAll(new XYChart.Data<>("Pessoas", hospedadoValue - checkOutValue));
            XYChart.Series<String, Integer> checkOut = new XYChart.Series<>("Check-out", olCheckOut);
            XYChart.Series<String, Integer> hospedado = new XYChart.Series<>("Hospedado", olHospedado);
            olPrevisaoSaida.setAll(checkOut, hospedado);
        }
    }

    private void carregarGraficoOcupacaoMensal() {
        if (dpData.getValue() != null) {
            ObservableList<XYChart.Data<String, Double>> olDados = FXCollections.observableArrayList();
            Calendar manipularData = Calendar.getInstance();
            manipularData.setTime(Date.from(dpData.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            Date inicio = Utilidades.toDate("01/" + (manipularData.get(Calendar.MONTH) + 1) + "/" + manipularData.get(Calendar.YEAR));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(inicio);
            calendar.add(Calendar.MONTH, -5);
            inicio = calendar.getTime();
            calendar.add(Calendar.MONTH, 11);
            Date fim = calendar.getTime();
            calendar.setTime(inicio);
            while (calendar.getTime().before(fim)) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(calendar.getTime());
                calendar1.add(Calendar.MONTH, 1);
                double percent = 0;
                int dias=0;
                while (calendar.getTime().before(calendar1.getTime())) {
                    percent += new HospedagemDAO().pegarEntreODia(calendar.getTime()).size();
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    dias++;
                }
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                olDados.add(new XYChart.Data<>(Utilidades.toMonth(calendar.getTime()), percent/dias));
            }
            XYChart.Series<String, Double> series = new XYChart.Series<>("Ocupação", olDados);
            olOcupacaoMensal.setAll(series);
        }
    }
}
