package pu.fmi.web.uplow.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pu.fmi.web.uplow.model.TryInfo;
import pu.fmi.web.uplow.logic.GameService;
import pu.fmi.web.uplow.model.GameInfo;

@Controller
public class GameController {
	
	@Autowired
	private GameService gameService;
	
	@GetMapping("/")
	public String homePage() {
		return "Home Page";
	}
	
	@PostMapping("/games")
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
		model.addAttribute("tries", gameInfo.getHistory().size());
		
		if (tryInfo.getMessage().equals("")) {
			return "Win";
		}
		
		if (gameInfo.getHistory().size() == 5) {
			return "Lose";
		}
		
		return "Game";
	}
	
	@PostMapping("games/{id}/try") 
	public String makeTry(@PathVariable String id, @RequestParam String guess) {
		
		GameInfo gameInfo = gameService.makeTry(id, Integer.parseInt(guess));
		return "redirect:/games/" + gameInfo.getGameId();
	}
	
	private static TryInfo setTryInfo(GameInfo gameInfo) {
		
		int triesSize = gameInfo.getHistory().size();
		
		if (triesSize == 0) {
			
			TryInfo tryInfo = new TryInfo();
			tryInfo.setMessage(" ");
			return tryInfo;
		}
		
		return gameInfo.getHistory().get(triesSize - 1);
	}
}