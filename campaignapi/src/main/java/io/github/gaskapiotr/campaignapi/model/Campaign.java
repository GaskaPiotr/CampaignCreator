package io.github.gaskapiotr.campaignapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "campaigns")
@NoArgsConstructor
@Setter
@Getter
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "campaign_keywords",
            joinColumns = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id")
    )
    private Set<Keyword> keywords = new HashSet<>();

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal bidAmount;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal campaignFund;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CampaignStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "town_id")
    private Town town;

    @Column(nullable = false)
    private Double radiusKm;

    public String getTownName() {
        return this.town != null ? this.town.getName() : null;
    }
}
