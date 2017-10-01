/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package u2f.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import u2f.model.RegisterData;

/**
 *
 * @author ENESTORO
 */
@Repository("registerDataRepository")
public interface RegisterDataRepository extends JpaRepository<RegisterData, Long> {

    RegisterData findByRequestId(String requestId);
}
