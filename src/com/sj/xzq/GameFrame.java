package com.sj.xzq;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;




public class GameFrame extends JFrame {

	private int level = 1;
	private Game game = new Game(level);
	private Object[] possibleValues = new Object[50];
	JPanel jp;

	public GameFrame() {

		this.setTitle("推箱子");
		this.setBounds(200, 100, 800, 600);
		// this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		for (int i = 0; i < 50; i++) {
			possibleValues[i] = "第" + (i + 1) + "关";
		}

		jp = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				ImageIcon   img=new   ImageIcon(GameFrame.class.getClassLoader().getResource("imgs/backgroundImg.png"));
				g.drawImage(img.getImage(), 0, 0, null);
			}
		};

		this.add(jp);
		jp.setLayout(null);
		JButton jb1 = new JButton("开始游戏");
		jp.add(jb1);
		jb1.setBounds(630, 350, 100, 30);
		jb1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				startGame();
				requestFocus();
			}
		});

		JButton jb2 = new JButton("操作说明");
		jp.add(jb2);
		jb2.setBounds(630, 400, 100, 30);
		jb2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "人物移动:方向键上下左右"
						+ "\r\n后退一步:空格键", "操作说明",
						JOptionPane.INFORMATION_MESSAGE);
				requestFocus();
			}
		});

		JButton jb3 = new JButton("退出游戏");
		jp.add(jb3);
		jb3.setBounds(630, 450, 100, 30);
		jb3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				setVisible(false);
				requestFocus();
			}
		});

		this.requestFocus();

		this.addKeyListener(new MyKeyListener());

	}

	// 开始游戏
	public void startGame() {
		repaint();
		this.remove(jp);
		this.setLayout(null);
		this.add(game);
		JPanel jp1 = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon img = new ImageIcon(GameFrame.class.getClassLoader().getResource("imgs/toolImg.png"));
				g.drawImage(img.getImage(), 0, 0, null);
			}
		};
		jp1.setBounds(600, 0, 200, 600);
		jp1.setLayout(null);
		this.add(jp1);
		JButton jb = new JButton("后退一步");
		jp1.add(jb);
		jb.setBounds(50, 200, 100, 30);
		jb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				game.back();
				requestFocus();
			}
		});
		JButton jb1 = new JButton("上一关");
		jp1.add(jb1);
		jb1.setBounds(50, 250, 100, 30);
		jb1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				game.goBack();
				requestFocus();
			}
		});
		JButton jb4 = new JButton("下一关");
		jp1.add(jb4);
		jb4.setBounds(50, 300, 100, 30);
		jb4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				game.goNext();
				requestFocus();
			}
		});

		JButton jb5 = new JButton("选关");
		jp1.add(jb5);
		jb5.setBounds(50, 350, 100, 30);
		jb5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 用户的选择项目
				Object selectedValue = JOptionPane.showInputDialog(null,
						"请选择关卡:", "选关", JOptionPane.INFORMATION_MESSAGE, null,
						possibleValues, possibleValues[0]);
				if(selectedValue != null) {
					for (int i = 0; i < 50; i++) {
						if (selectedValue.toString().equals(
								possibleValues[i].toString())) {
							game.choice(i + 1);
							break;
						}
					}
				}
				requestFocus();
			}
		});

		JButton jb6 = new JButton("重新开始");
		jp1.add(jb6);
		jb6.setBounds(50, 400, 100, 30);
		jb6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				game.reStart();
				requestFocus();
			}
		});

		JButton jb7 = new JButton("退出游戏");
		jp1.add(jb7);
		jb7.setBounds(50, 450, 100, 30);
		jb7.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				setVisible(false);
				requestFocus();
			}
		});
	}

	// 键盘监听
	class MyKeyListener extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			game.keyReleased(e);
		}

	}

	public static void main(String[] args) {

		new GameFrame();
	}

}
