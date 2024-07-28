package com.uptime.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MONITOR")
@Entity
@Builder
@ToString
public class Monitor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;
    private String url;
    @ManyToOne
    private UserInfo userInfo;
    @Enumerated(EnumType.STRING)
    private CheckStatus currentStatus;
    @Enumerated(EnumType.STRING)
    private CheckFrequency checkFrequency;
    private boolean isPaused=false;

}
