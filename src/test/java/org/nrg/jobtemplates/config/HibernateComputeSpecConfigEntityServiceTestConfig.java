package org.nrg.jobtemplates.config;

import org.hibernate.SessionFactory;
import org.nrg.jobtemplates.entities.*;
import org.nrg.jobtemplates.repositories.ComputeSpecConfigDao;
import org.nrg.jobtemplates.repositories.ConstraintConfigDao;
import org.nrg.jobtemplates.repositories.HardwareConfigDao;
import org.nrg.jobtemplates.services.impl.HibernateComputeSpecConfigEntityService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.support.ResourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Import({HibernateConfig.class})
public class HibernateComputeSpecConfigEntityServiceTestConfig {

    @Bean
    public HibernateComputeSpecConfigEntityService hibernateComputeSpecConfigEntityServiceTest(@Qualifier("computeSpecConfigDaoImpl") final ComputeSpecConfigDao computeSpecConfigDaoImpl,
                                                                                               @Qualifier("hardwareConfigDaoImpl") final HardwareConfigDao hardwareConfigDaoImpl) {
        return new HibernateComputeSpecConfigEntityService(
                computeSpecConfigDaoImpl,
                hardwareConfigDaoImpl);
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(final DataSource dataSource, @Qualifier("hibernateProperties") final Properties properties) {
        final LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setHibernateProperties(properties);
        bean.setAnnotatedClasses(
                ConstraintConfigEntity.class,
                ConstraintEntity.class,
                ConstraintScopeEntity.class,
                ComputeSpecConfigEntity.class,
                ComputeSpecEntity.class,
                ComputeSpecScopeEntity.class,
                ComputeSpecHardwareOptionsEntity.class,
                HardwareConfigEntity.class,
                HardwareEntity.class,
                HardwareScopeEntity.class,
                HardwareConstraintEntity.class,
                EnvironmentVariableEntity.class,
                MountEntity.class,
                GenericResourceEntity.class
        );
        return bean;
    }

    @Bean
    public ResourceTransactionManager transactionManager(final SessionFactory sessionFactory) throws Exception {
        return new HibernateTransactionManager(sessionFactory);
    }

    @Bean
    public ConstraintConfigDao constraintConfigDao(final SessionFactory sessionFactory) {
        return new ConstraintConfigDao(sessionFactory);
    }

    @Bean
    @Qualifier("hardwareConfigDaoImpl")
    public HardwareConfigDao hardwareConfigDaoImpl(final SessionFactory sessionFactory) {
        return new HardwareConfigDao(sessionFactory);
    }

    @Bean
    @Qualifier("computeSpecConfigDaoImpl")
    public ComputeSpecConfigDao computeSpecConfigDaoImpl(final SessionFactory sessionFactory,
                                                         final @Qualifier("hardwareConfigDaoImpl") HardwareConfigDao hardwareConfigDaoImpl) {
        return new ComputeSpecConfigDao(sessionFactory, hardwareConfigDaoImpl);
    }

}