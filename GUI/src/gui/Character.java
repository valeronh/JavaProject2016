package gui;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import algorithms.mazeGenarators.Position;
/**
 * class Character
 */
public class Character {
	private Position posisiton;
	private Image img;
	
	public Character() {
		this.img = new Image(null, "resources/images/MosesCharacter.png");
	}

	public Position getPos() {
		return posisiton;
	}

	public void setPos(Position pos) {
		this.posisiton = pos;
	}
	/**
	 *draw method draws the image itself
	 *@param cellWidth is the width of the image
	 *@param cellHeight is the height of the image
	 *@param gc, GC
	 */
	public void draw(int width, int height, GC gc) {
		gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, width * posisiton.getColumn(), height * posisiton.getRow(), width, height);
	}
	
}