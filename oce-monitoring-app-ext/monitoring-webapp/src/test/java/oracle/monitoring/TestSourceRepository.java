/**
 * 
 */
package oracle.monitoring;

import io.monitoring.domain.Source;
import io.monitoring.repository.SourceRepository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author vipink
 *
 */
@SpringBootTest
public class TestSourceRepository {

	/**
	 * 
	 */
	public TestSourceRepository() {
		// TODO Auto-generated constructor stub
	}

    @Autowired
    private SourceRepository sourceRepository;
    
    @Test
    public void whenFindingCustomerById_thenCorrect() {
    	sourceRepository.save(new Source(10L, "oracle.monitiroing.source.OCEWlsMetricSource", "{}","WLS Exporter","Scrapped from weblogic"));
        //assertThat(sourceRepository.findById(10L)).isInstanceOf(Optional.class);
    }
    
    @Test
    public void whenFindingAllCustomers_thenCorrect() {
    	sourceRepository.save(new Source(10L, "oracle.monitiroing.source.OCEWlsMetricSource", "{}","WLS Exporter","Scrapped from weblogic"));
    	sourceRepository.save(new Source(20L, "oracle.monitiroing.source.OCEWlsMetricSource2", "{}","WLS Exporter 1","Scrapped from weblogic"));
        //assertThat(customerRepository.findAll()).isInstanceOf(List.class);
    }
}
