package me.soulyana.wk7challenge;

import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room,Long> {

    //Get all rented rooms (true), unrented rooms (false)
    Iterable <Room> findAllByRented(boolean rented);

    //Count the number of rented/not rented rooms
    int countAllByRented(boolean rented);

    //privateListing(true) (false)
    Iterable <Room> findAllByPrivateListing(boolean privateListing);

}
