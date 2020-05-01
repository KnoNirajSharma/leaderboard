export interface AuthorModel {
    authorName: string;
    score: number;
    rank: number;
    monthlyScore ?: string;
    monthlyRank ?: string;
    monthlyStreak ?: string;
}
