/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexao;

import dto.DiscenteDTO;
import dto.MatriculaComponenteDTO;
import dto.SituacaoMatriculaDTO;
import dto.UsuarioDTO;
import excecoes.JsonStringInvalidaException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author rafao
 */
public class JsonToObject {
    public static UsuarioDTO toUsuario(String text) throws JsonStringInvalidaException {
        UsuarioDTO usuario = new UsuarioDTO();
        if(!text.equalsIgnoreCase("")){
            try {
                JSONObject jsonList = new JSONObject(text);
                usuario.setAtivo(jsonList.getBoolean("ativo"));
                usuario.setChaveFoto(jsonList.getString("chave-foto"));
                usuario.setCpfCnpj(jsonList.getLong("cpf-cnpj"));
                usuario.setEmail(jsonList.getString("email"));
                usuario.setIdFoto(jsonList.getLong("id-foto"));
                usuario.setIdUnidade(jsonList.getInt("id-unidade"));
                usuario.setIdUsuario(jsonList.getLong("id-usuario"));
                usuario.setLogin(jsonList.getString("login"));
                usuario.setNomePessoa(jsonList.getString("nome-pessoa"));
            } catch (JSONException e) {
                System.out.println(e.getMessage());
                throw new JsonStringInvalidaException(e.getMessage());
            }
        }else{
            throw new JsonStringInvalidaException("String vazia!");
        }
        return usuario;
    }
    
    public static List<DiscenteDTO> toDiscentes(String text) throws JsonStringInvalidaException{
        List<DiscenteDTO> discentes = new ArrayList<>();
        JSONArray array = new JSONArray(text);
        for(int i = 0; i < array.length(); i++){
            JSONObject object = array.getJSONObject(i);
            discentes.add(toDiscente(object));
        }
        return discentes;
    }
    
    public static DiscenteDTO toDiscente(JSONObject discenteJson) throws JsonStringInvalidaException {
        DiscenteDTO discenteDTO = new DiscenteDTO();
        discenteDTO.setIdDiscente(discenteJson.getInt("id-discente"));
        discenteDTO.setNomeCurso(discenteJson.getString("nome-curso"));
        discenteDTO.setPeriodoIngresso(discenteJson.getInt("periodo-ingresso"));
        discenteDTO.setIdCurso(discenteJson.getInt("id-curso"));
        System.out.println(discenteDTO.getNomeCurso());
        return discenteDTO;
    }
    
    public static List<MatriculaComponenteDTO> paraMatriculas(String json) throws JsonStringInvalidaException{
        List<MatriculaComponenteDTO> matriculas = new ArrayList<>();
        JSONArray array = new JSONArray(json);
        for(int i = 0; i < array.length(); i++){
            JSONObject object = array.getJSONObject(i);
            matriculas.add(paraMatricula(object));
        }
        return matriculas;
    }
    
    public static MatriculaComponenteDTO paraMatricula(JSONObject object) throws JsonStringInvalidaException{
        MatriculaComponenteDTO matriculaDTO = new MatriculaComponenteDTO();
        matriculaDTO.setAno(object.getInt("ano"));
        matriculaDTO.setConceito(object.getBoolean("conceito"));
        matriculaDTO.setFaltas(object.getInt("faltas"));
        matriculaDTO.setIdComponente(object.getInt("id-componente"));
        matriculaDTO.setIdDiscente(object.getInt("id-discente"));
        matriculaDTO.setIdSituacaoMatricula(object.getInt("id-situacao-matricula"));
        matriculaDTO.setIdTipoIntegralizacao(object.getString("id-tipo-integralizacao"));
        matriculaDTO.setIdTurma(object.getInt("id-turma"));
//        matriculaDTO.setMediaFinal(matriculaDTO.getConceito() ? object.getString("media-final"): object.getFloat("media-final"));
        matriculaDTO.setPeriodo(object.getInt("periodo"));
//        matriculaDTO.setRecuperacao(object.getDouble("recuperacao"));
        return matriculaDTO;
    }
    
    public static List<SituacaoMatriculaDTO> paraSituacoes(String json){
         List<SituacaoMatriculaDTO> situacoes = new ArrayList<>();
        JSONArray array = new JSONArray(json);
        for(int i = 0; i < array.length(); i++){
            JSONObject object = array.getJSONObject(i);
            situacoes.add(paraSituacao(object));
        }
        return situacoes;
    }
    
    public static SituacaoMatriculaDTO paraSituacao(JSONObject obj){
        SituacaoMatriculaDTO situacao = new SituacaoMatriculaDTO();
        situacao.setIdSituacaoMatricula(obj.getInt("id-situacao-matricula"));
        situacao.setDescricao(obj.getString("descricao"));
        return situacao;
    }

}
