package io.omnipede.springbootrestapiboilerplate.entrypoint.broadbandaccessdevice;

import io.omnipede.core.entity.BroadbandAccessDevice;
import io.omnipede.core.usecase.broadbandaccessdevice.getdetails.DeviceNotFoundException;
import io.omnipede.core.usecase.broadbandaccessdevice.getdetails.GetBroadbandAccessDeviceDetailsUseCase;
import io.omnipede.core.usecase.broadbandaccessdevice.getdetails.GetDeviceDetails;
import io.omnipede.dataprovider.database.broadbandaccessdevice.BroadbandAccessDeviceDatabaseDataProvider;
import io.omnipede.dataprovider.jpa.broadbandaccessdevice.BroadbandAccessDeviceRepository;
import io.omnipede.dataprovider.jpa.broadbandaccessdevice.ExchangeRepository;
import io.omnipede.springbootrestapiboilerplate.global.exception.BusinessException;
import io.omnipede.springbootrestapiboilerplate.global.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BroadbandAccessDeviceController {

    private GetBroadbandAccessDeviceDetailsUseCase getBroadbandAccessDeviceDetailsUseCase;

    private static final Logger logger = LoggerFactory.getLogger(BroadbandAccessDeviceController.class);

    @Autowired
    public BroadbandAccessDeviceController(BroadbandAccessDeviceRepository broadbandAccessDeviceRepository, ExchangeRepository exchangeRepository) {
        GetDeviceDetails getDeviceDetails
                = new BroadbandAccessDeviceDatabaseDataProvider(broadbandAccessDeviceRepository, exchangeRepository);
        this.getBroadbandAccessDeviceDetailsUseCase = new GetBroadbandAccessDeviceDetailsUseCase(getDeviceDetails);
    }

    @GetMapping(value="/broadbandaccessdevice/{hostname}", headers="accept=application/json")
    public BroadbandAccessDeviceDTO getDetails(@PathVariable String hostname) {
        logger.debug(hostname);
        try {
            BroadbandAccessDevice broadbandAccessDevice = getBroadbandAccessDeviceDetailsUseCase.getDeviceDetails(hostname);
            return toDTO(broadbandAccessDevice);
        } catch (DeviceNotFoundException e) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_EXISTS);
        }
    }

    private BroadbandAccessDeviceDTO toDTO(BroadbandAccessDevice device) {
        String exchangeCode = device.getExchange().getCode();
        String hostname = device.getHostname();
        String seriaNumber = device.getSerialNumber();
        String deviceType = device.getDeviceType().name();

        return new BroadbandAccessDeviceDTO(exchangeCode, hostname, seriaNumber, deviceType);
    }
}
