package sk.stuba.fei.uim.vsa.pr1b.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sk.stuba.fei.uim.vsa.pr1b.entities.CarPark;
import sk.stuba.fei.uim.vsa.pr1b.entities.CarParkFloorId;
import sk.stuba.fei.uim.vsa.pr1b.entities.ParkingSpot;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2022-05-21T11:06:14")
@StaticMetamodel(CarParkFloor.class)
public class CarParkFloor_ { 

    public static volatile SingularAttribute<CarParkFloor, CarParkFloorId> floorIdentifier;
    public static volatile SingularAttribute<CarParkFloor, CarPark> carPark;
    public static volatile CollectionAttribute<CarParkFloor, ParkingSpot> parkingSpots;

}