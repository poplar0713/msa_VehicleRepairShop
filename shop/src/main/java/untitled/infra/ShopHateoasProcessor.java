package untitled.infra;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import untitled.domain.*;

@Component
public class ShopHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Shop>> {

    @Override
    public EntityModel<Shop> process(EntityModel<Shop> model) {
        return model;
    }
}
