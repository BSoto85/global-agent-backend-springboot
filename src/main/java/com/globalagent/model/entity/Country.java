package com.globalagent.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "countries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String flag;

    @Column(name = "country_code", length = 2)
    private String countryCode;

    @Column(length = 30)
    private String name;

    @Column(name = "language_code", length = 2)
    private String languageCode;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String silhouette;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<CaseFile> caseFiles = new ArrayList<>();
}
