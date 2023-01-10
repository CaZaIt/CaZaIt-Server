package shop.cazait.global.common.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import shop.cazait.global.common.status.BaseStatus;

@MappedSuperclass
public abstract class baseEntity {

    @Enumerated(EnumType.STRING)
    private BaseStatus status;

    @CreatedDate
    private String createdAt;

    @LastModifiedDate
    private String updatedAt;

    /**
     * Execute before entity insert
     */
    @PrePersist
    public void onPrePersist(){
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.updatedAt = this.createdAt;
    }

    /**
     * Execute before entity update
     */
    @PreUpdate
    public void onPreUpdate(){
        this.updatedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
