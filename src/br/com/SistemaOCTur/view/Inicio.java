/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.view;

import br.com.SistemaOCTur.dao.CargoDAO;
import br.com.SistemaOCTur.dao.ClienteDAO;
import br.com.SistemaOCTur.dao.SalaoDAO;
import br.com.SistemaOCTur.dao.TipoServicoDAO;
import br.com.SistemaOCTur.dao.UsuarioDAO;
import br.com.SistemaOCTur.entity.Cliente;
import br.com.SistemaOCTur.entity.Salao;
import br.com.SistemaOCTur.entity.TipoServico;
import br.com.SistemaOCTur.entity.Usuario;
import br.com.SistemaOCTur.util.Sessao;
import br.com.SistemaOCTur.util.Utilidades;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author OCTI01
 */
public class Inicio extends Application {

    @Override
    public void start(Stage primaryStage) {
        //Modo developer
        Sessao.usuario=new UsuarioDAO().login("admin");
        Utilidades.abrirJanela("Principal", Utilidades.NOME_PROGRAMA, true, true);
        
//Instalador
//        Utilidades.abrirJanela("EscolherSistema", "Inicio", true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void inserirFotos() {
        //Carregar Fotos usuarios
//        for (Usuario usuario : new UsuarioDAO().pegarTodos()) {
//            try {
//                usuario.setFoto(Files.readAllBytes(Paths.get("usuario/" + usuario.getId() + ".jpg")));
//            } catch (IOException ex) {
//                try {
//                    usuario.setFoto(Files.readAllBytes(Paths.get("usuario/" + usuario.getId() + ".JPG")));
//                } catch (IOException ex1) {
//                    try {
//                        usuario.setFoto(Files.readAllBytes(Paths.get("usuario/" + usuario.getId() + ".jpeg")));
//                    } catch (IOException ex2) {
//                        try {
//                            usuario.setFoto(Files.readAllBytes(Paths.get("usuario/0.png")));
//                        } catch (IOException ex3) {
//                            System.out.println(ex3.getMessage());
//                        }
//                    }
//                }
//            }
//            new UsuarioDAO().editar(usuario);
//        }
        //Carregar Fotos clientes
          for (Cliente cliente : new ClienteDAO().pegarTodos()) {
            try {
                cliente.setFoto(Files.readAllBytes(Paths.get("cliente/" + cliente.getId() + ".jpg")));
            } catch (IOException ex) {
                try {
                    cliente.setFoto(Files.readAllBytes(Paths.get("cliente/" + cliente.getId() + ".JPG")));
                } catch (IOException ex1) {
                    try {
                        cliente.setFoto(Files.readAllBytes(Paths.get("cliente/" + cliente.getId() + ".jpeg")));
                    } catch (IOException ex2) {
                        try {
                            cliente.setFoto(Files.readAllBytes(Paths.get("cliente/0.png")));
                        } catch (IOException ex3) {
                            System.out.println(ex3.getMessage());
                        }
                    }
                }
            }
            new ClienteDAO().editar(cliente);
        }
        
    }

    public void cadastrarAdministrador() {
        Usuario usuario = new Usuario();
        usuario.setNome("admin");
        usuario.setSenha("123456");
        usuario.setCargo(new CargoDAO().pegarPorId(17));
        try {
            usuario.setFoto(Files.readAllBytes(Paths.get("usuario/0.png")));
        } catch (IOException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        new UsuarioDAO().cadastrar(usuario);
    }

    private void cadastrarEventos() {
        for (int i = 1; i < 5; i++) {
            new SalaoDAO().cadastrar(new Salao("Salão "+i));
        }
        new TipoServicoDAO().cadastrar(new TipoServico("Eventos"));
        new TipoServicoDAO().cadastrar(new TipoServico("Limpeza"));
        new TipoServicoDAO().cadastrar(new TipoServico("Manutenção"));
        new TipoServicoDAO().cadastrar(new TipoServico("Indisponível"));
    }
}
