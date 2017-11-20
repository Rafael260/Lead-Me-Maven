def limpar_linha(linha):
	return linha.replace("\"","")

def dados_invalidos(dados, indices):
	for i in indices:
		if(dados[i] == None or not dados[i]):
			return True
	return False