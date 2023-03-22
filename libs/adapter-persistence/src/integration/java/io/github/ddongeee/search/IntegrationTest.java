package io.github.ddongeee.search;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import io.github.ddongeee.search.configure.PersistenceJpaConfig;
import io.github.ddongeee.search.persistence.PersistenceModule;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
@ComponentScan(basePackageClasses = {PersistenceModule.class})
@ContextConfiguration(classes = {PersistenceJpaConfig.class})
public abstract class IntegrationTest {
}
