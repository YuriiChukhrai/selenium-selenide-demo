package com.exception;

import org.apache.log4j.Logger;

/**
 *
 * @author limit (Yurii Chukhrai)
 */
public class UnknownTypeWebDriverExceptions extends RuntimeException {

	private static final long serialVersionUID = -6423330935345283215L;
	static final Logger LOG = Logger.getLogger(UnknownTypeWebDriverExceptions.class.getName());

	public UnknownTypeWebDriverExceptions(String msg) {
		super(String.format("TID [%d] %s", Thread.currentThread().getId(), msg));
		LOG.error(String.format("TID [%d] %s", Thread.currentThread().getId(), msg));
	}

	public UnknownTypeWebDriverExceptions(String msg, Throwable throwable) {
		super(String.format("TID [%d] %s", Thread.currentThread().getId(), msg), throwable);
		LOG.error(String.format("TID [%d]. Chain exception. %s", Thread.currentThread().getId(), msg));
	}
}
