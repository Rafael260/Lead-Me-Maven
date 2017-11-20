from MySQL import Banco
from extratorUtil import *

def turma_invalida(dadosTurma):
	return dadosTurma[6] != "GRADUAÇÃO" or dadosTurma[22] != "CONSOLIDADA" or dadosTurma[24] != "Presencial"

PASTA_DADOS_UFRN = "dadosUFRN/turmas/"
PREFIXO_NOME_ARQUIVO = "turmas-"
banco = Banco("127.0.0.1","root","12345678","leadme")

for ano in range(2011,2018): #2011 ao 2017
	for periodo in range(1,3): #1 ao 2
		print(">>>>>>>>ABRINDO O ARQUIVO "+ str(ano) +"."+str(periodo))
		for linhaTurma in open(PASTA_DADOS_UFRN+PREFIXO_NOME_ARQUIVO+str(ano)+"."+str(periodo)+".csv", "r", encoding="utf-8"):
			linhaTurma = limpar_linha(linhaTurma)
			dadosTurma = linhaTurma.split(";")

			if(len(dadosTurma) <= 24 or dadosInvalidos(dadosTurma, [0, 1, 5, 6, 9, 10, 22, 24]) or turma_invalida(dadosTurma)):
				continue

			disciplina = banco.select("disciplina","*","id="+dadosTurma[5])
			if(disciplina == None or len(disciplina) == 0):
				print("Disciplina nula")
				continue

			banco.insert("turma",["id","codigoTurma","periodoLetivo","disciplina_id"],[dadosTurma[0],dadosTurma[1],str(dadosTurma[9])+"."+str(dadosTurma[10]), dadosTurma[5]])

