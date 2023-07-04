package shop.cazait.domain.master.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shop.cazait.domain.master.entity.Master;
import shop.cazait.global.common.status.BaseStatus;

@Repository
public interface MasterRepository extends JpaRepository<Master, Integer> {

	Optional<Master> findMasterById(long id);

	Optional<Master> findMasterByEmail(String email);

	Optional<Master> findMasterByNickname(String nickname);

	List<Master> findAllMasterById(Long id);

	List<Master> findMasterByStatus(BaseStatus status);

	Optional<Master> findById(Long id);
	Optional<Master> findById(UUID id);
}
