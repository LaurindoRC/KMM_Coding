from random import *

op_jog = ['pedra', 'papel', 'tesoura']

def jokempo(option):
	maquina  = randrange(0,2)
	if option == 0 and maquina == 1:
		print('Voce '+op_jog[op]+'\nMaquina: '+op_jog[maquina]+'\nVoce perdeu!')
	elif option == 0 and maquina == 2:
		print('Voce '+op_jog[op]+'\nMaquina: '+op_jog[maquina]+'\nVoce ganhou!')
	elif option == 1 and maquina == 0:
		print('Voce '+op_jog[op]+'\nMaquina: '+op_jog[maquina]+'\nVoce ganhou!')
	elif option == 1 and maquina == 2:
		print('Voce '+op_jog[op]+'\nMaquina: '+op_jog[maquina]+'\nVoce perdeu!')
	elif option == 2 and maquina == 0:
		print('Voce '+op_jog[op]+'\nMaquina: '+op_jog[maquina]+'\nVoce perdeu!')
	elif option == 2 and maquina == 1:
		print('Voce '+op_jog[op]+'\nMaquina: '+op_jog[maquina]+'\nVoce perdeu!')
	else:
		print('Voce '+op_jog[op]+'\nMaquina: '+op_jog[maquina]+'\nVoce empatou!')

escape = True

while escape == True:
	print('JO-KEN-PO\nSelecionar uma opção:\n0 - Pedra\n1 - Papel\n2 - Tesoura')
	op = int(input('Opção: '))
	jokempo(op)
	escape = input('Gostaria de continuar jogando?\nSe sim digite s\nSe não digite n\nOpção: ')
	if escape == 's':
	  escape = True
	elif escape == 'n':
	  escape = False
