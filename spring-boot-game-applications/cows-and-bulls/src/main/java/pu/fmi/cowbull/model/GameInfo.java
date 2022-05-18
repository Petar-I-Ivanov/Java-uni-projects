package pu.fmi.cowbull.model;

import java.util.ArrayList;
import java.util.List;

public class GameInfo {
	
	private String gameId;
	private String number;
	private List<TryInfo> history = new ArrayList<TryInfo>();

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public List<TryInfo> getHistory() {
		return history;
	}
}