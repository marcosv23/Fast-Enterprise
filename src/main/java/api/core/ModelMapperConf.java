package api.core;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConf {

	@Bean
	public ModelMapper createModelMapperInstance() {
		return new ModelMapper();
	}
}
