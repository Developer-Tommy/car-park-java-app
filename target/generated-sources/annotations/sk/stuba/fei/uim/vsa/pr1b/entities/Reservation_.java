package sk.stuba.fei.uim.vsa.pr1b.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sk.stuba.fei.uim.vsa.pr1b.entities.Car;
import sk.stuba.fei.uim.vsa.pr1b.entities.ParkingSpot;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2022-05-21T11:06:14")
@StaticMetamodel(Reservation.class)
public class Reservation_ { 

    public static volatile SingularAttribute<Reservation, Car> car;
    public static volatile SingularAttribute<Reservation, ParkingSpot> parkingSpot;
    public static volatile SingularAttribute<Reservation, Integer> price;
    public static volatile SingularAttribute<Reservation, Date> startTime;
    public static volatile SingularAttribute<Reservation, Long> id;
    public static volatile SingularAttribute<Reservation, Date> endTime;

}