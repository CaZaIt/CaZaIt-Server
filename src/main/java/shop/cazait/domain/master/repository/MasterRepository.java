package shop.cazait.domain.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.cazait.domain.master.entity.Master;

import java.util.List;
import java.util.Optional;

@Repository
public interface MasterRepository extends JpaRepository<Master, Integer> {

   Optional<Master> findMasterById(int id);

   Optional<Master> findMasterByEmail(String email);

    List<Master> findAllMasterById(Long id);
}
