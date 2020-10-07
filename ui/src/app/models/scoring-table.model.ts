export interface ScoringInfoModel {
  points: number;
  pointsMultiplier: number;
}

export interface ScoringTableModel {
  blog: ScoringInfoModel;
  knolx: ScoringInfoModel;
  webinar: ScoringInfoModel;
  techhubTemplate: ScoringInfoModel;
  osContribution: ScoringInfoModel;
  conference: ScoringInfoModel;
  book: ScoringInfoModel;
  researchPaper: ScoringInfoModel;
}
