import java.awt.*;
import java.awt.geom.AffineTransform;

public class DrawO implements Shape {
public Integer centerWidth;
public Integer centerHeight;
public Double scale;

public DrawO(Integer centerWidth, Integer centerHeight, Double scale) {
	this.centerWidth = centerWidth;
	this.centerHeight = centerHeight;
	this.scale = scale;
}

public DrawO(Integer centerWidth, Integer centerHeight) {
	this.centerWidth = centerWidth;
	this.centerHeight = centerHeight;
	this.scale = scale = 0.2;
}

@Override
public void render(Graphics2D g2d) {
	AffineTransform mat = g2d.getTransform();
	g2d.setPaint(new Color(224, 33, 215));
	g2d.translate(100, 100);
	g2d.scale(scale,scale);
	for (int i = 0; i < 100; i++) {
		g2d.drawLine(100, 100, 106, 101);
		g2d.rotate(2 * Math.PI / 100);
	}
	g2d.setTransform(mat);
}

@Override
public void transform(Graphics2D g2d) {
	g2d.translate(centerWidth, centerHeight);
}

}
