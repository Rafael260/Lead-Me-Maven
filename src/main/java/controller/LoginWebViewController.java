/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import conexao.ConsumidorAPI;
import static conexao.ConsumidorAPI.URL_AUTENTICACAO_SINFO;
import static conexao.ConsumidorAPI.URL_ENCAMINHA;
import dto.DiscenteDTO;
import dto.UsuarioDTO;
import excecoes.DataException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import modelo.Aluno;
import modelo.Curso;
import org.json.JSONException;
import service.AlunoService;
import service.CursoService;
import service.LoginService;
import service.ServiceFacade;
import service.ServiceFacadeImpl;

/**
 *
 * @author rafao
 */
public class LoginWebViewController {

    @FXML
    private WebView webViewLogin;
    
    private ServiceFacade service;
    private ControllerUtil util = new ControllerUtil();
    private ConsumidorAPI consumidor;
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        service = new ServiceFacadeImpl();
        consumidor = ConsumidorAPI.getInstance();
         WebEngine engine = webViewLogin.getEngine();
        engine.load(URL_AUTENTICACAO_SINFO);
        
        engine.setOnStatusChanged(new EventHandler<WebEvent<String>>() {
            public void handle(WebEvent<String> event) {
                if (event.getSource() instanceof WebEngine) {
                    WebEngine we = (WebEngine) event.getSource();
                    String location = we.getLocation();
                     if (location.startsWith(URL_ENCAMINHA) && location.contains("code")) {
                        try {
                            
                            consumidor.carregarAccessToken(location);
                            UsuarioDTO usuarioLogado = consumidor.coletarUsuarioLogado();
                            System.out.println("Nome usuario logado: " + usuarioLogado.getNomePessoa());
                            System.out.println("CPF usuario logado: " + usuarioLogado.getCpfCnpj());
                            List<DiscenteDTO> vinculos = consumidor.coletarVinculos(usuarioLogado.getCpfCnpj().toString());
                            DiscenteDTO ultimoVinculo = vinculos.get(vinculos.size()-1);
                            Integer id = ultimoVinculo.getIdDiscente();
                            Integer idCurso = ultimoVinculo.getIdCurso();
                            LoginService loginService = LoginService.getInstance();
                            CursoService cursoService = new CursoService();
                            AlunoService alunoService = new AlunoService();
                            Aluno aluno = new Aluno();
                            aluno.setId(id.toString());
                            Curso curso = cursoService.carregarCurso(idCurso);
                            aluno.setCurso(curso);
                            //Carregar as matriculas do aluno
                            alunoService.carregarMatriculasDoAluno(aluno);
                            System.out.println("Carregou as matriculas do aluno");
                            loginService.setAluno(aluno);
                            
                            util.carregarTela("/fxml/TelaPrincipal.fxml", "Lead Me");
                            ((Stage) webViewLogin.getScene().getWindow()).close();
                        } catch (JSONException ex) {
                            Logger.getLogger(LoginWebViewController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(LoginWebViewController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (DataException ex) {
                            Logger.getLogger(LoginWebViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
    }
}
