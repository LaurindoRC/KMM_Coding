package challenge2;

/**
 *
 * @author fabiog
 */
public class ChallengeUtil {

   public static String getRules() {
      return ""
              + "Envie no conteudo do e-mail o que deseja criptografar/descriptografar. O texto precisa estar dentro de um bloco de chaves para facilitar a identificação (problemas com assinatura de e-mail, por exemplo) \n"
              + "Exemplo:\n{\n"
              + "Seu texto deve estar aqui, sem acentos.\n"
              + "}\n\n"
              + "O título do e-mail tem que ser obrigatoriamente SILVIO_SANTOS[O][X], sendo:\n"
              + "   O: operação desejada: C-> Criptografar; D-> Descriptografar\n"
              + "   X determina o valor a ser utilizado no calculo da criptografia/descriptografia (se vier um valor inválido, ira considerar 3)\n"
              + "\n\n"
              + "O texto sera retornado em UPPE CASE! sem choro!!"
              + "";
   }

}
