package com.example.userservice.repository;

import com.example.userservice.entity.DeviceEntity;
import com.example.common.entity.enums.user.DeviceTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, String> {
    Optional<DeviceEntity> findByUserIdAndType(String userId, DeviceTypeEnum type);
    List<DeviceEntity> findAllByUserId(String userId);
}
