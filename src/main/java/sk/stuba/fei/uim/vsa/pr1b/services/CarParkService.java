package sk.stuba.fei.uim.vsa.pr1b.services;

import sk.stuba.fei.uim.vsa.pr1b.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class CarParkService extends AbstractCarParkService{

    private final EntityManager em;

    public CarParkService() {
        this.em = emf.createEntityManager();
    }

    @Override
    public Object createCarPark(String name, String address, Integer pricePerHour) {
        CarPark carPark = new CarPark(name, address, pricePerHour);
        carPark.setCarParkFloors(Collections.emptyList());
        try {
            em.getTransaction().begin();
            em.persist(carPark);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            return null;
        }
        return carPark;
    }

    @Override
    public Object getCarPark(Long carParkId) {
        try {
            CarPark carParkResult = em.find(CarPark.class, carParkId);
            if (carParkResult != null) {
                return carParkResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            return null;
        }
        return null;
    }

    @Override
    public Object getCarPark(String carParkName) {
        TypedQuery<CarPark> query = em.createNamedQuery("CarPark.findByName",CarPark.class);
        query.setParameter("name", carParkName);
        try {
            CarPark carParkResult = query.getSingleResult();
            if (carParkResult != null) {
                return carParkResult;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public List<Object> getCarParks() {
        TypedQuery<CarPark> query = em.createNamedQuery("CarPark.findAll", CarPark.class);
        try {
            List <CarPark> carParkResult = query.getResultList();
            if (!(carParkResult.isEmpty())) {
                return new ArrayList<>(carParkResult);
            }
        } catch (Exception e) {
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }

    @Override
    public Object updateCarPark(Object carPark) {
        CarPark carParkUpdated = (CarPark) carPark;
        CarPark carParkResult = (CarPark) getCarPark(carParkUpdated.getId());
        try {
            if (carParkResult != null) {
                carParkUpdated.setCarParkFloors(carParkResult.getCarParkFloors());
                if (carParkUpdated.getName() == null)
                    carParkUpdated.setName(carParkResult.getName());
                if (carParkUpdated.getAddress() == null)
                    carParkUpdated.setAddress(carParkResult.getAddress());
                if (carParkUpdated.getPricePerHour() == null)
                    carParkUpdated.setPricePerHour(carParkResult.getPricePerHour());

                em.getTransaction().begin();
                em.merge(carParkUpdated);
                em.getTransaction().commit();
                return carParkUpdated;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public Object deleteCarPark(Long carParkId) {
        CarPark carPark = (CarPark) getCarPark(carParkId);
        try {
            if (carPark != null) {
                for (CarParkFloor carParkFloor : carPark.getCarParkFloors()) {
                    deleteCarParkFloor(carParkFloor.getFloorIdentifier().getCarParkId(), carParkFloor.getFloorIdentifier().getFloorIdentifierId());
                }
                em.getTransaction().begin();
                em.merge(carPark);
                em.remove(carPark);
                em.getTransaction().commit();
                return carPark;
            }
        }
        catch (Exception e){
            return null;
        }
        return null;
    }

    @Override
    public Object createCarParkFloor(Long carParkId, String floorIdentifier) {
        CarParkFloorId carParkFloorId = new CarParkFloorId(carParkId,floorIdentifier);
        CarParkFloor carParkFloor = new CarParkFloor(carParkFloorId, (CarPark) getCarPark(carParkId));
        try {
            carParkFloor.setCarPark((CarPark) getCarPark(carParkId));
            if (carParkFloor.getCarPark() != null){
                em.getTransaction().begin();
                em.persist(carParkFloor);
                em.getTransaction().commit();
                return carParkFloor;
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }

    @Override
    public Object getCarParkFloor(Long carParkId, String floorIdentifier) {
        CarParkFloorId carParkFloorId = new CarParkFloorId(carParkId,floorIdentifier);
        try {
            CarParkFloor carParkFloorResult = em.find(CarParkFloor.class, carParkFloorId);
            if (carParkFloorResult != null) {
                return carParkFloorResult;
            }

        } catch (Exception e) {
            return null;
        }

        return null;
    }

    @Override
    public Object getCarParkFloor(Long carParkFloorId) {
        return null;
    }

    @Override
    public List<Object> getCarParkFloors(Long carParkId) {
        TypedQuery<CarParkFloor> query = em.createNamedQuery("CarParkFloor.findByFloorIdentifier_CarParkId",CarParkFloor.class);
        query.setParameter("carParkId", carParkId);
        try {
            List <CarParkFloor> carParkFloorResult = query.getResultList();
            if (!(carParkFloorResult.isEmpty())) {
                return new ArrayList<>(carParkFloorResult);
            }
        } catch (Exception e) {
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }

    @Override
    public Object updateCarParkFloor(Object carParkFloor) {
        return null;
    }

    @Override
    public Object deleteCarParkFloor(Long carParkId, String floorIdentifier) {
        CarParkFloor carParkFloorResult = (CarParkFloor) getCarParkFloor(carParkId, floorIdentifier);
        try {
            if (carParkFloorResult != null) {
                for (ParkingSpot parkingSpot : carParkFloorResult.getParkingSpots()){
                    deleteParkingSpot(parkingSpot.getId());
                }
                em.getTransaction().begin();
                em.merge(carParkFloorResult);
                em.remove(carParkFloorResult);
                em.getTransaction().commit();
                return carParkFloorResult;
            }

        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public Object deleteCarParkFloor(Long carParkFloorId) {
        return null;
    }

    @Override
    public Object createParkingSpot(Long carParkId, String floorIdentifier, String spotIdentifier) {
        CarParkFloor carParkFloorResult = (CarParkFloor) getCarParkFloor(carParkId, floorIdentifier);
        CarType carType = (CarType) getCarType("Gasoline");
        try {
            if (carParkFloorResult != null) {
                if (carType == null){
                    createCarType("Gasoline");
                    carType = (CarType) getCarType("Gasoline");
                }
                ParkingSpot parkingSpot = new ParkingSpot(spotIdentifier, carParkFloorResult, carType);
                em.getTransaction().begin();
                em.persist(parkingSpot);
                em.getTransaction().commit();
                return parkingSpot;
            }
        }
        catch (Exception e){
            return null;
        }
        return null;
    }

    @Override
    public Object getParkingSpot(Long parkingSpotId) {
        try {
            ParkingSpot parkingSpotResult = em.find(ParkingSpot.class, parkingSpotId);
            if (parkingSpotResult != null) {
                return parkingSpotResult;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public List<Object> getParkingSpots(Long carParkId, String floorIdentifier) {
        CarParkFloorId carParkFloorId = new CarParkFloorId(carParkId,floorIdentifier);
        TypedQuery<ParkingSpot> query = em.createNamedQuery("ParkingSpot.findParkingSpotByFloorIdentifier", ParkingSpot.class);
        query.setParameter("floorIdentifier", carParkFloorId);
        try {
            List <ParkingSpot> parkingSpotsResult = query.getResultList();
            if (!(parkingSpotsResult.isEmpty())) {
                return new ArrayList<>(parkingSpotsResult);
            }
        } catch (Exception e) {
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }

    @Override
    public Map<String, List<Object>> getParkingSpots(Long carParkId) {
        Map<String, List<Object>> listToMap = new HashMap<>();
        TypedQuery<ParkingSpot> query = em.createNamedQuery("ParkingSpot.findParkingSpotByCarParkId", ParkingSpot.class);
        query.setParameter("carParkId", carParkId);
        try {
            List <ParkingSpot> parkingSpotsResult = query.getResultList();
            if (!(parkingSpotsResult.isEmpty())) {
                return getStringListMap(listToMap, parkingSpotsResult);
            }
        } catch (Exception e) {
            return Collections.emptyMap();
        }
        return Collections.emptyMap();
    }

    @Override
    public Map<String, List<Object>> getAvailableParkingSpots(String carParkName) {
        Map<String, List<Object>> listToMap = new HashMap<>();
        TypedQuery<ParkingSpot> queryName = em.createNamedQuery("ParkingSpot.findParkingSpotByCarParkName", ParkingSpot.class);
        queryName.setParameter("name", carParkName);
        TypedQuery<ParkingSpot> queryOccupied = em.createNamedQuery("ParkingSpot.findAllOccupiedParkingSpots", ParkingSpot.class);
        queryOccupied.setParameter("name", carParkName);
        try {
            List<ParkingSpot> parkingSpotsResult = queryName.getResultList();
            List<ParkingSpot> occupiedSpots = queryOccupied.getResultList();
            if (!(parkingSpotsResult.isEmpty())) {
                parkingSpotsResult.removeAll(occupiedSpots);
                return getStringListMap(listToMap, parkingSpotsResult);
            }
        } catch (Exception e) {
            return Collections.emptyMap();
        }

        return Collections.emptyMap();
    }

    @Override
    public Map<String, List<Object>> getOccupiedParkingSpots(String carParkName) {
        Map<String, List<Object>> listToMap = new HashMap<>();
        TypedQuery<ParkingSpot> query = em.createNamedQuery("ParkingSpot.findAllOccupiedParkingSpots", ParkingSpot.class);
        query.setParameter("name", carParkName);
        try {
            List<ParkingSpot> occupiedSpots = query.getResultList();
            if (!(occupiedSpots.isEmpty())) {
                return getStringListMap(listToMap, occupiedSpots);
            }
        } catch (Exception e) {
            return Collections.emptyMap();
        }
        return Collections.emptyMap();
    }

    private Map<String, List<Object>> getStringListMap(Map<String, List<Object>> listToMapResult, List<ParkingSpot> parkingSpotsResult) {
        for (ParkingSpot parkingSpot: parkingSpotsResult){
            if(!listToMapResult.containsKey(parkingSpot.getCarParkFloor().getFloorIdentifier().getFloorIdentifierId())){
                listToMapResult.put(parkingSpot.getCarParkFloor().getFloorIdentifier().getFloorIdentifierId(), new ArrayList<>());
            }
            listToMapResult.get(parkingSpot.getCarParkFloor().getFloorIdentifier().getFloorIdentifierId()).add(parkingSpot);
        }
        return listToMapResult;
    }

    @Override
    public Object updateParkingSpot(Object parkingSpot) {
        ParkingSpot newParkingSpot = (ParkingSpot) parkingSpot;
        ParkingSpot parkingSpotResult = (ParkingSpot) getParkingSpot(newParkingSpot.getId());
        CarParkFloor carParkFloor = (CarParkFloor) getCarParkFloor(newParkingSpot.getCarParkFloor().getFloorIdentifier().getCarParkId(), newParkingSpot.getCarParkFloor().getFloorIdentifier().getFloorIdentifierId());
        CarType carType = (CarType) getCarType(newParkingSpot.getCarType().getId());
        try {
            if (parkingSpotResult != null) {
                newParkingSpot.setReservations(parkingSpotResult.getReservations());
                if (newParkingSpot.getSpotIdentifier() == null)
                    newParkingSpot.setSpotIdentifier(parkingSpotResult.getSpotIdentifier());
                if (newParkingSpot.getCarParkFloor() == null)
                    newParkingSpot.setCarParkFloor(parkingSpotResult.getCarParkFloor());
                else if (carParkFloor == null)
                    return null;
                if (newParkingSpot.getCarType() == null)
                    newParkingSpot.setCarType(parkingSpotResult.getCarType());
                else if (carType == null)
                    return null;

                em.getTransaction().begin();
                em.merge(newParkingSpot);
                em.getTransaction().commit();
                return newParkingSpot;
            }

        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public Object deleteParkingSpot(Long parkingSpotId) {
        ParkingSpot parkingSpot = (ParkingSpot) getParkingSpot(parkingSpotId);
        try{
            if (parkingSpot != null) {
                for (Reservation reservation : parkingSpot.getReservations()) {
                    endReservation(reservation.getId());
                    reservation.setParkingSpot(null);
                    updateReservation(reservation);
                }
                em.getTransaction().begin();
                em.merge(parkingSpot);
                em.remove(parkingSpot);
                em.getTransaction().commit();
                return parkingSpot;
            }

        } catch (Exception e){
            return null;
        }
        return null;
    }

    @Override
    public Object createCar(Long userId, String brand, String model, String colour, String vehicleRegistrationPlate) {
        User user = (User) getUser(userId);
        CarType carType = (CarType) getCarType("Gasoline");
        try {
            if (user != null) {
                if (carType == null){
                    createCarType("Gasoline");
                    carType = (CarType) getCarType("Gasoline");
                }
                Car car = new Car(user, brand, model, colour, vehicleRegistrationPlate, carType);
                em.getTransaction().begin();
                em.persist(car);
                em.getTransaction().commit();
                return car;
            }

        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public Object getCar(Long carId) {
        try {
            Car carResult = em.find(Car.class, carId);
            if (carResult != null) {
                return carResult;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public Object getCar(String vehicleRegistrationPlate) {
        TypedQuery <Car> query = em.createNamedQuery("Car.findByVehicleRegistrationPlate",Car.class);
        query.setParameter("vehicleRegistrationPlate",vehicleRegistrationPlate);
        try {
            Car carResult = query.getSingleResult();
            if (carResult != null) {
                return carResult;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public List<Object> getCars(Long userId) {
        TypedQuery <Car> query = em.createNamedQuery("Car.findByUserId", Car.class);
        query.setParameter("id", userId);
        try {
            List <Car> carResult = query.getResultList();
            if (!(carResult.isEmpty())) {
                return new ArrayList<>(carResult);
            }
        } catch (Exception e) {
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }

    @Override
    public Object updateCar(Object car) {
        Car newCar = (Car) car;
        Car carResult = (Car) getCar(newCar.getId());
        User user = (User) getUser(newCar.getUser().getId());
        try {
            if (carResult != null){
                newCar.setReservations(carResult.getReservations());
                if (newCar.getUser() == null)
                    newCar.setUser(carResult.getUser());
                else if (user == null)
                    return null;

                if (newCar.getBrand() == null)
                    newCar.setBrand(carResult.getBrand());
                if (newCar.getModel() == null)
                    newCar.setModel(carResult.getModel());
                if (newCar.getColour() == null)
                    newCar.setColour(carResult.getColour());
                if (newCar.getVehicleRegistrationPlate() == null)
                    newCar.setVehicleRegistrationPlate(carResult.getVehicleRegistrationPlate());
                if (newCar.getCarType() == null)
                    newCar.setCarType(carResult.getCarType());

                em.getTransaction().begin();
                em.merge(newCar);
                em.getTransaction().commit();
                return newCar;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public Object deleteCar(Long carId) {
        Car carResult = (Car) getCar(carId);
        try{
            if (carResult != null) {
                for (Reservation reservation : carResult.getReservations()) {
                    endReservation(reservation.getId());
                    reservation.setCar(null);
                    updateReservation(reservation);

                }
                em.getTransaction().begin();
                em.merge(carResult);
                em.remove(carResult);
                em.getTransaction().commit();
                return carResult;
            }

        } catch (Exception e){
            return null;
        }

        return null;
    }

    @Override
    public Object createUser(String firstname, String lastname, String email) {
        User user = new User(firstname, lastname, email);
        user.setCars(Collections.emptyList());
        try{
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }
        catch (Exception e){
            return null;
        }
        return user;
    }

    @Override
    public Object getUser(Long userId) {
        try{
            User userResult =  em.find(User.class,userId);
            if (userResult != null){
                return userResult;
            }
        }
        catch (Exception e){
            return null;

        }
        return null;
    }

    @Override
    public Object getUser(String email) {
        TypedQuery<User> query = em.createNamedQuery("User.findByEmail", User.class);
        query.setParameter("email", email);
        try {
            User userResult = query.getSingleResult();
            if (userResult != null) {
                return userResult;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public List<Object> getUsers() {
        TypedQuery<User> query = em.createNamedQuery("User.findAll", User.class);
        try {
            List<User> userResult = query.getResultList();
            if (!(userResult.isEmpty())){
                return new ArrayList<>(userResult);
            }
        }
        catch (Exception e){
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }

    @Override
    public Object updateUser(Object user) {
        User newUser = (User) user;
        User userResult = (User) getUser(newUser.getId());
        try {
            if (userResult != null) {
                newUser.setCars(userResult.getCars());
                if (newUser.getFirstname() == null)
                    newUser.setFirstname(userResult.getFirstname());
                if (newUser.getLastname() == null)
                    newUser.setLastname(userResult.getLastname());
                if (newUser.getEmail() == null)
                    newUser.setEmail(userResult.getEmail());
                em.getTransaction().begin();
                em.merge(newUser);
                em.getTransaction().commit();
                return newUser;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public Object deleteUser(Long userId) {
        User userResult = (User) getUser(userId);
        try {
            if (userResult != null){
                for (Car car : userResult.getCars()) {
                    deleteCar(car.getId());
                }
                em.getTransaction().begin();
                em.remove(userResult);
                em.getTransaction().commit();
                return userResult;
            }
        }
        catch (Exception e){
            return null;
        }
        return null;
    }

    @Override
    public Object createReservation(Long parkingSpotId, Long cardId) {
        ParkingSpot parkingSpot = (ParkingSpot) getParkingSpot(parkingSpotId);
        Car car = (Car) getCar(cardId);
        try{
            if (parkingSpot != null && car != null){
                if (!Objects.equals(parkingSpot.getCarType().getId(), car.getCarType().getId())) {
                    System.out.println("Not suitable Parking Spot for your Car.");
                    return null;
                }
                em.refresh(parkingSpot);
                em.refresh(car);
                if (parkingSpot.getReservations() != null || car.getReservations() != null) {
                    for (Reservation activeReservation: parkingSpot.getReservations()){
                        if (activeReservation.getPrice() == null) {
                            System.out.println("There is already an active Reservation.");
                            return null;
                        }
                    }
                    for (Reservation activeReservation: car.getReservations()){
                        if (activeReservation.getPrice() == null) {
                            System.out.println("There is already an active Reservation.");
                            return null;
                        }
                    }

                }
                Reservation reservation = new Reservation(new Date(), car, parkingSpot);
                em.getTransaction().begin();
                em.persist(reservation);
                em.getTransaction().commit();
                return reservation;
            }

        }
        catch (Exception e){
            return null;
        }
        return null;
    }

    @Override
    public Object endReservation(Long reservationId) {
        Reservation reservation = em.find(Reservation.class, reservationId);
        try{
            if (reservation != null) {
                if (reservation.getEndTime() == null){
                    reservation.setEndTime(new Date());
                    long time = Math.abs(new Date().getTime() - reservation.getStartTime().getTime());
                    long hours = TimeUnit.HOURS.convert(time, TimeUnit.MILLISECONDS) + 1;
                    int price = (int) (reservation.getParkingSpot().getCarParkFloor().getCarPark().getPricePerHour() * hours);
                    reservation.setPrice(price);
                    em.getTransaction().begin();
                    em.merge(reservation);
                    em.getTransaction().commit();
                }
                else
                    System.out.println("Reservation has already ended.");

                return reservation;
            }
        }
        catch (Exception e){
            return null;
        }
        return null;
    }

    @Override
    public List<Object> getReservations(Long parkingSpotId, Date date) {
        TypedQuery<Reservation> query = em.createNamedQuery("Reservation.findByDate", Reservation.class);
        Calendar c = new GregorianCalendar();
        c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date date2 = c.getTime();
        query.setParameter("id", parkingSpotId);
        query.setParameter("startTime", date, TemporalType.DATE );
        query.setParameter("endTime", date2, TemporalType.DATE);

        try{
            List<Reservation> reservations = query.getResultList();
            if (!(reservations.isEmpty())) {
                return new ArrayList<>(reservations);
            }
        }
        catch (Exception e){
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Object> getMyReservations(Long userId) {
        EntityManager entityManager = emf.createEntityManager();
        TypedQuery<Reservation> query = entityManager.createNamedQuery("Reservation.findUserReservations", Reservation.class);
        query.setParameter("id", userId);

        try{
            List<Reservation> reservations = query.getResultList();
            if (!(reservations.isEmpty())) {
                return new ArrayList<>(reservations);
            }
        }
        catch (Exception e){
            return Collections.emptyList();
        }
        return Collections.emptyList();

    }

    @Override
    public Object updateReservation(Object reservation) {
        Reservation newReservation = (Reservation) reservation;
        Reservation reservationResult = em.find(Reservation.class, newReservation.getId());
        try {
            if (reservationResult != null) {
                ParkingSpot parkingSpot = (ParkingSpot) getParkingSpot(newReservation.getParkingSpot().getId());
                Car car = (Car) getCar(newReservation.getCar().getId());

                if (newReservation.getStartTime() == null)
                    newReservation.setStartTime(reservationResult.getStartTime());
                if (newReservation.getCar() == null)
                    newReservation.setCar(reservationResult.getCar());
                else if (car == null)
                    return null;

                if (newReservation.getParkingSpot() == null)
                    newReservation.setParkingSpot(reservationResult.getParkingSpot());
                else if (parkingSpot == null)
                    return null;

                if (newReservation.getEndTime() == null)
                    newReservation.setEndTime(reservationResult.getEndTime());
                if (newReservation.getPrice() == null)
                    newReservation.setPrice(reservationResult.getPrice());

                if (parkingSpot != null && car != null){
                    if (!Objects.equals(parkingSpot.getCarType().getId(), car.getCarType().getId())) {
                        System.out.println("Not suitable Parking Spot for your Car.");
                        return null;
                    }
                    em.refresh(parkingSpot);
                    em.refresh(car);

                    if (parkingSpot.getReservations() != null || car.getReservations() != null) {
                        for (Reservation activeReservation: parkingSpot.getReservations()){
                            if (activeReservation.getPrice() == null) {
                                System.out.println("There is already an active Reservation.");
                                return null;
                            }
                        }
                        for (Reservation activeReservation: car.getReservations()){
                            if (activeReservation.getPrice() == null) {
                                System.out.println("There is already an active Reservation.");
                                return null;
                            }
                        }
                    }
                    em.getTransaction().begin();
                    em.merge(newReservation);
                    em.getTransaction().commit();
                    return newReservation;
                }
            }
        }
        catch (Exception e){
            return null;
        }
        return null;
    }

    @Override
    public Object createCarType(String name) {
        CarType carType = new CarType(name);
        try{
            em.getTransaction().begin();
            em.persist(carType);
            em.getTransaction().commit();
        }
        catch (Exception e ){
            return null;
        }
        return carType;
    }

    @Override
    public List<Object> getCarTypes() {
        TypedQuery<CarType> query = em.createNamedQuery("CarType.findAll", CarType.class);
        try{
            List<CarType> carTypes = query.getResultList();
            if(!(carTypes.isEmpty())) {
                return new ArrayList<>(carTypes);
            }
        }
        catch (Exception e){
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }

    @Override
    public Object getCarType(Long carTypeId) {
        CarType carType = em.find(CarType.class, carTypeId);
        try{
            if (carType != null){
                return carType;
            }
        }
        catch (Exception e){
            return null;
        }
        return null;
    }

    @Override
    public Object getCarType(String name) {
        TypedQuery<CarType> query = em.createNamedQuery("CarType.findByName", CarType.class);
        query.setParameter("name", name);
        try {
            CarType carType = query.getSingleResult();
            if (carType != null)
                return carType;
        }
        catch (Exception e){
            return null;
        }
        return null;
    }

    @Override
    public Object deleteCarType(Long carTypeId) {
        CarType carType = (CarType) getCarType(carTypeId);
        try{
            if (Objects.equals(carType.getName(), "Gasoline"))
                return carType;

            for (Car car: carType.getCars()){
                car.setCarType((CarType) getCarType("Gasoline"));
                updateCar(car);
            }
            for (ParkingSpot parkingSpot: carType.getParkingSpots()){
                parkingSpot.setCarType((CarType) getCarType("Gasoline"));
                updateParkingSpot(parkingSpot);
            }

            em.getTransaction().begin();
            em.merge(carType);
            em.remove(carType);
            em.getTransaction().commit();
            return carType;
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public Object createCar(Long userId, String brand, String model, String colour, String vehicleRegistrationPlate, Long carTypeId) {
        try{
            User user = (User) getUser(userId);
            CarType carType = (CarType) getCarType(carTypeId);

            if (user != null) {
                if (carType == null) {
                    return null;
                }

                Car car = new Car(user, brand, model, colour, vehicleRegistrationPlate, carType);
                em.getTransaction().begin();
                em.persist(car);
                em.getTransaction().commit();
                return car;
            }
        }
        catch (Exception e){
            return null;
        }
        return null;
    }

    @Override
    public Object createParkingSpot(Long carParkId, String floorIdentifier, String spotIdentifier, Long carTypeId) {
        try {
            CarParkFloor carParkFloor = (CarParkFloor) getCarParkFloor(carParkId, floorIdentifier);
            CarType carType = (CarType) getCarType(carTypeId);

            if (carParkFloor != null){
                if(carType == null){
                    return null;
                }

                ParkingSpot parkingSpot = new ParkingSpot(spotIdentifier, carParkFloor, carType);
                em.getTransaction().begin();
                em.persist(parkingSpot);
                em.getTransaction().commit();
                return parkingSpot;
            }
        }
        catch (Exception e){
            return null;
        }
        return null;
    }
}
