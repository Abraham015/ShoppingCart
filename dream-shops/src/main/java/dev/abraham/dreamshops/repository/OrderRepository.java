package dev.abraham.dreamshops.repository;

import dev.abraham.dreamshops.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
