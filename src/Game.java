import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;




public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 344897139546L;
	
	public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;

	private Thread thread;
	volatile boolean running = false;
	private Board board;
	private Selector selector;
	private ID turn;

	private Random r;

	
	public Game() {
		int turns = 0;
		turn = ID.White;
		board = new Board();
		selector = new Selector(board);
		this.addKeyListener(new keyInput(selector));
		new Window(WIDTH, HEIGHT, "BUILDING A GAME", this);
		while(running = true) {
			if(!board.isInProgress() && turn == ID.White) {
				board.startTurn(ID.White);
				turn = ID.Red;
			}
			if(!board.isInProgress() && turn == ID.Red) {
				board.startTurn(ID.Red);
				turn = ID.White;
			}	
		}
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if(running)
				render();
			frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println("FPS: "+ frames);
				frames = 0;
			}
			
		}
		stop();
		
	}
	
	private void tick() {
		
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy(); 
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		board.render(g);
		selector.render(g);
		
		g.dispose();
		bs.show();
		
	}
	

	
	public static void main(String args[]) {
		new Game();
	}
}