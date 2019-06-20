package atlan.ceer;


import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class snake extends JFrame {
	private static int height = 500;
	private static int width = 500;
	// private int score = 0;
	private int compare = 1;
	private Point point = new Point();// 食物
	private Point boom = new Point();// 炸弹
	// private int boomnum =1;//初始化炸弹数量
	private LinkedList<Point> list = new LinkedList<Point>();// 蛇
	// 初始化键盘键
	private int key = 37;//初始化移动方向
	private int time = 150;// 定义线程睡眠时间
	// 坐标的初始化

	public void init() {
		point.setLocation(100, 100);//食物的初始化坐标
		boom.setLocation(200, 250);//炸弹的初始化坐标
		//蛇身体的初始化坐标
		list.add(new Point(300, 300));
		list.add(new Point(310, 300));
		list.add(new Point(320, 300));
		list.add(new Point(330, 300));
		list.add(new Point(340, 300));
		list.add(new Point(350, 300));
		list.add(new Point(360, 300));
		new Thread(new MoveThread()).start();
	}

	public snake() {
		this.setTitle("贪吃蛇");//设置窗口的名称
		this.setResizable(false);// 不能改变大小
		this.setSize(500, 500);// 界面大小
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设置关闭
		this.setLocationRelativeTo(null);// 居中显示
		this.setVisible(true);
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if (e.getKeyCode() >= 37 && e.getKeyCode() <= 40) {
					if (Math.abs(key - e.getKeyCode()) != 2) {
						key = e.getKeyCode();

					}
				}
			}
		});

		init();

	}

	// 实现线程
	class MoveThread implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// 获取蛇的第一个点
				Point p = list.getFirst().getLocation();
				switch (key) {
				case 37:
					p.x = p.x - 10;
					break;
				case 38:
					p.y = p.y - 10;
					break;
				case 39:
					p.x = p.x + 10;
					break;
				case 40:
					p.y = p.y + 10;
					break;
				}

				// 判断游戏结束
				if (p.x < 0 || p.x > 390 || p.y < +0 || p.y > 390 || list.contains(p)) {
					JOptionPane.showMessageDialog(null, "游戏结束，重新开始");
					new snake();
					break;
				}
				// 吃到炸弹游戏结束
				if (p.x == boom.x && p.y == boom.y) {
					JOptionPane.showMessageDialog(null, "你吃到炸弹了,不能重新开始");
					break;
				}
				list.addFirst(p);

				// 吃掉食物后生成新的食物 变长
				if (p.equals(point)) {
					// int compare = 1;
					// score++;// 每吃一个食物分数加一
					time -= 10;// 每吃一个食物移动速度增加
					// boomnum++;//每吃一个食物炸弹数量增加一
					// 速度的极限
					if (time <= 30) {
						time = 30;
					}
					// 生成的食物不能和蛇的身体重合
					for (int i = 0; i < list.size(); i++) {
						int x = (int) (Math.random() * 40) * 10;
						int y = (int) (Math.random() * 40) * 10;
						point.setLocation(x, y);
						// 初始化compare=0 如果食物与身体重合 则赋值为0 重新生成食物
						for (int j = 0; j < list.size(); j++) {
							if (list.get(j).x == x && list.get(j).y == y) {
								compare = 0;
								break;
							}
						}
						if (compare == 0)
							continue;
						else {
							break;
						}

					}
					// 生成炸弹不能与食物和身体重合
					for (int a = 0; a < list.size(); a++) {
						int x1 = (int) (Math.random() * 40) * 10;
						int y1 = (int) (Math.random() * 40) * 10;
						boom.setLocation(x1, y1);
						// 初始化compare=0 如果炸弹食物和身体重合 则赋值为0 重新生成炸弹
						for (int b = 0; b < list.size(); b++) {
							if (list.get(b).x == x1 && list.get(b).y == y1 && list.get(b).x == x1
									&& list.get(b).y == y1) {
								compare = 0;
								break;
							}
						}

					}
				} else {
					list.removeLast();
				}

				// list.addFirst(p);
				// list.removeLast();//删除最后一个点

				// 画的方法paint()
				snake.this.repaint();

			}
		}
	}

	public void paint(Graphics g) {
		// Image img=createImage(500,500);
		// Graphics g2=img.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, height, width);//背景颜色
		g.translate(50, 50);
		g.setColor(Color.RED);
		g.drawRect(0, 0, height - 100, width - 100);//墙
		g.setColor(Color.GREEN);
		// 蛇 获取list中的每一个点
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				g.setColor(Color.RED);
				g.fillRect(list.get(i).x, list.get(i).y, 10, 10);
			} else {
				g.setColor(Color.GREEN);
				g.fillRect(list.get(i).x, list.get(i).y, 10, 10);
			}
		}
		/*
		 * for(Point p:list) { g2.fillRect(p.x, p.y, 10, 10); }
		 */
		// 画食物
		g.setColor(Color.RED);
		g.fillOval(point.x, point.y, 10, 10);
		// 画炸弹

		g.setColor(Color.BLACK);
		g.fillOval(boom.x, boom.y, 10, 10);
		// g.drawImage(img, 0, 0,500,500,this);
	}

	public static void main(String[] args) {
		new snake();
	}
}
