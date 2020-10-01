export interface ScoringInfoModel {
  points: number;
  multiplier: number;
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
