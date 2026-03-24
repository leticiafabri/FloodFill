import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.JOptionPane;

public class Main {

    static int contador = 0;
    static int frame = 0;

    public static void main(String[] args) throws Exception {

        File pastaFrames = new File("frames");

        if (pastaFrames.exists()) {
            File[] arquivos = pastaFrames.listFiles();
            if (arquivos != null) {
                for (File f : arquivos) {
                    f.delete();
                }
            }
        } else {
            pastaFrames.mkdir();
        }

        BufferedImage img = ImageIO.read(new File("imagens/coracao.png"));

        String[] opcoesEstrutura = {"Pintar por Pilha", "Pintar por Fila"};
        int escolhaEstrutura = JOptionPane.showOptionDialog(
                null, "Escolha o tipo de preenchimento:",
                "Flood Fill", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null,
                opcoesEstrutura, opcoesEstrutura[0]);

        String[] opcoesPosicao = {"Dentro do coração", "Fora do coração"};
        int escolhaPosicao = JOptionPane.showOptionDialog(
                null, "Onde deseja começar?",
                "Posição inicial", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null,
                opcoesPosicao, opcoesPosicao[0]);

        int x, y;

        if (escolhaPosicao == 0) {
            x = 512;
            y = 512;
        } else {
            x = 50;
            y = 50;
        }

        int corOriginal = img.getRGB(x, y);
        int novaCor = new Color(255, 0, 0).getRGB();

        contador = 0;
        frame = 0;

        if (escolhaEstrutura == 0) {
            floodFillPilha(img, x, y, corOriginal, novaCor);
        } else {
            floodFillFila(img, x, y, corOriginal, novaCor);
        }

        ImageIO.write(img, "png", new File("resultado.png"));

        JOptionPane.showMessageDialog(null, "Processo concluído!");
    }

    public static void floodFillPilha(BufferedImage img, int x, int y, int corOriginal, int novaCor) {
        MinhaPilha<Ponto> pilha = new MinhaPilha<>();
        pilha.push(new Ponto(x, y));

        while (!pilha.estaVazia()) {
            Ponto p = pilha.pop();
            int px = p.getX();
            int py = p.getY();

            if (px < 0 || py < 0 || px >= img.getWidth() || py >= img.getHeight()) continue;

            if (img.getRGB(px, py) != corOriginal) continue;

            img.setRGB(px, py, novaCor);

            salvarFrame(img);

            pilha.push(new Ponto(px + 1, py));
            pilha.push(new Ponto(px - 1, py));
            pilha.push(new Ponto(px, py + 1));
            pilha.push(new Ponto(px, py - 1));
        }
    }

    public static void floodFillFila(BufferedImage img, int x, int y, int corOriginal, int novaCor) {
        MinhaFila<Ponto> fila = new MinhaFila<>();
        fila.enqueue(new Ponto(x, y));

        while (!fila.estaVazia()) {
            Ponto p = fila.dequeue();
            int px = p.getX();
            int py = p.getY();

            if (px < 0 || py < 0 || px >= img.getWidth() || py >= img.getHeight()) continue;

            if (img.getRGB(px, py) != corOriginal) continue;

            img.setRGB(px, py, novaCor);

            salvarFrame(img);

            fila.enqueue(new Ponto(px + 1, py));
            fila.enqueue(new Ponto(px - 1, py));
            fila.enqueue(new Ponto(px, py + 1));
            fila.enqueue(new Ponto(px, py - 1));
        }
    }

    public static void salvarFrame(BufferedImage img) {
        contador++;

        if (contador % 100 == 0) {
            try {
                ImageIO.write(img, "png", new File("frames/frame_" + frame + ".png"));
                frame++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}