package com.jdp.irs.service;

import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jdp.irs.entity.CreditCardEntity;
import com.jdp.irs.entity.FlightEntity;
import com.jdp.irs.entity.PassengerEntity;
import com.jdp.irs.entity.TicketEntity;
import com.jdp.irs.entity.UserEntity;
import com.jdp.irs.exception.CreditCardNotFoundException;
import com.jdp.irs.exception.InvalidCardDetailsException;
import com.jdp.irs.model.Booking;
import com.jdp.irs.model.CreditCard;
import com.jdp.irs.model.Passenger;
import com.jdp.irs.model.PassengerListContainer;
import com.jdp.irs.repository.CreditCardRepository;
import com.jdp.irs.repository.FlightRepository;
import com.jdp.irs.repository.PassengerRepository;
import com.jdp.irs.repository.TicketRepository;
import com.jdp.irs.repository.UserRepository;
import com.jdp.irs.utility.CalendarUtility;

@Service
public class PaymentService {
	@Autowired
	private CreditCardRepository creditCardRepository;
	@Autowired
	private FlightRepository flightRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private PassengerRepository passengerRepository;

	public void findCreditCard(CreditCard creditCard) throws CreditCardNotFoundException, InvalidCardDetailsException {
//		CreditCardEntity cce = (CreditCardEntity) creditCardRepository.findOne(creditCard.getCardNumber());
		CreditCardEntity cce = (CreditCardEntity) creditCardRepository.getOne(creditCard.getCardNumber());
		if (cce == null) {
			throw new CreditCardNotFoundException("PaymentService.CREDITCARD_NOT_FOUND");
		}
		if (
			!(cce.getCardHolderName().equalsIgnoreCase(creditCard.getCardHolderName())
					&& cce.getCvv().equals(creditCard.getCvv())
					&& cce.getExpiryMonth().equalsIgnoreCase(creditCard.getExpiryMonth())
					&& cce.getExpiryYear().equals(creditCard.getExpiryYear())
					&& cce.getSecurePin().equals(creditCard.getSecurePin()))
		) {
			throw new InvalidCardDetailsException("PaymentService.INVALID_CARD_DETAILS");
		}
	}

	public void updateCreditCard(String cardNumber, String fare) {
		creditCardRepository.updateSeatCount(cardNumber, fare);
	}

	public void confirmBooking(HttpSession session) throws Exception {
		PassengerListContainer passengerListContainer = (PassengerListContainer) session
				.getAttribute("passengerListContainer");
		List<Passenger> passengerList = passengerListContainer.getPassengerList();
		Booking booking = (Booking) session.getAttribute("booking");
//		UserEntity ue = userRepository.findOne(booking.getName());
		UserEntity ue = userRepository.getOne(booking.getName());
//		FlightEntity flightEntity = flightRepository.findOne(booking.getFlightId());
		FlightEntity flightEntity = flightRepository.getOne(booking.getFlightId());
		flightRepository.updateSeatsDetails(booking.getFlightId(), booking.getSeats());
		TicketEntity te = new TicketEntity();
		te.setBookingDate(CalendarUtility.getStringFromCalendar(Calendar.getInstance()));
		te.setDepartureDate(booking.getDepartureDate());
		te.setDepartureTime(booking.getDepartureTime());
		te.setNoOfSeats(booking.getSeats());
		te.setPnr(booking.getPnr().toString());
		te.setCustomer(ue);
		te.setFlight(flightEntity);
		te.setTotalFare(booking.getFare());
		ticketRepository.saveAndFlush(te);
		for (Passenger passenger : passengerList) {
			PassengerEntity pe = new PassengerEntity();
			pe.setPassengerAge(passenger.getAge());
			pe.setPassengerGender(passenger.getGender());
			pe.setPassengerName(passenger.getPassengerName());
//			TicketEntity t = ticketRepository.findOne(booking.getPnr().toString());
			TicketEntity t = ticketRepository.getOne(booking.getPnr().toString());
			pe.setTicket(t);
			pe = passengerRepository.saveAndFlush(pe);
		}
	}
}
