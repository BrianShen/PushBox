package com.sj.xzq;

class BackUpInfo {

	private int[][] map;
	private int manX;
	private int manY;

	public BackUpInfo(int[][] map, int manX, int manY) {
		this.map = map;
		this.manX = manX;
		this.manY = manY;
	}

	public int[][] getMap() {
		return map;
	}

	public int getManX() {
		return manX;
	}

	public int getManY() {
		return manY;
	}

}
