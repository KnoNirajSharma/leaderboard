import { TrendsModel } from './trends.model';
import { AuthorModel } from './author.model';

export interface TribeDetailsModel {
  name: string;
  tribeSummery: SummeryDetails[];
  allTimeScoreBreakdown: AllTimeScoreBreakdownModel[];
  trends: TrendsModel[];
  memberReputations: AuthorModel[];
}

interface SummeryDetails {
  name: string;
  value: number;
}

interface AllTimeScoreBreakdownModel {
  contributionType: string;
  contributionScore: number;
}
