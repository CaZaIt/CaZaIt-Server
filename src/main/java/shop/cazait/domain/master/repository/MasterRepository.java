package shop.cazait.domain.master.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shop.cazait.domain.master.entity.Master;
import shop.cazait.global.common.status.BaseStatus;

@Repository
public interface MasterRepository extends JpaRepository<Master, UUID> {

	Optional<Master> findMasterById(UUID id);

	Optional<Master> findMasterByAccountName(String id);


	Optional<Master> findMasterByNickname(String nickname);

	List<Master> findAllMasterById(Long id);

	List<Master> findMasterByStatus(BaseStatus status);

	Optional<Master> findById(UUID id);

	Optional<Master> findByPhoneNumber(String phoneNumber);
}
