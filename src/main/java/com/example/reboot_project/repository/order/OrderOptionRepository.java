package com.example.reboot_project.repository.order;

import com.example.reboot_project.entity.order.OrderOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderOptionRepository extends JpaRepository<OrderOptionEntity, String> {
}
