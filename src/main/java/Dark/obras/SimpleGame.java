package Dark.obras;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class SimpleGame extends JPanel implements ActionListener, KeyListener {
	
	
	
	//параметры 
    private int playerX = 175;  // Начальное положение игрока по горизонтали
    private int playerY = 480;  // Начальное положение игрока по вертикали
    private int playerSpeed = 15;  // Скорость движения игрока
    private ArrayList<Integer> enemyX = new ArrayList<>(); // X-координаты врагов
    private ArrayList<Integer> enemyY = new ArrayList<>();  // Y-координаты врагов
    private int enemySpeed = 20;  // Скорость движения врагов
    private Timer timer;  // Таймер для обновления экрана
    private boolean gameOver = false;  // Флаг окончания игры
    private int score = 0;  // Счет игрока
    
    
    
    //конструктор первое что запускаем обьект получает первыми параметрами 
    //обьект получает через конструктор параметры 
    public SimpleGame() {
    	//методы интерфейса 
        addKeyListener(this);//слушателя KeyListener к интересуемому компоненту для прослушивания событий клавиатуры, используется метод addKeyListener.В качестве параметра методу передается ссылка на слушателя.
        //метод супер классе JPanel.
        setFocusable(true);//фокус на кнопках Фокус — это некий указатель, который говорит о том, какой сейчас компонент активен и может реагировать на клавиатуру. Фокус возможно переключать, чтобы добраться до требуемого компонента.
        setFocusTraversalKeysEnabled(false);//если кнопка фокусе то нельзя использовать для перенмещения фокуса другие кнопки
        timer = new Timer(100, this);  // Тут создаем таймер таймер для начала процесса анимации
        timer.start();  // В этой строчке его запускаем
    }
    
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Simple Game");// обьект форма у него есть методы 
        SimpleGame game = new SimpleGame();//сам обьект игра 
        //вызываем методы 
        frame.add(game);//вызываем обьект и его метод вкотором мы вызвали другой обьект 
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }   
    
  //метод супер класса  все что связано с рисованием 
    public void paintComponent(Graphics g) {// в этом методе производится рисование с применением обьекта и его методов 
        super.paintComponent(g);
        g.setColor(Color.BLACK); // Заливаем фон черным цветом
        g.fillRect(0, 0, 400, 600);
        g.setColor(Color.WHITE); // Белый цвет для фигуры игрока
        g.fillRect(playerX, playerY, 50, 50);  // Рисуем объект игрока
        
        //игрок это овал красного цвета с координатами 
             for (int i = 0; i < enemyX.size(); i++) {//счетчик
           g.setColor(Color.RED); // Используем красный цвет
            g.fillOval(enemyX.get(i), enemyY.get(i), 20, 20);//рисуем овал 
        }
        
        
        //шрифты 
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Счет: " + score, 10, 30);  // Выводим счет игрока на экран
        
        
        if (gameOver) {//проиграл
        	//текст
            g.setFont(new Font("Arial", Font.PLAIN, 40));
            g.drawString("Конец игры", 120, 300);  // Выводим надпись "Конец игры" при окончании игры
            timer.stop();  // Останавливаем таймер
        }
    }
    //метод интерфейса Обработчики события
    @Override
    public void actionPerformed(ActionEvent e) {
    //если   
    	if (!gameOver) {
    		//то сделать 
            for (int i = 0; i < enemyX.size(); i++) {
                enemyY.set(i, enemyY.get(i) + enemySpeed);  // Двигаем врагов вниз по экрану
                //если 
                if (enemyY.get(i) >= 600) {
                    enemyX.remove(i);
                    enemyY.remove(i);
                    score++;  // Увеличиваем счет при уничтожении врага
                }
            }
            repaint();  // Перерисовываем экран
            if (enemyX.isEmpty()) {
                spawnEnemy();  // Создаем нового врага, если текущих нет на экране
            }
            checkCollision();  // Проверяем коллизию игрока с врагами
        }
    }
    public void spawnEnemy() {
        Random rand = new Random();
        int numEnemies = rand.nextInt(5) + 1; // Генерируем от 1 до 5 врагов за раз
        for (int i = 0; i < numEnemies; i++) {
            int x = rand.nextInt(350); // Генерируем случайную X-координату для врага
            int y = 0;
            enemyX.add(x);
            enemyY.add(y); // Добавляем врага в списки координат
        }
    }
    public void checkCollision() {
        Rectangle playerBounds = new Rectangle(playerX, playerY, 50, 50);  // Границы игрока
        for (int i = 0; i < enemyX.size(); i++) {
            Rectangle enemyBounds = new Rectangle(enemyX.get(i), enemyY.get(i), 20, 20);  // Границы врага
            if (playerBounds.intersects(enemyBounds)) {
                gameOver = true;  // Если произошло столкновение, игра заканчивается
                break;
            }
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (!gameOver) {
            if (key == KeyEvent.VK_LEFT && playerX > 0) {
                playerX -= playerSpeed;  // Перемещаем игрока влево
            }
            if (key == KeyEvent.VK_RIGHT && playerX < 350) {
                playerX += playerSpeed;  // Перемещаем игрока вправо
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {}
}