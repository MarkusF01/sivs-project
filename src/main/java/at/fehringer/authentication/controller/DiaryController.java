package at.fehringer.authentication.controller;

import at.fehringer.authentication.controller.dto.DiaryEntryResponse;
import at.fehringer.authentication.controller.dto.CreateDiaryEntryRequest;
import at.fehringer.authentication.controller.dto.DiaryEntryResponseData;
import at.fehringer.authentication.repository.DiaryEntryRepository;
import at.fehringer.authentication.repository.UserRepository;
import at.fehringer.authentication.repository.model.DiaryEntry;
import at.fehringer.authentication.repository.model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users/current/diary-entries")
public class DiaryController {
    private final UserRepository userRepository;
    private final DiaryEntryRepository diaryEntryRepository;
    private final ModelMapper mapper;

    @Autowired
    public DiaryController(UserRepository userRepository, DiaryEntryRepository diaryEntryRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.diaryEntryRepository = diaryEntryRepository;
        this.mapper = mapper;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> getDiaryEntries(Authentication authentication) {
        Optional<User> userOptional = userRepository.findByUsername(authentication.getName());

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = userOptional.get();
        List<DiaryEntry> diaryEntries = user.getDiaryEntries();

        if(diaryEntries.isEmpty()) {
            return ResponseEntity.ok(new DiaryEntryResponse(user.getUsername(), Collections.emptyList()));
        }

        Type listType = new TypeToken<List<DiaryEntryResponseData>>() {}.getType();
        List<DiaryEntryResponseData> mappedEntries = mapper.map(diaryEntries, listType);
        String username = diaryEntries.getFirst().getUser().getUsername();
        DiaryEntryResponse response = new DiaryEntryResponse(username, mappedEntries);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<?> deleteDiaryEntry(@PathVariable Integer diaryId, Authentication authentication) {
        Optional<DiaryEntry> diaryEntryOptional = diaryEntryRepository.findById(diaryId);

        if (diaryEntryOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Diary entry not found");
        }

        String authUsername = authentication.getName();
        if(!diaryEntryOptional.get().getUser().getUsername().equals(authUsername)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        diaryEntryRepository.deleteById(diaryId);
        return ResponseEntity.ok("Diary entry deleted successfully");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<?> createDiaryEntry(@RequestBody CreateDiaryEntryRequest diaryEntry, Authentication authentication) {
        Optional<User> userOptional = userRepository.findByUsername(authentication.getName());

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = userOptional.get();
        DiaryEntry diaryEntryToAdd = new DiaryEntry();
        diaryEntryToAdd.setEntryTitle(diaryEntry.getEntryTitle());
        diaryEntryToAdd.setEntryContent(diaryEntry.getEntryContent());
        diaryEntryToAdd.setEntryDate(diaryEntry.getEntryDate().toString());
        diaryEntryToAdd.setUser(user);

        diaryEntryRepository.save(diaryEntryToAdd);
        return ResponseEntity.ok(mapper.map(diaryEntryToAdd, DiaryEntryResponseData.class));
    }
}