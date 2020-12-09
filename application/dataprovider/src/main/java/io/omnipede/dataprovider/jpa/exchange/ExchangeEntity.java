package io.omnipede.dataprovider.jpa.exchange;

import io.omnipede.dataprovider.jpa.broadbandaccessdevice.BroadbandAccessDeviceEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="t_exchange")
@Getter
@Setter
@NoArgsConstructor
public class ExchangeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "postcode", length = 10, nullable = false)
    private String postcode;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createAt;

    @OneToMany(mappedBy = "exchangeEntity")
    private List<BroadbandAccessDeviceEntity> broadbandAccessDeviceEntities = new ArrayList<>();
}
