package com.joh.esms.service;

import java.util.List;

import com.joh.esms.domain.model.NotificationD;

public interface ReportService {
	
	List<NotificationD> findAdminNotifications();

}
