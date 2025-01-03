package at.fehringer.authentication.repository;

import at.fehringer.authentication.repository.model.DiaryEntry;
import org.springframework.data.repository.CrudRepository;

public interface DiaryEntryRepository extends CrudRepository<DiaryEntry, Integer> {
}
