package org.windai.domain.specification.shared;

import org.windai.domain.exception.GenericSpecificationExeception;

public abstract class AbstractSpecification<T> implements Specification<T> {

    public abstract boolean isSatisfiedBy(T t);
    
    public abstract void check(T t) throws GenericSpecificationExeception;
  
}
