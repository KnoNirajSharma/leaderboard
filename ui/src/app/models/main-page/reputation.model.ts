import { AuthorModel } from '../author.model';

export interface ReputationModel {
  blogs: KnoldusStatsModel;
  knolx: KnoldusStatsModel;
  webinars: KnoldusStatsModel;
  techhubTemplates: KnoldusStatsModel;
  osContributions: KnoldusStatsModel;
  conferences: KnoldusStatsModel;
  books: KnoldusStatsModel;
  researchPapers: KnoldusStatsModel;
  reputation: AuthorModel[];
}

export interface KnoldusStatsModel {
  monthly: number;
  allTime: number;
}
