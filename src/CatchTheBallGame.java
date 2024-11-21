import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class CatchTheBallGame extends JPanel {
    private int score = 0;
    private int ballX = 100;
    private int ballY = 100;
    private final int ballSize = 30; // Размер шарика
    private Random random = new Random();
    private boolean gameRunning = true;
    private int timeLeft = 10; // Время игры в секундах
    private Timer timer; // Таймер для отсчета времени

    public CatchTheBallGame() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);

        // Таймер для перемещения шарика
        Timer ballMovementTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameRunning) {
                    moveBall();
                    repaint();
                }
            }
        });
        ballMovementTimer.start();

        // Таймер на 10 секунд
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameRunning) {
                    timeLeft--;
                    if (timeLeft <= 0) {
                        gameRunning = false;
                        timer.stop();
                        JOptionPane.showMessageDialog(null, "Время вышло! Ваш счет: " + score);
                    }
                    repaint();
                }
            }
        });
        timer.start();

        // Обработка кликов мыши
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (gameRunning && isBallClicked(e.getX(), e.getY())) {
                    score++;
                    moveBall();
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillOval(ballX, ballY, ballSize, ballSize);
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 10, 20);
        g.drawString("Оставшееся время: " + timeLeft + " секунд", 10, 40);
        if (!gameRunning) {
            g.drawString("Игра окончена!", getWidth() / 2 - 50, getHeight() / 2);
        }
    }

    private void moveBall() {
        ballX = random.nextInt(getWidth() - ballSize);
        ballY = random.nextInt(getHeight() - ballSize);
    }

    private boolean isBallClicked(int mouseX, int mouseY) {
        return mouseX >= ballX && mouseX <= ballX + ballSize &&
                mouseY >= ballY && mouseY <= ballY + ballSize;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Catch The Ball Game");
        CatchTheBallGame game = new CatchTheBallGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
