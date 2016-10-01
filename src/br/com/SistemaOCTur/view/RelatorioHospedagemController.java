/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.view;

import br.com.SistemaOCTur.dao.HospedagemDAO;
import br.com.SistemaOCTur.entity.Hospedagem;
import br.com.SistemaOCTur.util.Sessao;
import br.com.SistemaOCTur.util.Utilidades;
import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.stage.DirectoryChooser;
import javax.swing.SwingUtilities;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.swing.JRViewer;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class RelatorioHospedagemController implements Initializable {

    @FXML
    private DatePicker dpInicio;
    @FXML
    private DatePicker dpFim;
    @FXML
    private SwingNode swRelatorio;
    private JasperPrint jasperPrint;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void btVisualizarActionEvent(ActionEvent actionEvent) {
        if (dpInicio.getValue() != null && dpFim.getValue() != null) {
            Map<String, Object> parametros = new HashMap<>();
            Date inicio = Date.from(dpInicio.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date fim = Date.from(dpFim.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            try {
                parametros.put("usuario", Sessao.usuario.toString());
                parametros.put("periodo", "de " + Utilidades.toDate(inicio) + " até " + Utilidades.toDate(fim));
                double renda = 0;
                String dados = "<html>";
                List<Hospedagem> hospedagens = new HospedagemDAO().pegarTodosEntre(inicio, fim);
                renda = hospedagens.stream().mapToDouble(Hospedagem::getValor).sum();
                for (Hospedagem hospedagem : hospedagens) {
                    dados += ""
                            + "<b>Cliente </b> " + hospedagem.getCliente() + "<br/>"
                            + "<b>Motivo </b>" + hospedagem.getMotivo() + "<br/>"
                            + "<b>Tipo apartamento </b>" + hospedagem.getTipoApartamento() + "<br/>"
                            + "<b>Valor </b>" + Utilidades.toMoney(hospedagem.getValor()) + "("+new DecimalFormat("0.00").format(hospedagem.getValor()*100/renda)+"% do total)<br/>"
                            + "<b>Entrada </b>" + Utilidades.toDate(hospedagem.getEntrada()) + "<br/>"
                            + "<b>Saida </b>" + Utilidades.toDate(hospedagem.getSaida()) + "<br/>"
                            + "<b>Status </b>" + hospedagem.getStatus() + "<br/><br/>";

                }
                parametros.put("dados", dados);
                parametros.put("renda", Utilidades.toMoney(renda));
                jasperPrint = JasperFillManager.fillReport(getClass().getResourceAsStream(Utilidades.relatorio + "relatorioHospedagem.jasper"), parametros, new JREmptyDataSource());
                JRViewer jRViewer = new JRViewer(jasperPrint);
                swRelatorio.setContent(jRViewer);
                SwingUtilities.invokeLater(() -> {
                    jRViewer.repaint();
                    jRViewer.revalidate();
                });
            } catch (JRException e) {
                System.err.println(e.getMessage());
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Selecione as datas para continuar").showAndWait();
        }
    }

    @FXML
    private void btPDFActionEvent(ActionEvent actionEvent) {
        if (jasperPrint != null) {
            try {
                JRPdfExporter jRPdfExporter = new JRPdfExporter();
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File file = directoryChooser.showDialog(swRelatorio.getScene().getWindow());
                if (file != null) {
                    jRPdfExporter.setParameter(JRExporterParameter.OUTPUT_FILE, new File(file.getAbsolutePath() + File.separator + "hospedagem" + new Random().nextInt() + ".pdf"));
                    jRPdfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    jRPdfExporter.exportReport();
                }
            } catch (JRException ex) {
                Logger.getLogger(RelatorioHospedesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btXLSActionEvent(ActionEvent actionEvent) {
        if (jasperPrint != null) {
            try {
                JRXlsExporter jRXlsExporter = new JRXlsExporter();
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File file = directoryChooser.showDialog(swRelatorio.getScene().getWindow());
                if (file != null) {
                    jRXlsExporter.setParameter(JRExporterParameter.OUTPUT_FILE, new File(file.getAbsolutePath() + File.separator + "hospedagem" + new Random().nextInt() + ".xls"));
                    jRXlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    jRXlsExporter.exportReport();
                }
            } catch (JRException ex) {
                Logger.getLogger(RelatorioHospedesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
