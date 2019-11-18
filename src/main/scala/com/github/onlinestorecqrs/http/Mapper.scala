package com.github.onlinestorecqrs.http

import java.util.UUID

import com.github.onlinestorecqrs.api.Api
import com.github.onlinestorecqrs.api.Api.{ItemDTO, OrderDTO}
import com.github.onlinestorecqrs.domain.DomainModel
import com.github.onlinestorecqrs.domain.DomainModel.Item
import com.github.onlinestorecqrs.domain.persistence.OrderActor.{CreateOrderCommand, OrderCreatedEvent}

object Mapper {

    def mapToCommand(orderDTO: OrderDTO): CreateOrderCommand = {
        new CreateOrderCommand(
            UUID.randomUUID().toString,
            "user-123", //TODO: get user
            orderDTO.items.map { dto =>
                new Item(id = dto.id, description = dto.description, amount = dto.amount, value = 0)
            })
    }

    def mapToDTO(orderCreatedEvent: OrderCreatedEvent): OrderDTO = {
        new OrderDTO(
            id = Some(orderCreatedEvent.orderId),
            items = mapItems(orderCreatedEvent.items)
        )
    }

    private def mapItems(items: List[DomainModel.Item]): List[Api.ItemDTO] = {
        items.map { item =>
            new ItemDTO(
                id = item.id,
                description = item.description,
                amount = item.amount
            )
        }
    }

}
