/**
 * 
 */
package io.monitoring.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.monitoring.domain.TimeSeriesMetric;

import java.util.Optional;

/**
 * @author vipink 
 *
 */
@Repository
public interface TimeSeriesMetricRepository extends CrudRepository<TimeSeriesMetric, Long> {
    
    @Query(value="select distinct key from time_series_metric offset :offset limit :limit", nativeQuery = true)
    public Iterable<String> findAllKey(@Param("offset") int offset,  @Param("limit") int limit);
    
	@Query(value="select * from time_series_metric where timestamp >=:timestamp order by timestamp desc offset :offset limit :limit", nativeQuery = true)
	public Iterable<TimeSeriesMetric> listByTimestamp(@Param("timestamp") Long timestamp, @Param("offset") int offset,  @Param("limit") int limit);
	
	@Query(value="select * from time_series_metric where key =:key and timestamp >=:timestamp order by timestamp desc offset :offset limit :limit", nativeQuery = true)
	public Iterable<TimeSeriesMetric> listByKeyAndTimestamp(@Param("key") String key, @Param("timestamp") Long timestamp, @Param("offset") int offset,  @Param("limit") int limit);

	@Query(value="select avg(value) as avg_value from time_series_metric where key =:key and timestamp >=:timestamp", nativeQuery = true)
	public Optional<Float> findAverageByKeyAndTimestamp(@Param("key") String key, @Param("timestamp") Long timestamp);

	@Query(value="select max(value) as avg_value from time_series_metric where key =:key and timestamp >=:timestamp", nativeQuery = true)
	public Optional<Float> findMaxByKeyAndTimestamp(@Param("key") String key, @Param("timestamp") Long timestamp);

	@Query(value="select min(value) as avg_value from time_series_metric where key =:key and timestamp >=:timestamp", nativeQuery = true)
	public Optional<Float> findMinByKeyAndTimestamp(@Param("key") String key, @Param("timestamp") Long timestamp);

	@Query(value="select sum(value) as avg_value from time_series_metric where key =:key and timestamp >=:timestamp", nativeQuery = true)
	public Optional<Float> findSumByKeyAndTimestamp(@Param("key") String key, @Param("timestamp") Long timestamp);


	@Query(value="select avg(value) as value from time_series_metric where key =:key and timestamp >=:timestamp having avg(value) > :value", nativeQuery = true)
	public Optional<Float> findAverageByKeyAndValueGreaterThan(@Param("key") String key, @Param("value") Float value, @Param("timestamp") Long timestamp);

	@Query(value="select avg(value) as value from time_series_metric where key =:key and timestamp >=:timestamp having avg(value) < :value", nativeQuery = true)
	public Optional<Float> findAverageByKeyAndValueLessThan(@Param("key") String key, @Param("value") Float value, @Param("timestamp") Long timestamp);

	@Query(value="select avg(value) as value from time_series_metric where key =:key and timestamp >=:timestamp having avg(value) = :value", nativeQuery = true)
	public Optional<Float> findAverageByKeyAndValueEquals(@Param("key") String key, @Param("value") Float value, @Param("timestamp") Long timestamp);

	@Query(value="select max(value) as value from time_series_metric where key =:key and timestamp >=:timestamp having max(value) > :value", nativeQuery = true)
	public Optional<Float> findMaxByKeyAndValueGreaterThan(@Param("key") String key, @Param("value") Float value, @Param("timestamp") Long timestamp);

	@Query(value="select max(value) as value from time_series_metric where key =:key and timestamp >=:timestamp having max(value) < :value", nativeQuery = true)
	public Optional<Float> findMaxByKeyAndValueLessThan(@Param("key") String key, @Param("value") Float value, @Param("timestamp") Long timestamp);

	@Query(value="select max(value) as value from time_series_metric where key =:key and timestamp >=:timestamp having max(value) = :value", nativeQuery = true)
	public Optional<Float> findMaxByKeyAndValueEquals(@Param("key") String key, @Param("value") Float value, @Param("timestamp") Long timestamp);

	@Query(value="select min(value) as value from time_series_metric where key =:key and timestamp >=:timestamp having min(value) > :value", nativeQuery = true)
	public Optional<Float> findMinByKeyAndValueGreaterThan(@Param("key") String key, @Param("value") Float value, @Param("timestamp") Long timestamp);

	@Query(value="select min(value) as value from time_series_metric where key =:key and timestamp >=:timestamp having min(value) < :value", nativeQuery = true)
	public Optional<Float> findMinByKeyAndValueLessThan(@Param("key") String key, @Param("value") Float value, @Param("timestamp") Long timestamp);

	@Query(value="select min(value) as value from time_series_metric where key =:key and timestamp >=:timestamp having min(value) = :value", nativeQuery = true)
	public Optional<Float> findMinByKeyAndValueEquals(@Param("key") String key, @Param("value") Float value, @Param("timestamp") Long timestamp);

	@Query(value="select sum(value) as value from time_series_metric where key =:key and timestamp >=:timestamp having sum(value) > :value", nativeQuery = true)
	public Optional<Float> findSumByKeyAndValueGreaterThan(@Param("key") String key, @Param("value") Float value, @Param("timestamp") Long timestamp);

	@Query(value="select sum(value) as value from time_series_metric where key =:key and timestamp >=:timestamp having sum(value) < :value", nativeQuery = true)
	public Optional<Float> findSumByKeyAndValueLessThan(@Param("key") String key, @Param("value") Float value, @Param("timestamp") Long timestamp);

	@Query(value="select sum(value) as value from time_series_metric where key =:key and timestamp >=:timestamp having sum(value) = :value", nativeQuery = true)
	public Optional<Float> findSumByKeyAndValueEquals(@Param("key") String key, @Param("value") Float value, @Param("timestamp") Long timestamp);
}
