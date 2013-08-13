package com.sj.xzq;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Stack;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Game extends JPanel {

	private LoadMap lm;
	private int level;
	private int manX, manY;
	private int lastImg = 2;
	private int[][] map; // ��ͼ����Ӧ�Ķ�ά����
	private int[][] backMap; // ��ͼ����Ӧ�Ķ�ά����,����������ڱ�����һ���ĵ�ͼ��Ϣ
	private static final int LEN = 30; // ͼƬ�ĳ�
	private static final int WIDTH = 30; // ͼƬ�Ŀ�
	private static final int SPEED = 30; // �ƶ��ٶȣ�ÿ���ƶ�һ��
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] imgs = null;
	private Stack<BackUpInfo> myStack = new Stack<BackUpInfo>();
	private BackUpInfo bp;
	static {
		imgs = new Image[] {
				tk.getImage(GameFrame.class.getClassLoader().getResource(
						"imgs/0.gif")),
				tk.getImage(GameFrame.class.getClassLoader().getResource(
						"imgs/1.gif")),
				tk.getImage(GameFrame.class.getClassLoader().getResource(
						"imgs/2.GIF")),
				tk.getImage(GameFrame.class.getClassLoader().getResource(
						"imgs/3.GIF")),
				tk.getImage(GameFrame.class.getClassLoader().getResource(
						"imgs/4.gif")),
				tk.getImage(GameFrame.class.getClassLoader().getResource(
						"imgs/5.GIF")),
				tk.getImage(GameFrame.class.getClassLoader().getResource(
						"imgs/6.GIF")),
				tk.getImage(GameFrame.class.getClassLoader().getResource(
						"imgs/7.GIF")),
				tk.getImage(GameFrame.class.getClassLoader().getResource(
						"imgs/8.GIF")),
				tk.getImage(GameFrame.class.getClassLoader().getResource(
						"imgs/9.GIF")) };
	}

	public Game(int level) {
		this.setBounds(0, 0, 600, 600);
		this.setVisible(true);
		lm = new LoadMap(level);
		map = lm.getMap();
		this.manX = lm.getManX();
		this.manY = lm.getManY();
		this.level = level;

	}

	public void paint(Graphics g) {

		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {

				g.drawImage(imgs[map[i][j]], i * LEN, j * WIDTH, this);

			}
		}
		g.setColor(Color.BLUE);
		g.setFont(new Font("����", Font.BOLD, 18));
		g.drawString("�ؿ�:" + level, 50, 50);
	}

	// ��Ӧ�����¼����ɿ����̲���Ӧ
	// ���������ĸ�����ʵ��������ƶ���
	public void keyReleased(KeyEvent e) {
		int keycode = e.getKeyCode();
		if (!isPass()) {
			switch (keycode) {
			case KeyEvent.VK_UP:
				moveUp();
				if (isPass()) {
					myStack.clear();
					lm = new LoadMap(++level);
					map = lm.getMap();
					this.manX = lm.getManX();
					this.manY = lm.getManY();
				}
				break;
			case KeyEvent.VK_DOWN:
				moveDown();
				if (isPass()) {
					myStack.clear();
					lm = new LoadMap(++level);
					map = lm.getMap();
					this.manX = lm.getManX();
					this.manY = lm.getManY();
				}
				break;
			case KeyEvent.VK_LEFT:
				moveLeft();
				if (isPass()) {
					myStack.clear();
					lm = new LoadMap(++level);
					map = lm.getMap();
					this.manX = lm.getManX();
					this.manY = lm.getManY();
				}
				break;
			case KeyEvent.VK_RIGHT:
				moveRight();
				if (isPass()) {
					myStack.clear();
					lm = new LoadMap(++level);
					map = lm.getMap();
					this.manX = lm.getManX();
					this.manY = lm.getManY();
				}
				break;
			case KeyEvent.VK_SPACE:
				back();
				break;
			default:
				break;
			}
		}
	}

	// ʵ������������ƶ�
	private void moveUp() {
		// �����һ����Ŀ������ǲݵأ����ƶ�����һ��
		if (map[manX][manY - 1] == 2 || map[manX][manY - 1] == 4) {
			backMap = new int[20][20];
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					backMap[i][j] = map[i][j];// ������һ���ĵ�ͼ��Ϣ
				}
			}
			bp = new BackUpInfo(backMap, manX, manY);
			myStack.push(bp);
			map[manX][manY] = lastImg; // �Ȼ�ԭ���ﴦ�ı���
			lastImg = map[manX][manY - 1];// ����������һ��λ�õı���
			map[manX][manY - 1] = 8;// �ƶ����ﵽ��һ��
			manY--;
			repaint();
		} else if (map[manX][manY - 1] == 3 || map[manX][manY - 1] == 9) {
			backMap = new int[20][20];
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					backMap[i][j] = map[i][j];// ������һ���ĵ�ͼ��Ϣ
				}
			}
			bp = new BackUpInfo(backMap, manX, manY);
			myStack.push(bp);
			// �����һ��������
			if (map[manX][manY - 2] == 2) {
				// ����ǰ���ǲݵ�
				map[manX][manY - 2] = 3;
				map[manX][manY] = lastImg;
				if (map[manX][manY - 1] == 3) {
					lastImg = 2;
				} else {
					lastImg = 4;
				}
				map[manX][manY - 1] = 8;
				manY--;
				repaint();
			} else if (map[manX][manY - 2] == 4) {
				// ����ǰ����Ŀ��
				map[manX][manY - 2] = 9;// �������ƶ���Ŀ�꣬������һ����ɫ������
				map[manX][manY] = lastImg;
				if (map[manX][manY - 1] == 3) {
					lastImg = 2;
				} else {
					lastImg = 4;
				}
				map[manX][manY - 1] = 8;
				manY--;
				repaint();
			}
		}
	}

	// ʵ������������ƶ�
	private void moveDown() {
		// �����һ����Ŀ������ǲݵأ����ƶ�����һ��
		backMap = new int[20][20];
		if (map[manX][manY + 1] == 2 || map[manX][manY + 1] == 4) {
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					backMap[i][j] = map[i][j];// ������һ���ĵ�ͼ��Ϣ
				}
			}
			bp = new BackUpInfo(backMap, manX, manY);
			myStack.push(bp);
			map[manX][manY] = lastImg; // �Ȼ�ԭ���ﴦ�ı���
			lastImg = map[manX][manY + 1];// ����������һ��λ�õı���
			map[manX][manY + 1] = 5;// �ƶ����ﵽ��һ��
			manY++;
			repaint();
		} else if (map[manX][manY + 1] == 3 || map[manX][manY + 1] == 9) {
			backMap = new int[20][20];
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					backMap[i][j] = map[i][j];// ������һ���ĵ�ͼ��Ϣ
				}
			}
			bp = new BackUpInfo(backMap, manX, manY);
			myStack.push(bp);
			// �����һ��������
			if (map[manX][manY + 2] == 2) {
				// ����ǰ���ǲݵ�
				map[manX][manY + 2] = 3;
				map[manX][manY] = lastImg;
				if (map[manX][manY + 1] == 3) {
					lastImg = 2;
				} else {
					lastImg = 4;
				}
				map[manX][manY + 1] = 5;
				manY++;
				repaint();
			} else if (map[manX][manY + 2] == 4) {
				// ����ǰ����Ŀ��
				map[manX][manY + 2] = 9;// �������ƶ���Ŀ�꣬������һ����ɫ������
				map[manX][manY] = lastImg;
				if (map[manX][manY + 1] == 3) {
					lastImg = 2;
				} else {
					lastImg = 4;
				}
				map[manX][manY + 1] = 5;
				manY++;
				repaint();
			}
		}
	}

	// ʵ�����������
	private void moveLeft() {
		// �����һ����Ŀ������ǲݵأ����ƶ�����һ��
		if (map[manX - 1][manY] == 2 || map[manX - 1][manY] == 4) {
			backMap = new int[20][20];
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					backMap[i][j] = map[i][j];// ������һ���ĵ�ͼ��Ϣ
				}
			}
			bp = new BackUpInfo(backMap, manX, manY);
			myStack.push(bp);
			map[manX][manY] = lastImg; // �Ȼ�ԭ���ﴦ�ı���
			lastImg = map[manX - 1][manY];// ����������һ��λ�õı���
			map[manX - 1][manY] = 6;// �ƶ����ﵽ��һ��
			manX--;
			repaint();
		} else if (map[manX - 1][manY] == 3 || map[manX - 1][manY] == 9) {
			backMap = new int[20][20];
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					backMap[i][j] = map[i][j];// ������һ���ĵ�ͼ��Ϣ
				}
			}
			bp = new BackUpInfo(backMap, manX, manY);
			myStack.push(bp);
			// �����һ��������
			if (map[manX - 2][manY] == 2) {
				// ����ǰ���ǲݵ�
				map[manX - 2][manY] = 3;
				map[manX][manY] = lastImg;
				if (map[manX - 1][manY] == 3) {
					lastImg = 2;
				} else {
					lastImg = 4;
				}
				map[manX - 1][manY] = 6;
				manX--;
				repaint();
			} else if (map[manX - 2][manY] == 4) {
				// ����ǰ����Ŀ��
				map[manX - 2][manY] = 9;// �������ƶ���Ŀ�꣬������һ����ɫ������
				map[manX][manY] = lastImg;
				if (map[manX - 1][manY] == 3) {
					lastImg = 2;
				} else {
					lastImg = 4;
				}
				map[manX - 1][manY] = 6;
				manX--;
				repaint();
			}
		}
	}

	// ʵ�����������
	private void moveRight() {
		// �����һ����Ŀ������ǲݵأ����ƶ�����һ��
		if (map[manX + 1][manY] == 2 || map[manX + 1][manY] == 4) {
			backMap = new int[20][20];
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					backMap[i][j] = map[i][j];// ������һ���ĵ�ͼ��Ϣ
				}
			}
			bp = new BackUpInfo(backMap, manX, manY);
			myStack.push(bp);
			map[manX][manY] = lastImg; // �Ȼ�ԭ���ﴦ�ı���
			lastImg = map[manX + 1][manY];// ����������һ��λ�õı���
			map[manX + 1][manY] = 7;// �ƶ����ﵽ��һ��
			manX++;
			repaint();
		} else if (map[manX + 1][manY] == 3 || map[manX + 1][manY] == 9) {
			backMap = new int[20][20];
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					backMap[i][j] = map[i][j];// ������һ���ĵ�ͼ��Ϣ
				}
			}
			bp = new BackUpInfo(backMap, manX, manY);
			myStack.push(bp);
			// �����һ��������
			if (map[manX + 2][manY] == 2) {
				// ����ǰ���ǲݵ�
				map[manX + 2][manY] = 3;
				map[manX][manY] = lastImg;
				if (map[manX + 1][manY] == 3) {
					lastImg = 2;
				} else {
					lastImg = 4;
				}
				map[manX + 1][manY] = 7;
				manX++;
				repaint();
			} else if (map[manX + 2][manY] == 4) {
				// ����ǰ����Ŀ��
				map[manX + 2][manY] = 9;// �������ƶ���Ŀ�꣬������һ����ɫ������
				map[manX][manY] = lastImg;
				if (map[manX + 1][manY] == 3) {
					lastImg = 2;
				} else {
					lastImg = 4;
				}
				map[manX + 1][manY] = 7;
				manX++;
				repaint();
			}
		}
	}

	// �ж��Ƿ����
	private boolean isPass() {
		boolean pass = true;
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				if (map[i][j] == 3) {
					pass = false;
					break;
				}
			}
		}
		return pass;
	}

	// ��һ��
	public void back() {
		if (!myStack.isEmpty()) {
			BackUpInfo bp = myStack.pop();
			backMap = new int[20][20];
			backMap = bp.getMap();
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					map[i][j] = backMap[i][j];
				}

			}
			this.manX = bp.getManX();
			this.manY = bp.getManY();

			repaint();
		}
	}

	// ��һ��
	public void goBack() {
		if (this.level != 1) {
			myStack.clear();
			lm = new LoadMap(--level);
			map = lm.getMap();
			this.manX = lm.getManX();
			this.manY = lm.getManY();
			repaint();
		}
	}

	// ��һ��
	public void goNext() {
		if (this.level != 50) {
			myStack.clear();
			lm = new LoadMap(++level);
			map = lm.getMap();
			this.manX = lm.getManX();
			this.manY = lm.getManY();
			repaint();
		}
	}

	// ѡ��
	public void choice(int level) {
		if (level > 0 && level <= 50) {
			myStack.clear();
			lm = new LoadMap(level);
			map = lm.getMap();
			this.manX = lm.getManX();
			this.manY = lm.getManY();
			this.level = level;
			repaint();
		}
	}

	// ���¿�ʼ
	public void reStart() {
		myStack.clear();
		lm = new LoadMap(level);
		map = lm.getMap();
		this.manX = lm.getManX();
		this.manY = lm.getManY();
		repaint();
	}

}
