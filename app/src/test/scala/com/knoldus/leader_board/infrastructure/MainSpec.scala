package com.knoldus.leader_board.infrastructure

import org.scalatest.Suites

class MainSpec extends Suites(
  new StoreBlogsImplSpec,
  new StoreKnolxImplSpec,
  new FetchBlogsImplSpec,
<<<<<<< HEAD
  new FetchKnolxImplSpec,
=======
>>>>>>> 214137affeb3d92feca09a0b7a2211dc3995ad4b
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
