package com.teamten.til.tilog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.teamten.til.tiler.entity.TilerTemp;
import com.teamten.til.tilog.entity.Tilog;

public interface TilogRepository extends JpaRepository<Tilog, Long> {
	List<Tilog> findAllByTilerAndRegYmdStartingWith(TilerTemp tiler, String regYm);

	Optional<Tilog> findByTilerAndRegYmd(TilerTemp tiler, String regYmd);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Tilog t SET t.likes = t.likes + 1 WHERE t.id = :id")
	int incrementLikes(Long id);

	@Query("UPDATE Tilog t SET t.likes = t.likes - 1 WHERE t.id = :id")
	int decrementLikes(Long id);

	List<Tilog> findAllByTilerAndRegYmdGreaterThanEqualAndRegYmdLessThanEqualOrderByRegYmdAsc(TilerTemp tiler,
		String start,
		String end);
}
