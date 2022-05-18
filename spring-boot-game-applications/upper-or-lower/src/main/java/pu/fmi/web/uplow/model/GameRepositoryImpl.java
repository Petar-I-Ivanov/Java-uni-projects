package pu.fmi.web.uplow.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component("gameRepository")
public class GameRepositoryImpl implements GameRepository {

private Map<String, GameInfo> games = new ConcurrentHashMap<String, GameInfo>();
	
	@Override
	public void addGame(GameInfo gameInfo) {
		games.put(gameInfo.getGameId(), gameInfo);
	}
	
	@Override
	public GameInfo getGame (String gameId) {
		return games.get(gameId);
	}
}