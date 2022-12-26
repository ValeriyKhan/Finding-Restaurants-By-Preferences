package restaurant.app.map.entity;

import lombok.AccessLevel;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table
@Getter
public class GoogleMapSecretEntity {
    @Getter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String secretKey;
}
