import java.awt.*;
import java.awt.geom.AffineTransform;

public class DrawX implements Shape {
public Integer centerWidth;
public Integer centerHeight;
public Double scale;

public DrawX(Integer centerWidth, Integer centerHeight) {
	this.centerWidth = centerWidth;
	this.centerHeight = centerHeight;
	this.scale = 0.2;
}

public DrawX(Integer centerWidth, Integer centerHeight, Double scale) {
	this.centerWidth = centerWidth;
	this.centerHeight = centerHeight;
	this.scale = scale;
}

@Override
public void render(Graphics2D g2d) {
	AffineTransform mat = g2d.getTransform();
	g2d.setPaint(new Color(20, 32, 130));
	g2d.translate(100, 100);
	g2d.scale(scale, scale);
	for (int i = 0; i < 4; i++) {
		g2d.drawLine(0, 0, 100, 100);
		g2d.drawLine(1, 0, 101, 100);
		g2d.drawLine(-1, 0, 99, 100);
		g2d.rotate(2 * Math.PI / 4);
	}
	g2d.setTransform(mat);
}

@Override
public void transform(Graphics2D g2d) {
	g2d.translate(centerWidth + 7, centerHeight - 30);
}

}
