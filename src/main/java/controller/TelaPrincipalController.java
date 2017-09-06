package controller;

import excecoes.DataException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Aluno;
import modelo.Disciplina;
import modelo.MatrizCurricular;
import modelo.MatrizDisciplina;
import service.ServiceFacade;
import service.ServiceFacadeFactory;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.AutoCompletionBinding.AutoCompletionEvent;
import org.controlsfx.control.textfield.TextFields;

public class TelaPrincipalController extends Application implements Initializable {

    @FXML
    private Label lbProgresso;

    @FXML
    private TextField txtBuscarDisciplina;

    @FXML
    private ProgressBar progressBarCurso;

    @FXML
    private Label txtIEA;

    @FXML
    private TextField txtDisciplina;

    @FXML
    private PieChart chartAprovacoes;

    @FXML
    private Label txtMCN;

    @FXML
    private Label txtBemVindo;

    @FXML
    private Label txtTituloPieChart;

    @FXML
    private TableView<MatrizDisciplina> tableDisciplinasDisponiveis;

    private ServiceFacade service;

    private String[] disciplinas;

    //Quando escolher a disciplina para verificar a taxa de aprovacao
    private Disciplina disciplinaSelecionada;

    private AutoCompletionBinding<String> autoCompleteSimulacao, autoCompleteEstatistica;

    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaPrincipal.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = ServiceFacadeFactory.getInstance().getServiceInstance();
        disciplinas = service.getDisciplinasDoCursoToString();

        final ObservableList<String> items = FXCollections.observableArrayList();

        /*estatistica*/
        autoCompleteEstatistica = TextFields.bindAutoCompletion(txtDisciplina, disciplinas);
        autoCompleteEstatistica.setOnAutoCompleted(new EventHandler<AutoCompletionEvent<String>>() {

            @Override
            public void handle(AutoCompletionEvent<String> event) {
                disciplinaSelecionada = service.getDisciplina(event.getCompletion());
                txtTituloPieChart.setText("Aprovações de " + disciplinaSelecionada.getNome());
                Double aprovacoes = 100.0;
                try {
                    aprovacoes = service.getMediaAprovacao(disciplinaSelecionada);
                } catch (DataException ex) {
                    //Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);;
                }
                ObservableList<PieChart.Data> dadosPieChart = FXCollections.observableArrayList(
                        new PieChart.Data("Aprovados: " + String.format("%.2f", aprovacoes) + "%", aprovacoes),
                        new PieChart.Data("Reprovados: " + String.format("%.2f", 100.0 - aprovacoes) + "%", 100.0 - aprovacoes));
                chartAprovacoes.setData(dadosPieChart);
                txtDisciplina.clear();
                event.consume();
            }
        });
        /*simulação*/
        autoCompleteSimulacao = TextFields.bindAutoCompletion(txtBuscarDisciplina, disciplinas);
        autoCompleteSimulacao.setOnAutoCompleted(new EventHandler<AutoCompletionEvent<String>>() {

            @Override
            public void handle(AutoCompletionEvent<String> event) {
                items.add(event.getCompletion());
//                listDisciplinas.setItems(items);
                txtBuscarDisciplina.clear();
                event.consume();
            }
        });

        service = ServiceFacadeFactory.getInstance().getServiceInstance();
        Aluno alunoLogado = service.getAlunoLogado();

        //Tela inicial
        txtBemVindo.setText("Bem vindo(a), " + alunoLogado.getNome());
        Double progresso = alunoLogado.getProgresso();
        progressBarCurso.setProgress(progresso);
        lbProgresso.setText("Progresso no curso: " + String.format("%.2f", progresso * 100) + "%");
        txtIEA.setText(alunoLogado.getIea().toString());
        txtMCN.setText(alunoLogado.getMcn().toString());

        //Tela de estatísticas
        disciplinas = service.getDisciplinasDoCursoToString();
        disciplinaSelecionada = service.getDisciplinaByCodigo("IMD0040");
        txtDisciplina.setText(disciplinaSelecionada.toString());
        txtTituloPieChart.setText("Aprovações de " + disciplinaSelecionada.getNome());
        try {
            Double aprovacoes = service.getMediaAprovacao(disciplinaSelecionada);
            ObservableList<PieChart.Data> dadosPieChart = FXCollections.observableArrayList(
                    new PieChart.Data("Aprovados: " + String.format("%.2f", aprovacoes) + "%", aprovacoes),
                    new PieChart.Data("Reprovados: " + String.format("%.2f", 100.0 - aprovacoes) + "%", 100.0 - aprovacoes));
            chartAprovacoes.setData(dadosPieChart);
        } catch (DataException ex) {
            Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    //Tela de sugestoes/simulacoes
    List<MatrizDisciplina> disciplinasDisponiveis = service.getDisciplinasDisponiveis();
    ObservableList<MatrizDisciplina> listaObs = FXCollections.observableArrayList();
   //listaObs.add(new MatrizDisciplina(new MatrizCurricular("teste"), new Disciplina("123","disciplina testes",90)));
    if(!listaObs.isEmpty()){ 
        for(MatrizDisciplina temp : disciplinasDisponiveis){
            listaObs.add(temp);
        }
    }
    tableDisciplinasDisponiveis.setItems(listaObs);   

    }

}
