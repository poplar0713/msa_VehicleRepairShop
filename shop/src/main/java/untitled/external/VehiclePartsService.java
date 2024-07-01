package untitled.external;

import java.util.Date;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "vehicleparts", url = "${api.url.vehicleparts}")
public interface VehiclePartsService {
    @RequestMapping(method = RequestMethod.POST, path = "/vehicleParts")
    public void decreaseStock(@RequestBody VehicleParts vehicleParts);
}
