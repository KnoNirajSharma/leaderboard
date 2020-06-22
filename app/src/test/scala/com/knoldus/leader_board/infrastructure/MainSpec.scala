package com.knoldus.leader_board.infrastructure

import org.scalatest.Suites

class MainSpec extends Suites(
  new StoreBlogsImplSpec,
  new FetchBlogsImplSpec,
  new ReadContributionImplSpec,
  new WriteAllTimeReputationImplSpec,
  new ReadAllTimeReputationImplSpec,
  new WriteMonthlyReputationImplSpec,
  new ReadMonthlyReputationImplSpec,
  new WriteQuarterlyReputationImplSpec,
  new ReadQuarterlyReputationImplSpec,
  new FetchReputationImplSpec,
  new FetchKnolderDetailsImplSpec
)
