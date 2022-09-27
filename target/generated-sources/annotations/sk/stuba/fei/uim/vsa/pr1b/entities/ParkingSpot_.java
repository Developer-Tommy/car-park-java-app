package sk.stuba.fei.uim.vsa.pr1b.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sk.stuba.fei.uim.vsa.pr1b.entities.CarParkFloor;
import sk.stuba.fei.uim.vsa.pr1b.entities.CarType;
import sk.stuba.fei.uim.vsa.pr1b.entities.Reservation;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2022-05-21T11:06:14")
@StaticMetamodel(ParkingSpot.class)
public class ParkingSpot_ { 

    public static volatile SingularAttribute<ParkingSpot, CarParkFloor> carParkFloor;
    public static volatile SingularAttribute<ParkingSpot, CarType> carType;
    public static volatile CollectionAttribute<ParkingSpot, Reservation> reservations;
    public static volatile SingularAttribute<ParkingSpot, Long> id;
    public static volatile SingularAttribute<ParkingSpot, String> spotIdentifier;

}