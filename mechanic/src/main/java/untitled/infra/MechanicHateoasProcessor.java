package untitled.infra;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import untitled.domain.*;

@Component
public class MechanicHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Mechanic>> {

    @Override
    public EntityModel<Mechanic> process(EntityModel<Mechanic> model) {
        return model;
    }
}
