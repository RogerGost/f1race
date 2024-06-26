package cat.uvic.teknos.f1race.repositories;

import java.util.Set;

public interface Repository <K, V> {
    void save(V model);
    void delete(V model);

    void update(V model);
    V get(K id);
    Set<V> getAll();
}
