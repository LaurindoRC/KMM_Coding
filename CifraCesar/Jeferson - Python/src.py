cod = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','X','W','Y','Z',' ']

chave = 3

def criptografa():

    entrada = input('Digite uma palavra: ').upper()

    cript_cod = list(range(len(entrada)))
    cript_cod_txt = list(range(len(entrada)))

    # PEGA O INDICE DA LETRA DA ENTRADA E CODIFICA PRA REGRA DA CIFRA DE CESAR
    cont = -1
    for i in entrada:
        for j in cod:
            if i == j:
                cont = cont + 1
                if i == 'W':
                    cript_cod[cont] = 0
                elif i == 'Y':
                    cript_cod[cont] = 1
                elif i == 'Z':
                    cript_cod[cont] = 2
                elif i == ' ':
                  cript_cod[cont] = 26
                else:
                    cript_cod[cont] = cod.index(j) + chave

    # CRIPTROGRAFA A ENTRADA
    cont_txt = -1
    for x in cript_cod:
        cont_txt = cont_txt + 1
        cript_cod_txt[cont_txt] = cod[x]
    print('Entrada criptografada: ' + "".join(cript_cod_txt))

def descriptografa():
    # ENTRADA CODIGO CRIPTOGRAFADO
    entrada_descript = input('Digite o código criptografado: ')

    descript = list(range(len(entrada_descript)))
    descript_txt = list(range(len(entrada_descript)))

    # PEGA O INDICE DAS ENTRADAS
    cont = -1
    for d in entrada_descript:
        for dv in cod:
            if d == dv:
                cont = cont + 1
                if d == 'W':
                    descript[cont] = 23
                elif d == 'Y':
                    descript[cont] = 24
                elif d == 'Z':
                    descript[cont] = 25
                elif d == ' ':
                  descript[cont] = 26
                else:
                    descript[cont] = cod.index(d) - chave

    # DESCRIPTOGRAFA ENTRADA
    cont_txt = -1
    for dtx in descript:
        cont_txt = cont_txt + 1
        descript_txt[cont_txt] = cod[dtx]
    print('Entrada descriptografada: ' + "".join(descript_txt))

criptografa()
descriptografa()