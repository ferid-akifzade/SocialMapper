package org.socialmapper.repos;

import org.socialmapper.libs.Target;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetRepo extends CrudRepository<Target, Long> {
}
