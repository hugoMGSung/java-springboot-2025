package com.pknu.backboard.controller;

import com.pknu.backboard.entity.History;
import com.pknu.backboard.entity.About;
import com.pknu.backboard.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    // Create
    @PostMapping
    public ResponseEntity<String> createHistory(@RequestBody Map<String, String> body) {
        // About 객체는 예시로 null 처리, 실제로는 별도 서비스/로직 필요
        About about = null;
        String year = body.get("year");
        String description = body.get("description");
        historyService.setHistory(about, year, description);
        return ResponseEntity.ok("History created");
    }

    // Read
    @GetMapping("/{id}")
    public ResponseEntity<History> getHistory(@PathVariable Long id) {
        History history = historyService.getHistory(id);
        return ResponseEntity.ok(history);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<String> updateHistory(@PathVariable Long id, @RequestBody History history) {
        history.setId(id);
        historyService.putHistory(history);
        return ResponseEntity.ok("History updated");
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHistory(@PathVariable Long id) {
        // delete 메서드는 HistoryService에 없으므로 직접 구현 필요
        // 예시: historyService.deleteHistory(id);
        return ResponseEntity.ok("Delete method not implemented");
    }
}
