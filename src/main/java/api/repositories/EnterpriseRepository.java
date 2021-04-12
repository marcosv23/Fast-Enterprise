package api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api.model.domain.Enterprise;

@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise,Long> {
	
	public Enterprise findBycnpj(String cnpj);
	
	public List<Enterprise> findByUserId(Long user_id);
	
	public List<Enterprise> findByEnterpriseName(String enterpriseName);
	
	public List<Enterprise> findByEnterpriseNameContaining(final String fullname);
	
	
}
