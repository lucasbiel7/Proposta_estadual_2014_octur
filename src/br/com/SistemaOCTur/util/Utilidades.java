/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.util;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author OCTI01
 */
public class Utilidades {

    public static final String diretorio = "/br/com/SistemaOCTur/view/";
    public static final String NOME_PROGRAMA = "Sistema OC-TUR Hotelaria";
    public static final String relatorio = "/br/com/SistemaOCTur/view/report/";

    public static void abrirJanela(String fxml) {
        try {
            Parent parent = FXMLLoader.load(Utilidades.class.getResource(diretorio + fxml + ".fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Falha em abrir Janela");
            System.out.println(ex.getMessage());
        }
    }

    public static void abrirJanela(String fxml, Object data) {
        try {
            Parent parent = FXMLLoader.load(Utilidades.class.getResource(diretorio + fxml + ".fxml"));
            parent.setUserData(data);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Falha em abrir Janela");
            System.out.println(ex.getMessage());
        }
    }

    public static Stage abrirJanela(String fxml, String title, boolean exit) {
        try {
            Parent parent = FXMLLoader.load(Utilidades.class.getResource(diretorio + fxml + ".fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setOnCloseRequest((WindowEvent event) -> {
                if (exit) {
                    Platform.exit();
                    System.exit(0);
                }
            });
            stage.setScene(scene);
            stage.show();
            return stage;
        } catch (IOException ex) {
            System.out.println("Falha em abrir Janela");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public static Stage abrirJanela(String fxml, String title, boolean exit, boolean max) {
        try {
            Parent parent = FXMLLoader.load(Utilidades.class.getResource(diretorio + fxml + ".fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setMaximized(max);
            stage.setResizable(false);
            stage.setTitle(title);
//            stage.initStyle(StageStyle.UNDECORATED);
            stage.setOnCloseRequest((WindowEvent event) -> {
                if (exit) {
                    Platform.exit();
                    System.exit(0);
                }
            });
            stage.setScene(scene);
            stage.show();
            return stage;
        } catch (IOException ex) {
            System.out.println("Falha em abrir Janela");
            System.out.println(ex.getCause());
            return null;
        }

    }

    public static String toDate(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    public static Date toDate(String date) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException ex) {
            return null;
        }
    }

    public static String toHour(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("HH:mm:ss").format(date);
    }

    public static void inserirPanel(AnchorPane pai, Parent filho) {
        pai.getChildren().clear();
        AnchorPane.setBottomAnchor(filho, 0d);
        AnchorPane.setLeftAnchor(filho, 0d);
        AnchorPane.setRightAnchor(filho, 0d);
        AnchorPane.setTopAnchor(filho, 0d);
        ((AnchorPane) filho).setPrefSize(pai.getWidth(), pai.getHeight());
        pai.getChildren().add(filho);

    }

    public static Parent carregarFXML(String string) {
        try {
            return FXMLLoader.load(Utilidades.class.getResource(diretorio + string + ".fxml"));
        } catch (IOException ex) {
            return null;
        }
    }

    public static String toMoney(Double preco) {
        return "R$ " + new DecimalFormat("0.00").format(preco);
    }

    public static String toMonth(Date inicio) {
        if (inicio == null) {
            return "";
        }
        return new SimpleDateFormat("MMMM").format(inicio);

    }

    static String toDay(Date time) {
        return new SimpleDateFormat("dd").format(time);
    }
}
