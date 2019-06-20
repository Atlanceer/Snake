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
	private Point point = new Point();// ʳ��
	private Point boom = new Point();// ը��
	// private int boomnum =1;//��ʼ��ը������
	private LinkedList<Point> list = new LinkedList<Point>();// ��
	// ��ʼ�����̼�
	private int key = 37;//��ʼ���ƶ�����
	private int time = 150;// �����߳�˯��ʱ��
	// ����ĳ�ʼ��

	public void init() {
		point.setLocation(100, 100);//ʳ��ĳ�ʼ������
		boom.setLocation(200, 250);//ը���ĳ�ʼ������
		//������ĳ�ʼ������
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
		this.setTitle("̰����");//���ô��ڵ�����
		this.setResizable(false);// ���ܸı��С
		this.setSize(500, 500);// �����С
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// ���ùر�
		this.setLocationRelativeTo(null);// ������ʾ
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

	// ʵ���߳�
	class MoveThread implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// ��ȡ�ߵĵ�һ����
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

				// �ж���Ϸ����
				if (p.x < 0 || p.x > 390 || p.y < +0 || p.y > 390 || list.contains(p)) {
					JOptionPane.showMessageDialog(null, "��Ϸ���������¿�ʼ");
					new snake();
					break;
				}
				// �Ե�ը����Ϸ����
				if (p.x == boom.x && p.y == boom.y) {
					JOptionPane.showMessageDialog(null, "��Ե�ը����,�������¿�ʼ");
					break;
				}
				list.addFirst(p);

				// �Ե�ʳ��������µ�ʳ�� �䳤
				if (p.equals(point)) {
					// int compare = 1;
					// score++;// ÿ��һ��ʳ�������һ
					time -= 10;// ÿ��һ��ʳ���ƶ��ٶ�����
					// boomnum++;//ÿ��һ��ʳ��ը����������һ
					// �ٶȵļ���
					if (time <= 30) {
						time = 30;
					}
					// ���ɵ�ʳ�ﲻ�ܺ��ߵ������غ�
					for (int i = 0; i < list.size(); i++) {
						int x = (int) (Math.random() * 40) * 10;
						int y = (int) (Math.random() * 40) * 10;
						point.setLocation(x, y);
						// ��ʼ��compare=0 ���ʳ���������غ� ��ֵΪ0 ��������ʳ��
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
					// ����ը��������ʳ��������غ�
					for (int a = 0; a < list.size(); a++) {
						int x1 = (int) (Math.random() * 40) * 10;
						int y1 = (int) (Math.random() * 40) * 10;
						boom.setLocation(x1, y1);
						// ��ʼ��compare=0 ���ը��ʳ��������غ� ��ֵΪ0 ��������ը��
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
				// list.removeLast();//ɾ�����һ����

				// ���ķ���paint()
				snake.this.repaint();

			}
		}
	}

	public void paint(Graphics g) {
		// Image img=createImage(500,500);
		// Graphics g2=img.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, height, width);//������ɫ
		g.translate(50, 50);
		g.setColor(Color.RED);
		g.drawRect(0, 0, height - 100, width - 100);//ǽ
		g.setColor(Color.GREEN);
		// �� ��ȡlist�е�ÿһ����
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
		// ��ʳ��
		g.setColor(Color.RED);
		g.fillOval(point.x, point.y, 10, 10);
		// ��ը��

		g.setColor(Color.BLACK);
		g.fillOval(boom.x, boom.y, 10, 10);
		// g.drawImage(img, 0, 0,500,500,this);
	}

	public static void main(String[] args) {
		new snake();
	}
}
