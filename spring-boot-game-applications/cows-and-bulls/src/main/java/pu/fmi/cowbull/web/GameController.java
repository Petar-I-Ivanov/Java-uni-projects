package pu.fmi.cowbull.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pu.fmi.cowbull.logic.GameService;
import pu.fmi.cowbull.model.GameInfo;
import pu.fmi.cowbull.model.TryInfo;

@Controller
public class GameController {
	
	@Autowired
	private GameService gameService;

	@GetMapping("/")
	public String hello() {
		return "Welcome";
	}

	@PostMapping("games")
	public String startNewGame() {
		
		GameInfo gameInfo = gameService.startGame();
		return "redirect:/games/" + gameInfo.getGameId();
	}
	
	@GetMapping("games/{id}")
	public String showGame(@PathVariable String id, Model model) {
		
		GameInfo gameInfo = gameService.getGame(id);
		TryInfo tryInfo = setTryInfo(gameInfo);
		
		model.addAttribute("game", gameInfo);
		model.addAttribute("try", tryInfo);
		
		if (tryInfo.getBulls() == 4) {
			return "Win";
		}
		
		return "Game";
	}
	
	@PostMapping("games/{id}/try") 
	public String makeTry(@PathVariable String id, @RequestParam String guess) {
		
		GameInfo gameInfo = gameService.makeTry(id, guess);
		return "redirect:/games/" + gameInfo.getGameId();
	}
	
	private static TryInfo setTryInfo(GameInfo gameInfo) {
		
		int triesSize = gameInfo.getHistory().size();
		
		if (triesSize == 0) {
			
			TryInfo tryInfo = new TryInfo();
			tryInfo.setCows(0);
			tryInfo.setBulls(0);
			
			return tryInfo;
		}
		
		return gameInfo.getHistory().get(triesSize - 1);
	}
}