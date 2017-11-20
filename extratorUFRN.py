from MySQL import Banco

def limpar_linha(linha):
	return linha.replace("\"","")

def dados_invalidos(dados, indices):
	for i in indices:
		if(dados[i] == None or not dados[i]):
			return True
	return False

def matricula_invalida(dados):
	return not ("APROVADO" in dados[9]) and not ("REPROVADO" in dados[9])



PASTA_DADOS_UFRN = "dadosUFRN/matriculas/"
PREFIXO_NOME_ARQUIVO = "matricula-componente-"
matriculasValidas = 0
banco = Banco("127.0.0.1","root","12345678","leadme")
#print(banco.select("aluno","*",None))

for ano in range(2017,2018): #2011 ao 2017
	for periodo in range(1,3): #1 ao 2
		print(">>>>>>>>ABRINDO O ARQUIVO "+ str(ano) +"."+str(periodo))
		try:
			for linhaMatricula in open(PASTA_DADOS_UFRN+PREFIXO_NOME_ARQUIVO+str(ano)+"."+str(periodo)+".csv", "r", encoding="utf-8"):
				linhaMatricula = limpar_linha(linhaMatricula)
				dadosMatricula = linhaMatricula.split(";")
				if(len(dadosMatricula) < 10 or dados_invalidos(dadosMatricula,[0,1,2,7,8,9]) or matricula_invalida(dadosMatricula)):
					continue

				turma = banco.select("turma","*","id="+dadosMatricula[0])
				curso = banco.select("curso","*","id="+dadosMatricula[2])

				if(turma == None or len(turma) == 0):
					print("Turma nula")
					continue

				if(curso == None or len(curso) == 0):
					print("Curso nulo")
					continue

				print(str(ano)+"."+str(periodo)+ " tentando inserir aluno e matricula")
				#inserindo o aluno
				banco.insert("aluno",["id","curso_id"],[dadosMatricula[1],dadosMatricula[2]])
				#inserindo a matricula
				banco.insert("matricula",["media","numeroFaltas","situacao","aluno_id","turma_id"],[dadosMatricula[7],dadosMatricula[8],dadosMatricula[9],dadosMatricula[1],dadosMatricula[0]])

		except FileNotFoundError:
			print("Arquivo nao encontrado")