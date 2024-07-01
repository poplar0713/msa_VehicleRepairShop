package untitled.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import untitled.domain.*;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "receipts", path = "receipts")
public interface ReceiptRepository
    extends PagingAndSortingRepository<Receipt, Long> {}
