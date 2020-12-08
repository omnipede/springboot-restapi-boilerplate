package io.omnipede.dataprovider.jpa.broadbandaccessdevice;

import io.omnipede.springbootrestapiboilerplate.core.entity.DeviceType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_broadband_access_device")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BroadbandAccessDeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "hostname", length=100, nullable = false)
    private String hostname;

    @Column(name = "serial_number", length=100, nullable = false)
    private String serialNumber;

    @Column(name = "available_ports", nullable = false)
    @Builder.Default
    private Integer availablePorts = 100;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createAt;

    @ManyToOne
    @JoinColumn(name = "exchange_id")
    private ExchangeEntity exchangeEntity;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private DeviceType deviceType = DeviceType.ADSL;
}
