import { AuthorModel } from './author.model';
import { KnoldusStatsModel } from './knoldusStats.model';

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
