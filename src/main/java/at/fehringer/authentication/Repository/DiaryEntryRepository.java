package at.fehringer.authentication.Repository;

import at.fehringer.authentication.Repository.model.DiaryEntry;
import org.springframework.data.repository.CrudRepository;

public interface DiaryEntryRepository extends CrudRepository<DiaryEntry, Integer> {
}
