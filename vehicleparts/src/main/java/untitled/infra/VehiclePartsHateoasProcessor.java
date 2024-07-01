package untitled.infra;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import untitled.domain.*;

@Component
public class VehiclePartsHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<VehicleParts>> {

    @Override
    public EntityModel<VehicleParts> process(EntityModel<VehicleParts> model) {
        return model;
    }
}
