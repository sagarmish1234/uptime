package com.uptime.model;


import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MONITOR")
@Entity
@Builder
public class Monitor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;
    private String url;
    @ManyToOne
    private UserInfo userInfo;
    @Enumerated(EnumType.STRING)
    private CheckFrequency checkFrequency;

}
