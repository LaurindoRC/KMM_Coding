package joken.game;

import java.applet.AudioClip;
import joken.core.DataManager;
import joken.core.FontManager;
import joken.core.Game;
import joken.core.ImageManager;
import joken.core.InputManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import joken.core.AudioManager;

public class JogoJoKenPo extends Game {

   static final int EST_MENU = 0;
   static final int EST_PREPARACAO = 1;
   static final int EST_ESCOLHENDO = 2;
   static final int EST_JOGANDO = 3;
   static final int EST_VENCEU = 4;
   static final int EST_PERDEU = 5;
   static final int EST_EMPATE = 6;

   static final int PEDRA = 0;
   static final int PAPEL = 1;
   static final int TESOURA = 2;

   int estado;

   Point posInimigo;
   Point posJogador;
   Point posPedraJogador;
   Point posPapelJogador;
   Point posTesouraJogador;
   Point posPedraInimigo;
   Point posPapelInimigo;
   Point posTesouraInimigo;

   int vitorias;
   int derrotas;
   int empates;

   int escolhaJogador;
   int escolhaInimigo;

   BufferedImage imgJogador;
   BufferedImage imgInimigo;
   BufferedImage imgPedra;
   BufferedImage imgPapel;
   BufferedImage imgTesoura;
   BufferedImage imgCenario;

   AudioClip sndFundo;

   Font fntScore;
   Font fntSubTitle;

   Random rnd;

   public JogoJoKenPo() {
      rnd = new Random();

      posJogador = new Point(1000, 325);
      posInimigo = new Point(0, 355);
      posPedraJogador = new Point(700, 250);
      posPapelJogador = new Point(800, 200);
      posTesouraJogador = new Point(900, 250);
      posPedraInimigo = new Point(10, 250);
      posPapelInimigo = new Point(110, 200);
      posTesouraInimigo = new Point(210, 250);

      estado = EST_PREPARACAO;
      vitorias = 0;
      derrotas = 0;
      empates = 0;
   }

   @Override
   public void onLoad() {
      try {
         imgCenario = ImageManager.getInstance().loadImage("jokenpoImagens/cenario.png");

         imgInimigo = ImageManager.getInstance().loadImage("jokenpoImagens/inimigo.png");
         imgJogador = ImageManager.getInstance().loadImage("jokenpoImagens/jogador.png");

         imgPedra = ImageManager.getInstance().loadImage("jokenpoImagens/pedra.png");
         imgPapel = ImageManager.getInstance().loadImage("jokenpoImagens/papel.png");
         imgTesoura = ImageManager.getInstance().loadImage("jokenpoImagens/tesoura.png");

         sndFundo = AudioManager.getInstance().loadAudio("jokenpoSons/fundo.wav");

         fntScore = FontManager.getInstance().loadFont(
                 "jokenpoImagens/CrimewaveBB.ttf", 44, FontManager.BOLD);
         fntSubTitle = FontManager.getInstance().loadFont(
                 "jokenpoImagens/CrimewaveBB.ttf", 24, FontManager.PLAIN);

         vitorias = 0;
         derrotas = 0;
         empates = 0;

         sndFundo.stop();
         runReinicio();
         sndFundo.loop();
      } catch (IOException ioe) {
         throw new RuntimeException(ioe);
      }
   }

   @Override
   public void onUnload() {
      sndFundo.stop();
   }

   @Override
   public void onUpdate(int currentTick) {
      runControleDoJogo();

      switch (estado) {

         case EST_MENU:
            break;
         case EST_PREPARACAO:
            runEstadoPreparacao();
            break;
         case EST_ESCOLHENDO:
            runEstadoEscolhendo();
            break;
         case EST_JOGANDO:
            runEstadoJogando();
            break;
         case EST_VENCEU:
            runEstadoVenceu();
            break;
         case EST_PERDEU:
            runEstadoPerdeu();
            break;
         case EST_EMPATE:
            runEstadoEmpate();
            break;
      }
   }

   protected void runReinicio() {
      estado = EST_PREPARACAO;
   }

   protected void runEstadoPreparacao() {
      estado = EST_ESCOLHENDO;
   }

   protected void runEstadoEscolhendo() {
      if (InputManager.getInstance().isMouseJustPressed(MouseEvent.BUTTON1)) {
         if ((InputManager.getInstance().getMouseX() >= posPedraJogador.x && InputManager.getInstance().getMouseX() <= (posPedraJogador.x + 50))
                 && (InputManager.getInstance().getMouseY() >= posPedraJogador.y && InputManager.getInstance().getMouseY() <= (posPedraJogador.y + 50))) {
            escolhaJogador = PEDRA;
            estado = EST_JOGANDO;
         } else if ((InputManager.getInstance().getMouseX() >= posPapelJogador.x && InputManager.getInstance().getMouseX() <= (posPapelJogador.x + 50))
                 && (InputManager.getInstance().getMouseY() >= posPapelJogador.y && InputManager.getInstance().getMouseY() <= (posPapelJogador.y + 50))) {
            escolhaJogador = PAPEL;
            estado = EST_JOGANDO;
         } else if ((InputManager.getInstance().getMouseX() >= posTesouraJogador.x && InputManager.getInstance().getMouseX() <= (posTesouraJogador.x + 50))
                 && (InputManager.getInstance().getMouseY() >= posTesouraJogador.y && InputManager.getInstance().getMouseY() <= (posTesouraJogador.y + 50))) {
            escolhaJogador = TESOURA;
            estado = EST_JOGANDO;
         }
      }
   }

   protected void runEstadoJogando() {
      estado = EST_JOGANDO;

      escolhaInimigo = rnd.nextInt(3);

      if (escolhaJogador == escolhaInimigo) {
         empates++;
         estado = EST_EMPATE;
      } else {
         switch (escolhaJogador) {
            case PAPEL:
               if (escolhaInimigo == PEDRA) {
                  vitorias++;
                  estado = EST_VENCEU;
               } else {
                  derrotas++;
                  estado = EST_PERDEU;
               }
               break;
            case PEDRA:
               if (escolhaInimigo == TESOURA) {
                  vitorias++;
                  estado = EST_VENCEU;
               } else {
                  derrotas++;
                  estado = EST_PERDEU;
               }
               break;
            case TESOURA:
               if (escolhaInimigo == PAPEL) {
                  vitorias++;
                  estado = EST_VENCEU;
               } else {
                  derrotas++;
                  estado = EST_PERDEU;
               }
               break;
         }
      }
   }

   protected void runEstadoVenceu() {
      if (InputManager.getInstance().isPressed(KeyEvent.VK_SPACE)) {
         runReinicio();
      }
   }

   protected void runEstadoPerdeu() {
      if (InputManager.getInstance().isPressed(KeyEvent.VK_SPACE)) {
         runReinicio();
      }
   }

   protected void runEstadoEmpate() {
      if (InputManager.getInstance().isPressed(KeyEvent.VK_SPACE)) {
         runReinicio();
      }
   }

   protected void runControleDoJogo() {
      if (InputManager.getInstance().isPressed(KeyEvent.VK_ESCAPE)) {
         terminate();
      }
//      if (InputManager.getInstance().isPressed(KeyEvent.VK_S)) {
//         try {
//            DataManager dm = new DataManager("JoKenPo.save");
//            dm.write("estado", estado);
//            dm.write("escolhaJogador", escolhaJogador);
//            dm.write("escolhaInimigo", escolhaInimigo);
//            dm.write("vitorias", vitorias);
//            dm.write("derrotas", derrotas);
//            dm.write("empates", empates);
//            dm.write("posInimigo.x", posInimigo.x);
//            dm.write("posInimigo.y", posInimigo.y);
//            dm.write("posJogador.x", posJogador.x);
//            dm.write("posJogador.y", posJogador.y);
//            dm.write("posPedraJogador.x", posPedraJogador.x);
//            dm.write("posPedraJogador.y", posPedraJogador.y);
//            dm.write("posPapelJogador.x", posPapelJogador.x);
//            dm.write("posPapelJogador.y", posPapelJogador.y);
//            dm.write("posPedraJogador.x", posTesouraJogador.x);
//            dm.write("posPedraJogador.y", posTesouraJogador.y);
//            dm.write("posPedraInimigo.x", posPedraInimigo.x);
//            dm.write("posPedraInimigo.y", posPedraInimigo.y);
//            dm.write("posPapelInimigo.x", posPapelInimigo.x);
//            dm.write("posPapelInimigo.y", posPapelInimigo.y);
//            dm.write("posTesouraInimigo.x", posTesouraInimigo.x);
//            dm.write("posTesouraInimigo.y", posTesouraInimigo.y);
//            dm.save();
//         } catch (IOException ex) {
//            System.out.println(ex.getMessage());
//         }
//      }
//      if (InputManager.getInstance().isPressed(KeyEvent.VK_C)) {
//         try {
//            DataManager dm = new DataManager("QuedaLivre.save");
//            estado = dm.read("estado", estado);
//            escolhaJogador = dm.read("escolhaJogador", escolhaJogador);
//            escolhaInimigo = dm.read("escolhaInimigo", escolhaInimigo);
//            vitorias = dm.read("vitorias", vitorias);
//            derrotas = dm.read("derrotas", derrotas);
//            empates = dm.read("empates", empates);
//            posInimigo.x = dm.read("posInimigo.x", posInimigo.x);
//            posInimigo.y = dm.read("posInimigo.y", posInimigo.y);
//            posJogador.x = dm.read("posJogador.x", posJogador.x);
//            posJogador.y = dm.read("posJogador.y", posJogador.y);
//            posPedraJogador.x = dm.read("posPedraJogador.x", posPedraJogador.x);
//            posPedraJogador.y = dm.read("posPedraJogador.y", posPedraJogador.y);
//            posPapelJogador.x = dm.read("posPapelJogador.x", posPapelJogador.x);
//            posPapelJogador.y = dm.read("posPapelJogador.y", posPapelJogador.y);
//            posTesouraJogador.x = dm.read("posTesouraJogador.x", posTesouraJogador.x);
//            posTesouraJogador.y = dm.read("posTesouraJogador.y", posTesouraJogador.y);
//            posPedraInimigo.x = dm.read("posPedraInimigo.x", posPedraInimigo.x);
//            posPedraInimigo.y = dm.read("posPedraInimigo.y", posPedraInimigo.y);
//            posPapelInimigo.x = dm.read("posPapelInimigo.x", posPapelInimigo.x);
//            posPapelInimigo.y = dm.read("posPapelInimigo.y", posPapelInimigo.y);
//            posTesouraInimigo.x = dm.read("posTesouraInimigo.x", posTesouraInimigo.x);
//            posTesouraInimigo.y = dm.read("posTesouraInimigo.y", posTesouraInimigo.y);
//         } catch (IOException ex) {
//            System.out.println(ex.getMessage());
//         }
//      }
   }

   @Override
   public void onRender(Graphics2D g) {
      renderJogo(g);
      renderHUD(g);
      switch (estado) {
         case EST_ESCOLHENDO:
            renderEscolhendo(g);
            break;
         case EST_JOGANDO:
            renderJogando(g);
            break;
         case EST_VENCEU:
            renderMensagemVenceu(g);
            break;
         case EST_PERDEU:
            renderMensagemPerdeu(g);
            break;
         case EST_EMPATE:
            renderMensagemEmpate(g);
            break;
      }
   }

   protected void renderJogo(Graphics2D g) {
      g.drawImage(imgCenario, 0, 0, null);
      g.drawImage(imgJogador, posJogador.x, posJogador.y, -300, 300, null);
      g.drawImage(imgInimigo, posInimigo.x, posInimigo.y, 200, 250, null);

   }

   protected void renderHUD(Graphics2D g) {
      g.setFont(fntScore);
      g.setColor(Color.BLUE);
      g.drawString("VITORIAS: " + vitorias, getWidth() - 925, 40);
      g.setColor(Color.YELLOW);
      g.drawString("EMPATES: " + empates, getWidth() - 625, 40);
      g.setColor(Color.RED);
      g.drawString("DERROTAS: " + derrotas, getWidth() - 325, 40);
   }

   protected void renderEscolhendo(Graphics2D g) {
      g.setFont(fntSubTitle);
      g.setColor(Color.blue);
      g.drawString("Escolha", posPapelJogador.x - 10, posPapelJogador.y - 40);
      g.drawString("Pedra", posPedraJogador.x, posPedraJogador.y - 10);
      g.drawImage(imgPedra, posPedraJogador.x, posPedraJogador.y, 50, 50, null);
      g.drawString("Papel", posPapelJogador.x, posPapelJogador.y - 10);
      g.drawImage(imgPapel, posPapelJogador.x, posPapelJogador.y, 50, 50, null);
      g.drawString("Tesoura", posTesouraJogador.x, posTesouraJogador.y - 10);
      g.drawImage(imgTesoura, posTesouraJogador.x, posTesouraJogador.y, 50, 50, null);

      g.setColor(Color.red);
      g.drawString("Pedra", posPedraInimigo.x, posPedraInimigo.y - 10);
      g.drawImage(imgPedra, posPedraInimigo.x, posPedraInimigo.y, 50, 50, null);
      g.drawString("Papel", posPapelInimigo.x, posPapelInimigo.y - 10);
      g.drawImage(imgPapel, posPapelInimigo.x, posPapelInimigo.y, 50, 50, null);
      g.drawString("Tesoura", posTesouraInimigo.x, posTesouraInimigo.y - 10);
      g.drawImage(imgTesoura, posTesouraInimigo.x, posTesouraInimigo.y, 50, 50, null);
   }

   protected void renderJogando(Graphics2D g) {
      g.setFont(fntSubTitle);
      g.setColor(Color.blue);
      switch (escolhaJogador) {
         case PEDRA:
            g.drawString("Pedra", posPedraJogador.x, posPedraJogador.y - 10);
            g.drawImage(imgPedra, posPedraJogador.x, posPedraJogador.y, 50, 50, null);
            break;
         case PAPEL:
            g.drawString("Papel", posPapelJogador.x, posPapelJogador.y - 10);
            g.drawImage(imgPapel, posPapelJogador.x, posPapelJogador.y, 50, 50, null);
            break;
         case TESOURA:
            g.drawString("Tesoura", posTesouraJogador.x, posTesouraJogador.y - 10);
            g.drawImage(imgTesoura, posTesouraJogador.x, posTesouraJogador.y, 50, 50, null);
            break;
      }

      g.setColor(Color.red);
      switch (escolhaInimigo) {
         case PEDRA:
            g.drawString("Pedra", posPedraInimigo.x, posPedraInimigo.y - 10);
            g.drawImage(imgPedra, posPedraInimigo.x, posPedraInimigo.y, 50, 50, null);
            break;
         case PAPEL:
            g.drawString("Papel", posPapelInimigo.x, posPapelInimigo.y - 10);
            g.drawImage(imgPapel, posPapelInimigo.x, posPapelInimigo.y, 50, 50, null);
            break;
         case TESOURA:
            g.drawString("Tesoura", posTesouraInimigo.x, posTesouraInimigo.y - 10);
            g.drawImage(imgTesoura, posTesouraInimigo.x, posTesouraInimigo.y, 50, 50, null);
            break;
      }
   }

   protected void renderMensagemVenceu(Graphics2D g) {
      g.setColor(Color.blue);
      g.setFont(fntScore);
      g.drawString("Voce venceu!", 250, 200);
      g.setColor(Color.white);
      g.setFont(fntSubTitle);
      g.drawString("PRESSIONE [ESPACO] PARA JOGAR NOVAMENTE", 250, 300);
      g.drawString("PRESSIONE [ESC] PARA SAIR", 250, 350);
      renderJogando(g);
   }

   protected void renderMensagemPerdeu(Graphics2D g) {
      g.setColor(Color.red);
      g.setFont(fntScore);
      g.drawString("Voce perdeu!", 250, 200);
      g.setColor(Color.white);
      g.setFont(fntSubTitle);
      g.drawString("PRESSIONE [ESPACO] PARA JOGAR NOVAMENTE", 250, 300);
      g.drawString("PRESSIONE [ESC] PARA SAIR", 250, 350);
      renderJogando(g);
   }

   protected void renderMensagemEmpate(Graphics2D g) {
      g.setColor(Color.yellow);
      g.setFont(fntScore);
      g.drawString("Empatou!", 250, 200);
      g.setColor(Color.white);
      g.setFont(fntSubTitle);
      g.drawString("PRESSIONE [ESPACO] PARA JOGAR NOVAMENTE", 250, 300);
      g.drawString("PRESSIONE [ESC] PARA SAIR", 250, 350);
      renderJogando(g);
   }
}
