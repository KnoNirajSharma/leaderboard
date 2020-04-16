package com.knoldus.leader_board.infrastructure

import org.scalatest.Suites

class MainSpec extends Suites(new StoreDataImplSpec, new FetchDataImplSpec, new UpdateDataImplSpec)
