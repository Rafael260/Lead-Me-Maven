from MySQL import Banco

def limpar_linha(linha):
	return linha.replace("\"","")

def dados_invalidos(dados, indices):
	for i in indices:
		if(dados[i] == None or not dados[i]):
			return True
	return False

def matricula_invalida(dados):
	return not ("APROVADO" in dados[4]) and not ("REPROVADO" in dados[4])



PASTA_DADOS_UFRN = "dadosBTI/matriculas/"
PREFIXO_NOME_ARQUIVO = "matricula-"
matriculasValidas = 0
banco = Banco("127.0.0.1","root","12345678","leadme_3")

periodos = ['2017.1','2016.2','2016.1','2015.2','2015.1','2014.2','2014.1']
for periodo in periodos:
	try:
		for linhaMatricula in open(PASTA_DADOS_UFRN+PREFIXO_NOME_ARQUIVO+periodo+".csv", "r", encoding="utf-8"):
			linhaMatricula = limpar_linha(linhaMatricula)
			dadosMatricula = linhaMatricula.split(";")
			if(len(dadosMatricula) < 5 or matricula_invalida(dadosMatricula)):
				continue

			turma = banco.select("turma","*","id="+dadosMatricula[0])

			if(turma == None or len(turma) == 0):
				print("Turma nula")
				continue

			#inserindo o aluno
			banco.insert("aluno",["id","curso_id"],[dadosMatricula[1],92127264])
			#inserindo a matricula
			banco.insert("matricula",["media","numeroFaltas","situacao","aluno_id","turma_id"],[dadosMatricula[2],dadosMatricula[3],dadosMatricula[4],dadosMatricula[1],dadosMatricula[0]])
	except FileNotFoundError:
		print("Arquivo nao encontrado")