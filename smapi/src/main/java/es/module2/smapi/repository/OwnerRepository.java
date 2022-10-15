package es.module2.smapi.repository;


@Repository
public interface OwnerRepository extends OwnerRepository<Owner, Long> {
    List<Owner> findByName(String name);
    List<Owner> findByUsername(String username);
}