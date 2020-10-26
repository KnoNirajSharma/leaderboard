import { ContributionDetailsModel } from './ContributionDetails.model';

export interface ScoreBreakDownModel {
  contributionType: string;
  contributionScore: number;
  contributionCount?: number;
  contributionDetails?: ContributionDetailsModel[];
}
