package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import service.ProcessadorRequisitos;

public class Aluno extends Pessoa {

    public static final Double MEDIA_APROVACAO = 5.0;
    
    private String numeroMatricula;
    private Curso curso;
    private String matrizCurricular;
    private List<Matricula> matriculas;
    private Integer cargaObrigatoriaCumprida;
    private Integer cargaOptativaCumprida;
    private Double iea;
    private Double mcn;
    
    public Aluno() {
        curso = null;
        matriculas = new ArrayList<>();
        cargaObrigatoriaCumprida = 0;
        cargaOptativaCumprida = 0;
    }

    public String getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setNumeroMatricula(String numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getMatrizCurricular() {
        return matrizCurricular;
    }

    public void setMatrizCurricular(String matrizCurricular) {
        this.matrizCurricular = matrizCurricular;
    }
    
    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }
    
    public void adicionarMatricula(Matricula matricula){
        this.matriculas.add(matricula);
    }

    public Integer getCargaObrigatoriaCumprida() {
        return cargaObrigatoriaCumprida;
    }

    public void setCargaObrigatoriaCumprida(Integer cargaObrigatoriaCumprida) {
        this.cargaObrigatoriaCumprida = cargaObrigatoriaCumprida;
    }

    public Integer getCargaOptativaCumprida() {
        return cargaOptativaCumprida;
    }

    public void setCargaOptativaCumprida(Integer cargaOptativaCumprida) {
        this.cargaOptativaCumprida = cargaOptativaCumprida;
    }
    
    public Integer getCargaTotalCumprida(){
        return this.cargaObrigatoriaCumprida + this.cargaOptativaCumprida;
    }

    public Double getIea() {
        return iea;
    }

    public void setIea(Double iea) {
        this.iea = iea;
    }

    public Double getMcn() {
        return mcn;
    }

    public void setMcn(Double mcn) {
        this.mcn = mcn;
    }
    
    public Double getProgresso(){
        Integer cargaHorariaCumprida = getCargaTotalCumprida();
        Set<String> keys = this.curso.getMatrizesCurricular().keySet();
        MatrizCurricular matriz = null;
        for (String key: keys){
            matriz = this.curso.getMatrizesCurricular().get(key);
        }
        if(matriz != null){    
            Integer cargaHorariaTotal = matriz.getCargaTotal();
            return cargaHorariaCumprida.doubleValue()/cargaHorariaTotal;
        }
        else{
            return 0.0;
        }
    }
    
    public boolean pagouMateria(Disciplina disciplina){
        for(Matricula matricula: this.matriculas){
            if (matricula.getTurma().getDisciplina().equals(disciplina) && matricula.getSituacao().contains("APR")){
                return true;
            }
        }
        return false;
    }
    
    public boolean podePagar(Disciplina disciplina){
        return !pagouMateria(disciplina) && ProcessadorRequisitos.satisfazRequisitos(this, disciplina.getPreRequisitos());
    }
    
    public List<Disciplina> carregarDisciplinasPagas(){
        List<Disciplina> disciplinasPagas = new ArrayList<>();
        for(Matricula matricula: this.matriculas){
            if (matricula.getMedia() >= MEDIA_APROVACAO){
                disciplinasPagas.add(matricula.getTurma().getDisciplina());
            }
        }
        return disciplinasPagas;
    }

}
