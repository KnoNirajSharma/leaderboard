import { TrendsModel } from './trends.model';
import { AuthorModel } from './author.model';

export interface TribeDetailsModel {
  name: string;
  tribeSummery: SummaryDetails[];
  allTimeScoreBreakdown: AllTimeScoreBreakdownModel[];
  trends: TrendsModel[];
  memberReputations: AuthorModel[];
}

interface SummaryDetails {
  name: string;
  value: number;
}

interface AllTimeScoreBreakdownModel {
  contributionType: string;
  contributionScore: number;
}
