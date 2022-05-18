package pu.fmi.cowbull.model;

public class TryInfo {
	
	private String guess;
	private int cows;
	private int bulls;

	public String getGuess() {
		return guess;
	}

	public void setGuessString(String guess) {
		this.guess = guess;
	}

	public int getCows() {
		return cows;
	}

	public void setCows(int cows) {
		this.cows = cows;
	}
	
	public int getBulls() {
		return bulls;
	}

	public void setBulls(int bulls) {
		this.bulls = bulls;
	}
}