/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.view;

import br.com.SistemaOCTur.dao.EventoDAO;
import br.com.SistemaOCTur.entity.Evento;
import br.com.SistemaOCTur.entity.Salao;
import br.com.SistemaOCTur.util.Sessao;
import br.com.SistemaOCTur.util.Utilidades;
import java.io.File;
import java.net.URL;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
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
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.swing.JRViewer;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class RelatorioEventoController implements Initializable {

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
            Date inicio = Date.from(dpInicio.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date fim = Date.from(dpFim.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Map<String, Object> parametros = new HashMap<>();
            try {
                parametros.put("usuario", Sessao.usuario.toString());
                parametros.put("periodo", " " + Utilidades.toDate(inicio) + " até " + Utilidades.toDate(fim));
                String dados="<html>";
                Salao salao=null;
                for (Evento evento : new EventoDAO().pegarEntreData(inicio,fim)){
                    if(!evento.getSalao().equals(salao)){
                        salao=evento.getSalao();
                        dados+="<h1><b>"+evento.getSalao().getNome()+"</b></h1><br/>"
                                + "<br/>";
                    }
                    Calendar calendar=Calendar.getInstance();
                    calendar.setTime(evento.getInicio());
                    int dias=0;
                    while (calendar.getTime().before(evento.getFim())) {                        
                        dias++;
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                    }
                    
                    dados+="Serviço: "+evento.getTipoServico().getNome()+"<br/>"
                            + "Inicio: "+Utilidades.toDate(evento.getInicio())+"<br/>"
                            + "Fim: "+Utilidades.toDate(evento.getFim())+"<br/>"
                            + "Dias: "+dias+"<br/>"
                            + "<br/><br/>";
                    
                }
                
                parametros.put("dados", dados);
                jasperPrint = JasperFillManager.fillReport(getClass().getResourceAsStream(Utilidades.relatorio + "relatorioEvento.jasper"), parametros, new JREmptyDataSource());
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
            new Alert(Alert.AlertType.ERROR, "É necessário preencher as datas").showAndWait();
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
                    jRPdfExporter.setParameter(JRExporterParameter.OUTPUT_FILE, new File(file.getAbsolutePath() + File.separator + "evento" + new Random().nextInt() + ".pdf"));
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
                    jRXlsExporter.setParameter(JRExporterParameter.OUTPUT_FILE, new File(file.getAbsolutePath() + File.separator + "evento" + new Random().nextInt() + ".xls"));
                    jRXlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    jRXlsExporter.exportReport();
                }
            } catch (JRException ex) {
                Logger.getLogger(RelatorioHospedesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
