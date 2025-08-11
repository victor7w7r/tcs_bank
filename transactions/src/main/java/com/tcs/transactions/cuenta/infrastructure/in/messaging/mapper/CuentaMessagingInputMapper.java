package com.tcs.transactions.cuenta.infrastructure.in.messaging.mapper;

import com.tcs.transactions.cuenta.domain.model.StatusAccountReceive;
import com.tcs.transactions.cuenta.domain.model.StatusAccountSend;
import com.tcs.transactions.cuenta.infrastructure.in.messaging.entity.StatusAccountReceiveRes;
import com.tcs.transactions.cuenta.infrastructure.in.messaging.entity.StatusAccountSendReq;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CuentaMessagingInputMapper {
    StatusAccountReceiveRes toStatusAccountReceiveRes(StatusAccountReceive statusAccountReceive);
    List<StatusAccountReceiveRes> toStatusAccountReceiveResList(List<StatusAccountReceive> statusAccountReceiveList);
    StatusAccountSend toStatusAccountSend(StatusAccountSendReq statusAccountSendReq);
}
