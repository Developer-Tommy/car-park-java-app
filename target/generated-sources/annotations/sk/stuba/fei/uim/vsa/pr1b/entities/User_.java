package sk.stuba.fei.uim.vsa.pr1b.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sk.stuba.fei.uim.vsa.pr1b.entities.Car;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2022-05-21T11:06:14")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile CollectionAttribute<User, Car> cars;
    public static volatile SingularAttribute<User, String> firstname;
    public static volatile SingularAttribute<User, Long> id;
    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, String> lastname;

}