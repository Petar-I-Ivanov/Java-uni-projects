package pu.fmi.web.uplow.model;

import java.util.ArrayList;
import java.util.List;

public class GameInfo {

	private String gameId;
	private int number;
	private List<TryInfo> history = new ArrayList<TryInfo>();
	
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	
	public String getGameId() {
		return this.gameId;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public List<TryInfo> getHistory() {
		return history;
	}
}