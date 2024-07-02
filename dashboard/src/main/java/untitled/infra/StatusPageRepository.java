package untitled.infra;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import untitled.domain.StatusPage;

@RepositoryRestResource(
    collectionResourceRel = "statusPages",
    path = "statusPages"
)
public interface StatusPageRepository extends PagingAndSortingRepository<StatusPage, Long> {

    List<StatusPage> findByReceiptId(Long receiptId);
}
