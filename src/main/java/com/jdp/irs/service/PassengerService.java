package com.jdp.irs.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.jdp.irs.exception.PassengerDetailNotFoundException;
import com.jdp.irs.model.Passenger;

@Service
public class PassengerService {
	public void validatePassengerDetails(List<Passenger> passengerList) throws PassengerDetailNotFoundException {
		int count = 0;
		for (Passenger passenger : passengerList) {
			if (passenger.getPassengerName() != null & passenger.getPassengerName() != "") {
				break;
			}
			else
				count++;
		}
		if (count == passengerList.size()) {
			throw new PassengerDetailNotFoundException("PassengerService.PASSENGER_NOT_FOUND");
		}
	}
}