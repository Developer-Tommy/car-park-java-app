package sk.stuba.fei.uim.vsa.pr1b.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sk.stuba.fei.uim.vsa.pr1b.entities.Car;
import sk.stuba.fei.uim.vsa.pr1b.entities.ParkingSpot;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2022-05-21T11:06:14")
@StaticMetamodel(CarType.class)
public class CarType_ { 

    public static volatile CollectionAttribute<CarType, Car> cars;
    public static volatile SingularAttribute<CarType, String> name;
    public static volatile CollectionAttribute<CarType, ParkingSpot> parkingSpots;
    public static volatile SingularAttribute<CarType, Long> id;

}