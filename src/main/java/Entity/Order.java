package Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Data
public class Order {
    @Id
    private Long id;
    private int user_id;

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private double total_price;

    public Long getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public double getTotal_price() {
        return total_price;
    }

    public String getStatus() {
        return status;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    private String status;
    private int payment_id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    

}
