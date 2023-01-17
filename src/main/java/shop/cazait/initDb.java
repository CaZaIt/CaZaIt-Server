package shop.cazait;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.master.entity.Master;
import shop.cazait.domain.user.entity.User;

@Component
@RequiredArgsConstructor
public class initDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit() {
            User user = User.builder()
                    .email("user@gmail.com")
                    .password("user")
                    .nickname("user")
                    .build();
            em.persist(user);

            Master master = Master.builder()
                    .email("master@gmail.com")
                    .password("master")
                    .nickname("master")
                    .build();
            em.persist(master);

            Cafe cafe = Cafe.builder()
                    .name("cazait1")
                    .location("서울시 광진구")
                    .longitude(113.667436)
                    .latitude(33.612843)
                    .build();
            em.persist(cafe);
        }
    }
}
