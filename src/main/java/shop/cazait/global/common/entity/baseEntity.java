package shop.cazait.global.common.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import shop.cazait.global.common.status.BaseStatus;

@MappedSuperclass
public abstract class baseEntity {

    @Enumerated(EnumType.STRING)
    private BaseStatus status;

}
