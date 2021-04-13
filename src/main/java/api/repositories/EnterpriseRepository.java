package api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api.model.domain.Enterprise;

@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {

	public Optional<Enterprise> findBycnpj(String cnpj);

	public Optional<List<Enterprise>> findByUserId(Long user_id);

	public Optional<Enterprise> findByEnterpriseName(String enterpriseName);

	public Optional<List<Enterprise>> findByEnterpriseNameContaining(final String fullname);

	public Optional<Enterprise> findByCompanyEmail(String companyEmail);

}
