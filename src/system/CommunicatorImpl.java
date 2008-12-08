package system;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import communication.AVLS;
import communication.MDT;

import events.AmbulancePosition;
import events.Event;
import events.MobilisationConfirmation;

/**
 * This class represents the communication module.  It provides communication
 * channel between system and AVLS/MDT.
 * 
 * @author Simon Busard
 */

public class CommunicatorImpl implements Communicator, Runnable {
	
	/**
	 * The AVLS
	 */
	private AVLS avls;
	/**
	 * The MDT
	 */
	private MDT mdt;
	/**
	 * The list of ambulances known by the system
	 */
	private Ambulance ambulances;
	/**
	 * List of untreated mobilisation confirmations
	 */
	private BlockingQueue<Event> mobConfirmations;
	/**
	 * List of untreated events
	 */
	private BlockingQueue<Event> events;
	/**
	 * Thread stopped or not
	 */
	private boolean finished;
	
	/**
	 * Create a new communicator
	 * 
	 * @param avls the avls sending and receiving messages from/to this
	 * @param mdt the mdt sending and receiving messages from/to this
	 * @param ambulances the list of ambulances known by the system
	 */
	public CommunicatorImpl(AVLS avls, MDT mdt, Ambulance ambulances) {
		this.avls = avls;
		this.mdt = mdt;
		this.ambulances = ambulances;
		
		this.mobConfirmations = new LinkedBlockingQueue<Event>();
		this.events = new LinkedBlockingQueue<Event>();
		
		this.finished = false;
		
		Thread thread = new Thread(this);
		thread.start();
	}

	/* (non-Javadoc)
	 * @see system.Communicator#waitForEvent(int)
	 */
	public Event waitForEvent(String ambulanceId) {
		Event mdtMessage = null;
		try {
			mdtMessage = this.events.take();
		} catch(InterruptedException e) {
			// TODO
		}
		if(mdtMessage.getSenderName() == ambulanceId) {
			return mdtMessage;
		}
		else {
			try {
				this.events.put(mdtMessage);
			} catch(InterruptedException e) {
				// TODO
			}
			return this.waitForEvent(ambulanceId);
		}
	}
	/**
	 * Main loop of the thread.  Get and dispatch messages
	 */
	private void mainLoop() {
		while(this.finished) {
			// Get MDT messages
			Event mdtEvent = this.mdt.receive();
			if(mdtEvent != null) {
				if(mdtEvent.getClass() == MobilisationConfirmation.class) {
					try {
						this.mobConfirmations.put(mdtEvent);
					} catch(InterruptedException e) {
						// TODO
					}
				}
				else {
					try {
						this.events.put(mdtEvent);
					} catch(InterruptedException e) {
						// TODO
					}
				}
			}
			
			// Get AVLS messages
			AmbulancePosition avlsMessage = (AmbulancePosition)this.avls.receive();
			if(avlsMessage != null) {
				this.ambulances.setPosition(	avlsMessage.getSenderName(), 
										avlsMessage.getPosition());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see system.Communicator#send(events.Event)
	 */
	public void send(Event order) {
		this.mdt.send(order);
	}

	/*
	 * (non-Javadoc)
	 * @see system.Communicator#waitForAck(events.Event, java.lang.String)
	 */
	public boolean waitForAck(Event order, String ambulanceId) {
		Event mdtMessage = null;
		try {
			mdtMessage = this.mobConfirmations.take();
		} catch(InterruptedException e) {
			// TODO
		}
		if(mdtMessage.getSenderName() == ambulanceId) {
			return true;
		}
		else {
			try {
				this.mobConfirmations.put(mdtMessage);
			} catch(InterruptedException e) {
				// TODO
			}
			return this.waitForAck(order,ambulanceId);
		}
	}
	
	/**
	 * Stop the thread
	 */
	public void stop() {
		this.finished = true;
	}

	/**
	 * Method runned by the thread
	 */
	public void run() {
		this.mainLoop();
	}
}
