import {ContributionDetailsModel} from './ContributionDetails.model';

export interface ScoreBreakDownModel {
    contributionType: string;
    contributionCount: number;
    contributionScore: number;
    contributionDetails: ContributionDetailsModel[];
}
