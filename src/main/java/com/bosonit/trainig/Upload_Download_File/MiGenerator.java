package com.bosonit.trainig.Upload_Download_File;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;
import java.util.stream.Stream;

public class MiGenerator implements IdentifierGenerator {

    String prefijo;

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object obj) throws HibernateException {
        String query = String.format("select %s from %s",
                sharedSessionContractImplementor.getEntityPersister(obj.getClass().getName(), obj)
                        .getIdentifierPropertyName(),
                obj.getClass().getSimpleName());

        Stream<String> ids = sharedSessionContractImplementor.createQuery(query).stream();


        Long max = ids.map(o -> o.replace(obj.getClass().getSimpleName().toLowerCase() + "-", ""))
                .mapToLong(Long::parseLong)
                .max()
                .orElse(0L);

        return obj.getClass().getSimpleName().toLowerCase() + "-" + (max + 1);
    }
    @Override
    public void configure(Type type, Properties properties, ServiceRegistry serviceRegistry) throws MappingException {
        prefijo = properties.getProperty("prefix");
    }


}
