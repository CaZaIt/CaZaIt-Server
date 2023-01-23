package shop.cazait.domain.master.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shop.cazait.domain.master.entity.Master;

@Repository
public interface MasterRepository extends JpaRepository<Master, Integer> {

	Optional<Master> findMasterById(long id);

	Optional<Master> findMasterByEmail(String email);

	List<Master> findAllMasterById(Long id);
}
