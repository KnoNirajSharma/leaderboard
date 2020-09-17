export interface AuthorModel {
  knolderId: number;
  knolderName: string;
  allTimeScore: number;
  allTimeRank: number;
  quarterlyStreak: string;
  monthlyScore: number;
  monthlyRank: number;
  topRanker?: boolean;
}
