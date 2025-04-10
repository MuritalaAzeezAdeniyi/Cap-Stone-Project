package africa.semicolon.koonnkt.data.repository;

import africa.semicolon.koonnkt.data.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepo extends JpaRepository<Report, Long> {
}
