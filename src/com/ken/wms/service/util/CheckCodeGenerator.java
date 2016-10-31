package com.ken.wms.service.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Component;

/**
 * 图形验证码产生器
 * @author Ken
 *
 */
@Component
public class CheckCodeGenerator {

	private static char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
			'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	private static int width = 80;// 生成验证码的宽度
	private static int height = 35;// 生成验证码的高度
	private static int codeCount = 4;// 验证码个数
	private static int lineCounr = 20;
	private static Font font;// 验证码字体样式

	static {
		font = new Font("楷体", Font.BOLD|Font.ITALIC, 25);
	}

	/**
	 * 获取验证码
	 * @return
	 */
	public Map<String, Object> generlateCheckCode() {

		// 存储验证码
		StringBuilder codeBuilder = new StringBuilder();

		Random random = new Random();

		// 创建 BufferedImage 对象
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		// 创建Grapchics2D对象
		Graphics graphics = image.getGraphics();
		Graphics2D graphics2d = (Graphics2D) graphics;

		// 设置图片
		graphics.setColor(Color.white);
		graphics.fillRect(1, 1, width - 2, height - 2);
		graphics.setFont(font);
		
		graphics.setColor(Color.gray);
		// 绘制颜色和位置全部为随机产生的线条,该线条为2f
		for (int i = 1; i <= lineCounr; i++) {
			int x = random.nextInt(width - 1);
			int y = random.nextInt(height - 1);
			int x1 = random.nextInt(width - 1);
			int y1 = random.nextInt(height - 1);

			Line2D line2d = new Line2D.Double(x, y, x1, y1);

			graphics2d.draw(line2d);
		}

		// 设置验证码中的内容
		for (int i = 0; i < codeCount; i++) {
			graphics.setColor(getRandColor());
			char c = codeSequence[random.nextInt(codeSequence.length - 1)];
			codeBuilder.append(c);
			graphics2d.drawString(c + "", 10 + 15 * i, 25);
		}

		Map<String, Object> checkCode = new HashMap<>();
		checkCode.put("checkCodeString", codeBuilder.toString());
		checkCode.put("checkCodeImage", image);
		
		return checkCode;
	}

	/**
	 * 随机生成颜色
	 * 
	 * @return
	 */
	private Color getRandColor() {
		Random random = new Random();

		int r, g, b;
		r = random.nextInt(255);
		g = random.nextInt(255);
		b = random.nextInt(255);

		return new Color(r, g, b);
	}
}
