import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {

        BufferedImage img = ImageIO.read(new File("imagens/coracao.png"));

        int x = 512;
        int y = 512;

        int corOriginal = img.getRGB(x, y);
        int novaCor = new Color(255, 0, 0).getRGB();

        MinhaPilha<Ponto> pilha = new MinhaPilha<>();
        pilha.push(new Ponto(x, y));

        while (!pilha.estaVazia()) {
            Ponto p = pilha.pop();
            int px = p.getX();
            int py = p.getY();

            if (px < 0 || py < 0 || px >= img.getWidth() || py >= img.getHeight()) continue;

            if (img.getRGB(px, py) != corOriginal) continue;

            img.setRGB(px, py, novaCor);

            pilha.push(new Ponto(px + 1, py));
            pilha.push(new Ponto(px - 1, py));
            pilha.push(new Ponto(px, py + 1));
            pilha.push(new Ponto(px, py - 1));
        }

        ImageIO.write(img, "png", new File("resultado.png"));
    }
}