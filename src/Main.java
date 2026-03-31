import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.JOptionPane;

public class Main {

    static int contador = 0;
    static int frame = 0;

    public static void main(String[] args) throws Exception {

        limparPastaFrames();

        BufferedImage img = ImageIO.read(new File("imagens/coracao.png"));

        int escolhaEstrutura = escolherEstrutura();
        int[] posicao = escolherPosicao();

        int x = posicao[0];
        int y = posicao[1];

        int corOriginal = img.getRGB(x, y);
        int novaCor = new Color(255, 0, 0).getRGB();

        contador = 0;
        frame = 0;

        Estrutura<Ponto> estrutura;
        if (escolhaEstrutura == 0) {
            estrutura = new PilhaAdapter<>();
        } else {
            estrutura = new FilaAdapter<>();
        }

        floodFill(img, x, y, corOriginal, novaCor, estrutura);

        ImageIO.write(img, "png", new File("resultado.png"));
        JOptionPane.showMessageDialog(null, "Processo concluído!");
    }

    public static void floodFill(
            BufferedImage img,
            int x,
            int y,
            int corOriginal,
            int novaCor,
            Estrutura<Ponto> estrutura) {

        estrutura.adicionar(new Ponto(x, y));

        while (!estrutura.estaVazia()) {

            Ponto p = estrutura.remover();

            int px = p.getX();
            int py = p.getY();

            if (px < 0 || py < 0 || px >= img.getWidth() || py >= img.getHeight()) continue;
            if (img.getRGB(px, py) != corOriginal) continue;

            img.setRGB(px, py, novaCor);
            salvarFrame(img);

            estrutura.adicionar(new Ponto(px + 1, py));
            estrutura.adicionar(new Ponto(px - 1, py));
            estrutura.adicionar(new Ponto(px, py + 1));
            estrutura.adicionar(new Ponto(px, py - 1));
        }
    }


    public static void limparPastaFrames() {
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
    }

    public static int escolherEstrutura() {
        String[] opcoes = {"Pintar por Pilha", "Pintar por Fila"};

        return JOptionPane.showOptionDialog(
                null,
                "Escolha o tipo de preenchimento:",
                "Flood Fill",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );
    }

    public static int[] escolherPosicao() {
        String[] opcoes = {"Dentro do coração", "Fora do coração"};

        int escolha = JOptionPane.showOptionDialog(
                null,
                "Onde deseja começar?",
                "Posição inicial",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        if (escolha == 0) {
            return new int[]{512, 512};
        } else {
            return new int[]{50, 50};
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