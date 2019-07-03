import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class keyInput extends KeyAdapter {
	
	private Selector selector;
	
	public keyInput(Selector selector) {
		this.selector = selector;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
			selector.move(key);
	}
		
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
	}
}
