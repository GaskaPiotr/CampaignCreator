package io.github.gaskapiotr.campaignapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "towns")
@Getter
@Setter
@NoArgsConstructor
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public Town(String name) {
        this.name = name;
    }
}
