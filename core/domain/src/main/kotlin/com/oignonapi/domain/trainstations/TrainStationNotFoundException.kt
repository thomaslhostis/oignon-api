package com.oignonapi.domain.trainstations

import com.oignonapi.domain.exceptions.NotFoundException

class TrainStationNotFoundException(trainStationId: String) :
    NotFoundException("La station de train $trainStationId n'existe pas")
