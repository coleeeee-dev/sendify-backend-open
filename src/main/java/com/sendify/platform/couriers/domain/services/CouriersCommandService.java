package com.sendify.platform.couriers.domain.services;

import com.sendify.platform.couriers.domain.model.aggregates.Courier;
import com.sendify.platform.couriers.domain.model.commands.CreateCourierCommand;
import com.sendify.platform.couriers.domain.model.commands.UpdateCourierCommand;

public interface CouriersCommandService {

    Courier handle(CreateCourierCommand command);

    Courier handle(UpdateCourierCommand command);
}
