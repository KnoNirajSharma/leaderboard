export interface KnolderDetailsModel {
  knolderName: string;
  score: number;
  scoreBreakDown: ScoreBreakDownModel[];
  pastTrend?: { month: string; score: number; }[];
}

export interface ScoreBreakDownModel {
  contributionType: string;
  contributionScore: number;
  contributionCount?: number;
  contributionDetails?: ContributionDetailsModel[];
}

export interface ContributionDetailsModel {
  title: string;
  date: string;
}
