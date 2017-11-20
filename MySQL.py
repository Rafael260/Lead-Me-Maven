import MySQLdb as db
from SQLManager import SQLManager

class Banco(SQLManager):
	def __init__(self,host,login,password,database):
		self.host = host
		self.login = login
		self.password = password
		self.database = database
		
		self.connected = False
		self.connection = None
		self.cursor = None

	def conectar(self):
		try:
			self.connection = db.connect(self.host,self.login,self.password, self.database)
		except:
			raise Exception("Deu ruim pra conectar no banco")
		else: #deu certo, simbora
			self.connected = True
			self.cursor = self.connection.cursor()

	def select(self, tabela, colunas, where=None):
		if (not self.connected):
			self.conectar()
		super(Banco, self).query_select(tabela,colunas,where,None,None)

		try:
			self.cursor.execute(self.query)
		except:
			raise Exception("Erro ao executar query")
		else:
			return self.cursor.fetchall()

	def insert(self,tabela,colunas,valores):
		if (not self.connected):
			self.conectar()

		super(Banco, self).query_insert(tabela,colunas,valores)

		try:
			self.cursor.execute(self.query)
			print("INSERIU NA TABELA "+ tabela)
		except:
			print("Nao conseguiu inserir na tabela "+ tabela)
		else:
			self.connection.commit()
