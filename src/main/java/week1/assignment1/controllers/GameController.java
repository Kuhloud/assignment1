package week1.assignment1.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import week1.assignment1.models.Game;
import week1.assignment1.services.GameService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }
    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {
        try
        {
            return ResponseEntity.ok(gameService.getAllGames()); // HTTP 200
        } catch (ResponseStatusException e) {
            throw e;
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable long id) {
        try
        {
            Game game = gameService.getGameById(id);
            return ResponseEntity.ok(game); // HTTP 200
        } catch (ResponseStatusException e) {
            throw e;
        }
    }
    @PostMapping
    public ResponseEntity<Game> addGame(@RequestBody Game newGame) {
        try {
            Game game = gameService.addGame(newGame);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(game.getId())
                    .toUri();
            return ResponseEntity.created(location).body(game);
        } catch (ResponseStatusException e) {
            throw e;
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable long id, @RequestBody Game game) {
        try {
            return ResponseEntity.ok(gameService.updateGame(id, game));
        } catch (ResponseStatusException e) {
            throw e;
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable long id) {
        try {
            gameService.deleteGame(id);
            return ResponseEntity.noContent().build(); // HTTP 204
        } catch (ResponseStatusException e) {
            throw e;
        }
    }
}
