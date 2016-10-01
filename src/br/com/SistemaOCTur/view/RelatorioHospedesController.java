/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.view;

import br.com.SistemaOCTur.dao.ClienteDAO;
import br.com.SistemaOCTur.entity.Cliente;
import br.com.SistemaOCTur.util.Sessao;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
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
public class RelatorioHospedesController implements Initializable {

    @FXML
    private TextField tfNome;
    @FXML
    private SwingNode swRelatorio;
    private JasperPrint jasperPrint;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void btVisualizarActionEvent(ActionEvent actionEvent) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("pesquisa", "\""+tfNome.getText()+"\" em qualquer parte");
            parametros.put("usuario", Sessao.usuario.toString());
            DefaultTableModel dtmTabela = new DefaultTableModel(new Object[][]{}, new Object[]{"nome", "email", "telefone", "genero", "foto"});
            List<Cliente> clientes = new ClienteDAO().pesquisarPorNome(tfNome.getText());
            for (Cliente cliente : clientes) {
                dtmTabela.addRow(new Object[]{cliente.getNome() + " " + cliente.getSobrenome(), cliente.getEmail(), cliente.getTelefone(), cliente.getGenero(), new ImageIcon(cliente.getFoto()).getImage()});
            }
            jasperPrint = JasperFillManager.fillReport(getClass().getResourceAsStream("/br/com/SistemaOCTur/view/report/relatorioHospedes.jasper"), parametros, new JRTableModelDataSource(dtmTabela));
            JRViewer jRViewer = new JRViewer(jasperPrint);
            swRelatorio.setContent(jRViewer);
            SwingUtilities.invokeLater(() -> {
                jRViewer.repaint();
                jRViewer.revalidate();
            });
        } catch (JRException e) {
            System.err.println(e.getMessage());
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
                    System.out.println(file.getAbsolutePath() + File.separator + "pesquisa" + tfNome.getText() + ".pdf");
                    jRPdfExporter.setParameter(JRExporterParameter.OUTPUT_FILE, new File(file.getAbsolutePath() + File.separator + "pesquisa" + tfNome.getText() + ".pdf"));
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
                    jRXlsExporter.setParameter(JRExporterParameter.OUTPUT_FILE, new File(file.getAbsolutePath() + File.separator + "pesquisa" + tfNome.getText() + ".xls"));
                    jRXlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    jRXlsExporter.exportReport();
                }
            } catch (JRException ex) {
                Logger.getLogger(RelatorioHospedesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
