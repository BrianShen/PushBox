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
	private int[][] map; // 地图所对应的二维数组
	private int[][] backMap; // 地图所对应的二维数组,这个数组用于保存上一步的地图信息
	private static final int LEN = 30; // 图片的长
	private static final int WIDTH = 30; // 图片的宽
	private static final int SPEED = 30; // 移动速度，每次移动一格
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
		g.setFont(new Font("仿宋", Font.BOLD, 18));
		g.drawString("关卡:" + level, 50, 50);
	}

	// 响应键盘事件，松开键盘才响应
	// 上下左右四个键，实现人物的移动。
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

	// 实现人物的向上移动
	private void moveUp() {
		// 如果下一步是目标或者是草地，则移动到下一步
		if (map[manX][manY - 1] == 2 || map[manX][manY - 1] == 4) {
			backMap = new int[20][20];
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					backMap[i][j] = map[i][j];// 保存上一步的地图信息
				}
			}
			bp = new BackUpInfo(backMap, manX, manY);
			myStack.push(bp);
			map[manX][manY] = lastImg; // 先还原人物处的背景
			lastImg = map[manX][manY - 1];// 保存人物下一步位置的背景
			map[manX][manY - 1] = 8;// 移动人物到下一步
			manY--;
			repaint();
		} else if (map[manX][manY - 1] == 3 || map[manX][manY - 1] == 9) {
			backMap = new int[20][20];
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					backMap[i][j] = map[i][j];// 保存上一步的地图信息
				}
			}
			bp = new BackUpInfo(backMap, manX, manY);
			myStack.push(bp);
			// 如果下一步是箱子
			if (map[manX][manY - 2] == 2) {
				// 箱子前面是草地
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
				// 箱子前面是目标
				map[manX][manY - 2] = 9;// 把箱子移动到目标，换成另一种颜色的箱子
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

	// 实现人物的向下移动
	private void moveDown() {
		// 如果下一步是目标或者是草地，则移动到下一步
		backMap = new int[20][20];
		if (map[manX][manY + 1] == 2 || map[manX][manY + 1] == 4) {
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					backMap[i][j] = map[i][j];// 保存上一步的地图信息
				}
			}
			bp = new BackUpInfo(backMap, manX, manY);
			myStack.push(bp);
			map[manX][manY] = lastImg; // 先还原人物处的背景
			lastImg = map[manX][manY + 1];// 保存人物下一步位置的背景
			map[manX][manY + 1] = 5;// 移动人物到下一步
			manY++;
			repaint();
		} else if (map[manX][manY + 1] == 3 || map[manX][manY + 1] == 9) {
			backMap = new int[20][20];
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					backMap[i][j] = map[i][j];// 保存上一步的地图信息
				}
			}
			bp = new BackUpInfo(backMap, manX, manY);
			myStack.push(bp);
			// 如果下一步是箱子
			if (map[manX][manY + 2] == 2) {
				// 箱子前面是草地
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
				// 箱子前面是目标
				map[manX][manY + 2] = 9;// 把箱子移动到目标，换成另一种颜色的箱子
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

	// 实现人物的左移
	private void moveLeft() {
		// 如果下一步是目标或者是草地，则移动到下一步
		if (map[manX - 1][manY] == 2 || map[manX - 1][manY] == 4) {
			backMap = new int[20][20];
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					backMap[i][j] = map[i][j];// 保存上一步的地图信息
				}
			}
			bp = new BackUpInfo(backMap, manX, manY);
			myStack.push(bp);
			map[manX][manY] = lastImg; // 先还原人物处的背景
			lastImg = map[manX - 1][manY];// 保存人物下一步位置的背景
			map[manX - 1][manY] = 6;// 移动人物到下一步
			manX--;
			repaint();
		} else if (map[manX - 1][manY] == 3 || map[manX - 1][manY] == 9) {
			backMap = new int[20][20];
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					backMap[i][j] = map[i][j];// 保存上一步的地图信息
				}
			}
			bp = new BackUpInfo(backMap, manX, manY);
			myStack.push(bp);
			// 如果下一步是箱子
			if (map[manX - 2][manY] == 2) {
				// 箱子前面是草地
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
				// 箱子前面是目标
				map[manX - 2][manY] = 9;// 把箱子移动到目标，换成另一种颜色的箱子
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

	// 实现人物的右移
	private void moveRight() {
		// 如果下一步是目标或者是草地，则移动到下一步
		if (map[manX + 1][manY] == 2 || map[manX + 1][manY] == 4) {
			backMap = new int[20][20];
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					backMap[i][j] = map[i][j];// 保存上一步的地图信息
				}
			}
			bp = new BackUpInfo(backMap, manX, manY);
			myStack.push(bp);
			map[manX][manY] = lastImg; // 先还原人物处的背景
			lastImg = map[manX + 1][manY];// 保存人物下一步位置的背景
			map[manX + 1][manY] = 7;// 移动人物到下一步
			manX++;
			repaint();
		} else if (map[manX + 1][manY] == 3 || map[manX + 1][manY] == 9) {
			backMap = new int[20][20];
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					backMap[i][j] = map[i][j];// 保存上一步的地图信息
				}
			}
			bp = new BackUpInfo(backMap, manX, manY);
			myStack.push(bp);
			// 如果下一步是箱子
			if (map[manX + 2][manY] == 2) {
				// 箱子前面是草地
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
				// 箱子前面是目标
				map[manX + 2][manY] = 9;// 把箱子移动到目标，换成另一种颜色的箱子
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

	// 判断是否过关
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

	// 退一步
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

	// 上一关
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

	// 下一关
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

	// 选关
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

	// 重新开始
	public void reStart() {
		myStack.clear();
		lm = new LoadMap(level);
		map = lm.getMap();
		this.manX = lm.getManX();
		this.manY = lm.getManY();
		repaint();
	}

}
