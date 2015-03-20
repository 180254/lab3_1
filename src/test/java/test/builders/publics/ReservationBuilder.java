package test.builders.publics;

import java.util.Date;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.reservation.Reservation;
import pl.com.bottega.ecommerce.sales.domain.reservation.Reservation.ReservationStatus;

public class ReservationBuilder {
	private Id id;
	private ReservationStatus status;
	private ClientData clientData;
	private Date createDate;

	public ReservationBuilder() {
		id = Id.generate();
		status = ReservationStatus.OPENED;
		clientData = new ClientDataBuilder().build();
		createDate = new Date();
	}

	public ReservationBuilder(Reservation reservation) {
		this.id = reservation.getId();
		this.status = reservation.getStatus();
		this.clientData = reservation.getClientData();
		this.createDate = reservation.getCreateDate();
	}

	public ReservationBuilder withStatus(ReservationStatus status) {
		this.status = status;
		return this;
	}

	public ReservationBuilder withClientData(ClientData clientData) {
		this.clientData = clientData;
		return this;
	}

	public ReservationBuilder withCreateDate(Date createDate) {
		this.createDate = createDate;
		return this;
	}

	public Reservation build() {
		return new Reservation(id, status, clientData, createDate);
	}
}
