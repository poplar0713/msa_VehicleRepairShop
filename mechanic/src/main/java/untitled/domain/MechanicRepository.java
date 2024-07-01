package untitled.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import untitled.domain.*;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "mechanics", path = "mechanics")
public interface MechanicRepository
    extends PagingAndSortingRepository<Mechanic, Long> {}
