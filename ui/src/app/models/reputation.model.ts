import { AuthorModel } from './author.model';

export interface ReputationModel {
  monthlyBlogCount: number;
  monthlyKnolxCount: number;
  monthlyWebinarCount: number;
  monthlyTechHubCount: number;
  monthlyOsContributionCount: number;
  allTimeBlogCount: number;
  allTimeKnolxCount: number;
  allTimeWebinarCount: number;
  allTimeTechHubCount: number;
  allTimeOsContributionCount: number;
  reputation: AuthorModel[];
}
