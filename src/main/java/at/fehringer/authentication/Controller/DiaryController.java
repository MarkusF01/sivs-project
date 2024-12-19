package at.fehringer.authentication.Controller;

import at.fehringer.authentication.Controller.dto.DiaryEntryResponse;
import at.fehringer.authentication.Controller.dto.CreateDiaryEntryRequest;
import at.fehringer.authentication.Repository.DiaryEntryRepository;
import at.fehringer.authentication.Repository.UserRepository;
import at.fehringer.authentication.Repository.model.DiaryEntry;
import at.fehringer.authentication.Repository.model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users/{username}/diary-entries")
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

    @GetMapping
    public ResponseEntity<?> getDiaryEntries(@PathVariable String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = userOptional.get();
        List<DiaryEntry> diaryEntries = user.getDiaryEntries();

        Type listType = new TypeToken<List<DiaryEntryResponse>>() {}.getType();
        List<DiaryEntryResponse> diaryEntryDtoList = mapper.map(diaryEntries, listType);
        return ResponseEntity.ok(diaryEntryDtoList);
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<?> deleteDiaryEntry(@PathVariable Integer diaryId) {
        Optional<DiaryEntry> diaryEntryOptional = diaryEntryRepository.findById(diaryId);

        if (diaryEntryOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Diary entry not found");
        }

        diaryEntryRepository.deleteById(diaryId);
        return ResponseEntity.ok("Diary entry deleted successfully");
    }

    @PostMapping
    public ResponseEntity<?> createDiaryEntry(@PathVariable String username, @RequestBody CreateDiaryEntryRequest diaryEntry) {
        if (username == null || diaryEntry == null) {
            return ResponseEntity.badRequest().body("Username and entry content are required");
        }
        Optional<User> userOptional = userRepository.findByUsername(username);

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
        return ResponseEntity.ok(mapper.map(diaryEntryToAdd, DiaryEntryResponse.class));
    }
}