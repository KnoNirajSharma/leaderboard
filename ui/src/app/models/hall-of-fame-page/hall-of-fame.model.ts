export interface HallOfFameModel {
  month: string;
  year: number;
  leaders: LeaderModel[];
}

export interface LeaderModel {
  month: string;
  year: number;
  knolderId: number;
  knolderName: string;
  allTimeScore: number;
  allTimeRank: number;
  monthlyScore: number;
  monthlyRank: number;
  position?: number;
}
